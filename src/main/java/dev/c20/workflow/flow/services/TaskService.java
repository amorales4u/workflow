package dev.c20.workflow.flow.services;

import dev.c20.workflow.WorkflowApplication;
import dev.c20.workflow.commons.auth.UserEntity;
import dev.c20.workflow.commons.wrapper.responses.Activity;
import dev.c20.workflow.flow.responses.EvalResult;
import dev.c20.workflow.storage.entities.Storage;
import dev.c20.workflow.storage.entities.adds.Data;
import dev.c20.workflow.storage.entities.adds.Log;
import dev.c20.workflow.storage.repositories.*;
import dev.c20.workflow.commons.tools.PathUtils;
import dev.c20.workflow.commons.tools.StringUtils;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

@Service
public class TaskService {

    static public final String TASK_CLAZZ = "task_instance";
    static public final Integer TASK_ACTIVE = 100;
    static public final Integer TASK_COMPLETED = 110;
    static public final Integer TASK_ERROR   = 120;
    static public final Integer TASK_DELETED   = 130;

    protected final org.apache.commons.logging.Log logger = LogFactory.getLog(this.getClass());

    EntityManager em;
    @Autowired
    public TaskService setEntityManager(EntityManager em) {
        logger.info( "Set entityManager");
        this.em = em;
        return this;
    }
    @Autowired
    StorageRepository storageRepository;

    @Autowired
    DataRepository dataRepository;

    @Autowired
    PermRepository permRepository;

    @Autowired
    AttachRepository attachRepository;

    @Autowired
    LogRepository logRepository;

    @Autowired
    NoteRepository noteRepository;

    @Autowired
    ValueRepository valueRepository;

    @Autowired
    EvalProcessService evalProcessService;

    private Boolean isFolder = false;
    private Boolean isFile = false;
    private String path;
    private Storage folderProcess;
    private Storage taskFile;
    private HttpServletRequest httpRequest;
    private UserEntity userEntity;
    private Map<String,Object> processConfig = null;
    public String getPath() {

        logger.info("URI:" + httpRequest.getRequestURI());
        String path = PathUtils.getPathFromLevel(httpRequest.getRequestURI(),3);
        try {
            path =  URLDecoder.decode(path, "UTF-8");
            logger.info("Command for:" + path);
            return path;
        } catch (UnsupportedEncodingException e) {
            logger.error(e);
        }
        return null;
    }


    public TaskService setHttpServletRequest(HttpServletRequest httpRequest ) throws Exception {
        this.httpRequest  = httpRequest;
        this.path = getPath();
        if( StringUtils.isEmpty(path) )
            return this;
        this.isFolder = PathUtils.isFolder(path);


        if( this.isFolder ) {
            logger.info("Resquest is for folder: " +  this.getPath());
            this.folderProcess = storageRepository.getFolder(this.getPath());
        } else {
            this.taskFile = storageRepository.getFolder(this.getPath());
            this.folderProcess = storageRepository.getFolder(PathUtils.getParentFolder(this.getPath()));
        }

        Data data = dataRepository.getByParent(folderProcess.getId());

        if( data != null )
            processConfig = (Map<String,Object>)StringUtils.JSONFromString(data.getData());
        else
            throw new RuntimeException("El Proceso no esta configurado");

        userEntity = UserEntity.fromToken(httpRequest.getHeader(WorkflowApplication.HEADER_AUTHORIZATION));

        return this;
    }

    public Log newLog(Storage parent, String user, String comment ) {
        return newLog( parent, user, comment,null, null );
    }
    public Log newLog( Storage parent, String user, String comment, Long commentId ) {
        return newLog( parent, user, comment,commentId, null );
    }

    public Log newLog( Storage parent, String user, String comment, Long commentId, Long type ) {
        Log log = new Log()
                .setParent(parent)
                .setModifier(user)
                .setModified(new Date())
                .setComment(comment)
                .setCommentId(commentId)
                .setType(type);
        logRepository.save(log);
        return log;
    }


    private void sendWorkflowEvent(Storage task, Map<String,Object> data ) {
        try {

            String appProcess = httpRequest.getScheme() + "://" + httpRequest.getServerName() + ":" + httpRequest.getLocalPort() + processConfig.get("service") ;
            logger.info("Sending message to:" + appProcess);

            int timeout = 1000;
            RequestConfig config = RequestConfig.custom()
                    .setConnectTimeout(timeout)
                    .setConnectionRequestTimeout(timeout)
                    .setSocketTimeout(timeout).build();

            CloseableHttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(config).build();

            HttpPost httpPost = new HttpPost(appProcess);

            StringEntity entity = new StringEntity(StringUtils.toJSON(createResult( task, data )));
            httpPost.setEntity(entity);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            httpPost.setHeader(WorkflowApplication.HEADER_AUTHORIZATION, httpRequest.getHeader(WorkflowApplication.HEADER_AUTHORIZATION));

            CloseableHttpResponse response = client.execute(httpPost);
            client.close();
            logger.info("Sended message to:" + appProcess);
        } catch( Exception ex ) {
            logger.error(ex);
        }

    }

    private Map<String,Object> createResult( Storage task, Map<String,Object> data ) {
        Map<String,Object> result = new HashMap<>();
        result.put("error", 0);
        result.put("errorDescription", null);
        result.put("task",task);
        result.put("data",data);
        return result;
    }

    private Map<String,Object> createErrorResult( int error, String errorDescription ) {
        Map<String,Object> result = new HashMap<>();
        result.put("error", error);
        result.put("errorDescription", errorDescription);
        result.put("task", null);
        result.put("data", null);
        return result;
    }

    public String canReadStorage(Storage storage) {
        if( storage.getDeleted() )
            return storage.getName() + " esta eliminado";

        if( storage.getLocked() )
            return storage.getName() + " esta bloqueado por sistema";

        if( storage.getVisible() )
            return storage.getName() + " no es visible para los usuarios";

        return null;
    }

    public ResponseEntity<?> getAllTasksCount() throws Exception {

        List<Activity> result = new ArrayList<>();

        List<String> userPermissions = userEntity.getPermissionsList();
        // llamamos los workflows a los que tiene permiso el usuario
        List<String> workflows = permRepository.getWorkflows(userPermissions);

        for( String workflow : workflows) {
            List<String> activities = permRepository.getWorkflows(workflow+"%", userPermissions);
            Activity resultActivities = new Activity().setName(workflow);
            resultActivities.setActivities(new ArrayList<>());
            logger.info("Permission for:" + workflow);
            for( String activitie: activities ) {

                Long count = permRepository.getWorkflowsCount(activitie, userPermissions);
                logger.info("Permission for:" + activitie + " with " + count + " tasks instances");

                if( count > 0l ) {
                    resultActivities.setCount( resultActivities.getCount() + count);
                }

                resultActivities.getActivities().add( new Activity().setName(activitie).setCount(count));

            }

            result.add(resultActivities);

        }

        return ResponseEntity.ok(result);

    }

    public ResponseEntity<?> getTask() throws Exception {
        if( this.taskFile == null ) {
            return ResponseEntity.badRequest().body(createErrorResult(100,"Get:Se espera un File para la tarea"));
        }

        String canReadStorage = canReadStorage(this.taskFile);
        if( canReadStorage != null ) {
            return ResponseEntity.badRequest().body("Get:" + canReadStorage);
        }

        Data dataStorage = dataRepository.getOne(taskFile.getId());
        Map<String,Object> data = ( Map<String,Object>)StringUtils.fromJSON(dataStorage.getData());

        return ResponseEntity.ok(createResult(taskFile,data));

    }

    public ResponseEntity<?> create(Map<String,Object> data) throws Exception {

        if( this.folderProcess == null ) {
            return ResponseEntity.badRequest().body(createErrorResult(200,"Create:No se encuentra la configuración del proceso"));
        }

        String canReadStorage = canReadStorage(this.folderProcess);
        if( canReadStorage != null ) {
            return ResponseEntity.badRequest().body(createErrorResult(300,"Create:" + canReadStorage));
        }


        // crea un proceso

        String startFolder = (String)this.processConfig.get("startFolder");

        String newPath = this.folderProcess.getPath() + startFolder + "/" + StringUtils.randomString(20);

        int cnt = permRepository.userHasCreatePermissionsInStorage( this.folderProcess.getId(), userEntity.getPermissionsList() );

        if( cnt == 0 ) {
            return ResponseEntity.badRequest().body(createErrorResult(400,"Create:El usuario no tiene permisos para crear una tarea"));
        }
        Storage task = new Storage()
                .setCreated(new Date())
                .setCreator(userEntity.getUser())
                .setClazzName(TASK_CLAZZ)
                .setPath(newPath)
                .setStatus(TASK_ACTIVE);

        storageRepository.save(task);
        Data dataStorage = new Data()
                .setParent(task.getId())
                .setData(StringUtils.toJSON(data,true));
        dataRepository.save(dataStorage);

        sendWorkflowEvent(task,data);

        return ResponseEntity.ok(createResult(task,data));

    }

    public ResponseEntity<?> update( Map<String,Object> data) throws Exception {

        // Actualiza un proceso
        if( this.folderProcess == null ) {
            return ResponseEntity.badRequest().body(createErrorResult(500,"Update:No se encuentra la configuración del proceso"));
        }

        String canReadStorage = canReadStorage(this.folderProcess);
        if( canReadStorage != null ) {
            return ResponseEntity.badRequest().body(createErrorResult(600,"Update:" + canReadStorage));
        }

        if( this.taskFile == null )
            return ResponseEntity.badRequest().body(createErrorResult(700,"Update:No existe la tarea solicitada"));

        canReadStorage = canReadStorage(this.taskFile);
        if( canReadStorage != null ) {
            return ResponseEntity.badRequest().body(createErrorResult(800,"Update:" + canReadStorage));
        }

        // crea un proceso

        int cnt = permRepository.userHasUpdatePermissionsInStorage( this.folderProcess.getId(), userEntity.getPermissionsList() );

        if( cnt == 0 ) {
            return ResponseEntity.badRequest().body(createErrorResult(900,"Update:El usuario no tiene permisos para modificar una tarea"));
        }

        taskFile.setModifyDate(new Date())
                .setModifier(userEntity.getUser())
                .setClazzName(TASK_CLAZZ)
                .setStatus(TASK_ACTIVE);

        storageRepository.save(taskFile);
        Data dataStorage = dataRepository.getOne(taskFile.getId());
        dataStorage.setData(StringUtils.toJSON(data,true));
        dataRepository.save(dataStorage);

        sendWorkflowEvent(taskFile,data);

        return ResponseEntity.ok(createResult(taskFile,data));

    }

    public ResponseEntity<?> delete( Map<String,Object> data) throws Exception {

        // completa una tarea
        if( this.folderProcess == null ) {
            return ResponseEntity.badRequest().body(createErrorResult(1000,"Delete:No se encuentra la configuración del proceso"));
        }

        String canReadStorage = canReadStorage(this.folderProcess);
        if( canReadStorage != null ) {
            return ResponseEntity.badRequest().body("Delete:" + canReadStorage);
        }

        if( this.taskFile == null )
            return ResponseEntity.badRequest().body(createErrorResult(1100,"Delete:No existe la tarea solicitada"));

        canReadStorage = canReadStorage(this.taskFile);
        if( canReadStorage != null ) {
            return ResponseEntity.badRequest().body(createErrorResult(1200,"Delete:" + canReadStorage));
        }

        // crea un proceso

        int cnt = permRepository.userHasDeletePermissionsInStorage( this.folderProcess.getId(), userEntity.getPermissionsList() );

        if( cnt == 0 ) {
            return ResponseEntity.badRequest().body(createErrorResult(1300,"Delete:El usuario no tiene permisos para eliminar una tarea"));
        }

        taskFile.setDeletedDate(new Date())
                .setUserDeleter(userEntity.getUser())
                .setClazzName(TASK_CLAZZ)
                .setStatus(TASK_DELETED);

        storageRepository.save(taskFile);
        Data dataStorage = dataRepository.getOne(taskFile.getId());
        dataStorage.setData(StringUtils.toJSON(data,true));
        dataRepository.save(dataStorage);

        // cual es el siguiente folder a donde se movera la tarea?

        sendWorkflowEvent(taskFile,data);

        return ResponseEntity.ok(createResult(taskFile,data));
    }

    public ResponseEntity<?> complete( Map<String,Object> data) throws Exception {

        // completa una tarea
        if( this.folderProcess == null ) {
            return ResponseEntity.badRequest().body(createErrorResult(1400,"Complete:No se encuentra la configuración del proceso"));
        }

        String canReadStorage = canReadStorage(this.folderProcess);
        if( canReadStorage != null ) {
            return ResponseEntity.badRequest().body(createErrorResult(1500,"Complete:" + canReadStorage));
        }

        if( this.taskFile == null )
            return ResponseEntity.badRequest().body(createErrorResult(1600,"Complete:No existe la tarea solicitada"));

        canReadStorage = canReadStorage(this.taskFile);
        if( canReadStorage != null ) {
            return ResponseEntity.badRequest().body(createErrorResult(1700,"Complete:" + canReadStorage));
        }

        // crea un proceso

        int cnt = permRepository.userHasSendPermissionsInStorage( this.folderProcess.getId(), userEntity.getPermissionsList() );

        if( cnt == 0 ) {
            return ResponseEntity.badRequest().body(createErrorResult(1800,"Complete:El usuario no tiene permisos para completar una tarea"));
        }

        taskFile.setModifyDate(new Date())
                .setModifier(userEntity.getUser())
                .setClazzName(TASK_CLAZZ)
                .setStatus(TASK_ACTIVE);

        storageRepository.save(taskFile);
        Data dataStorage = dataRepository.getOne(taskFile.getId());
        dataStorage.setData(StringUtils.toJSON(data,true));
        dataRepository.save(dataStorage);

        // cual es el siguiente folder a donde se movera la tarea?
        String newFolder = evaluateFlow(data);

        if( newFolder == null ) {
            return ResponseEntity.badRequest().body(createErrorResult(1900,"Complete:No se encuentra un folder destino para completar la tarea"));
        }
        newFolder = this.folderProcess.getPath() + newFolder + "/" + this.taskFile.getName();
        Storage result = null;
        if( (Boolean)this.processConfig.get("saveHistory")) {
            Storage target = new Storage().setPropertiesFrom(this.taskFile);
            target.setCreated( new Date() )
                    .setCreator(userEntity.getUser());
            target.setPath(newFolder);

            storageRepository.save(target);
            // copiar propiedades adicionales

            attachRepository.copyTo(target, this.taskFile );
            dataRepository.copyTo(target.getId(), this.taskFile.getId() );
            logRepository.copyTo(target, this.taskFile );
            noteRepository.copyTo(target, this.taskFile );
            permRepository.copyTo(target, this.taskFile );
            valueRepository.copyTo(target, this.taskFile );

            this.taskFile.setStatus(TASK_COMPLETED);
            newLog(target, userEntity.getUser(), "Se copio desde " + this.taskFile.getPath());
            result = target;
        } else {
            this.taskFile.setPath(newFolder);
            storageRepository.save(taskFile);
            newLog(taskFile, userEntity.getUser(), "Se copio desde " + this.taskFile.getPath());
            result = this.taskFile;
        }


        sendWorkflowEvent(result,data);

        return ResponseEntity.ok(createResult(result,data));

    }

    private String evaluateFlow(Map<String,Object> data) {
        if( this.processConfig == null )
            return null;

        String current = PathUtils.getParentFolder( taskFile.getPath() );
        current = PathUtils.getName(current);
        current.substring(0, current.length() - 1);

        // obtenemos el objeto de flows
        Map<String,Object> flow = (Map<String,Object>)this.processConfig.get("flow");

        // obtenemos el flow del current folder
        flow = (Map<String,Object>)this.processConfig.get(current);

        String defaultFolder = (String)flow.get("default");

        List<Map<String,Object>> whens =  (List<Map<String,Object>>)flow.get("when");

        for( Map<String,Object> when : whens ) {
            List<String> conditionList = (List<String>) when.get("when");
            if( conditionList.size() == 0 ) {
                continue;
            }
            String condition = StringUtils.listAsString(conditionList, "\n");
            Map<String,Object> context = createResult(taskFile,data);
            EvalResult evalResult = new EvalResult();
            try {
                evalResult = evalProcessService.executeCode(context, this.folderProcess.getName(), this.taskFile.getName());
            } catch( Exception ex ) {
                logger.error(ex.getMessage());
            }
            if( evalResult.isError() ) {
                this.taskFile.setStatus(TASK_ERROR);
                storageRepository.save(this.taskFile);
                return null;
            }

            if( evalResult.getResponse() != null && (Boolean)evalResult.getResponse() ) {
                return (String)when.get("goto");
            }
        }



        return defaultFolder;
    }

}

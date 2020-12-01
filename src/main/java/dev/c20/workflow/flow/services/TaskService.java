package dev.c20.workflow.flow.services;

import dev.c20.workflow.WorkflowApplication;
import dev.c20.workflow.auth.entities.UserEntity;
import dev.c20.workflow.storage.entities.Storage;
import dev.c20.workflow.storage.entities.adds.Data;
import dev.c20.workflow.storage.repositories.DataRepository;
import dev.c20.workflow.storage.repositories.StorageRepository;
import dev.c20.workflow.tools.PathUtils;
import dev.c20.workflow.tools.StringUtils;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class TaskService {

    static public final Integer TASK_ACTIVE = 100;
    static public final Integer TASK_COMPLETED = 110;
    static public final Integer TASK_ERROR   = 120;

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
        result.put("task",task);
        result.put("data",data);
        return result;
    }

    public ResponseEntity<?> getTask() throws Exception {
        if( isFolder ) {
            return ResponseEntity.badRequest().body("Get:Se espera un File para la tarea");
        }
        return ResponseEntity.badRequest().body("Funcionalidad no implementada");

    }

    public ResponseEntity<?> create(Map<String,Object> data) throws Exception {

        if( this.folderProcess == null ) {
            return ResponseEntity.badRequest().body("Create:No se encuentra la configuración del proceso");
        }
        // crea un proceso

        String startFolder = (String)this.processConfig.get("startFolder");

        String newPath = this.folderProcess.getPath() + startFolder + "/" + StringUtils.randomString(20);

        Storage task = new Storage()
                .setCreated(new Date())
                .setCreator(userEntity.getUser())
                .setPath(newPath)
                .setStatus(TASK_ACTIVE);

        storageRepository.save(task);
        Data dataStorage = new Data()
                .setParent(task.getId())
                .setData(StringUtils.toJSON(data,true));

        sendWorkflowEvent(task,data);

        return ResponseEntity.ok(createResult(task,data));

    }

    public ResponseEntity<?> update( Map<String,Object> data) throws Exception {

        // Actualiza un proceso
        if( isFolder ) {
            return ResponseEntity.badRequest().body("Update: Se espera un File para la tarea");
        }

        return ResponseEntity.badRequest().body("Funcionalidad no implementada");
    }

    public ResponseEntity<?> complete( Map<String,Object> data) throws Exception {

        // desborra un proceso
        if( !isFolder ) {
            return ResponseEntity.badRequest().body("complete: Se espera un Folder para configurar el proceso");
        }

        return ResponseEntity.badRequest().body("Funcionalidad no implementada");
    }

    public ResponseEntity<?> delete( Map<String,Object> data) throws Exception {

        // desborra un proceso
        if( !isFolder ) {
            return ResponseEntity.badRequest().body("complete: Se espera un Folder para configurar el proceso");
        }

        return ResponseEntity.badRequest().body("Funcionalidad no implementada");
    }


}

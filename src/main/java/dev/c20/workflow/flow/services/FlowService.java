package dev.c20.workflow.flow.services;

import dev.c20.workflow.WorkflowApplication;
import dev.c20.workflow.commons.auth.UserEntity;
import dev.c20.workflow.storage.entities.Storage;
import dev.c20.workflow.storage.entities.adds.Data;
import dev.c20.workflow.storage.repositories.DataRepository;
import dev.c20.workflow.storage.repositories.StorageRepository;
import dev.c20.workflow.commons.tools.PathUtils;
import dev.c20.workflow.commons.tools.StringUtils;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

@Service
public class FlowService {

    protected final org.apache.commons.logging.Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    DataRepository dataRepository;

    EntityManager em;
    @Autowired
    public FlowService setEntityManager(EntityManager em) {
        logger.info( "Set entityManager");
        this.em = em;
        return this;
    }
    @Autowired
    StorageRepository storageRepository;

    private Boolean isFolder = false;
    private Boolean isFile = false;
    private String path;
    private Storage folderProcess;
    private HttpServletRequest httpRequest;
    private UserEntity userEntity;
    public String getPath() {

        String path = WorkflowApplication.WORKFLOWS_PATH + PathUtils.getPathFromLevel(httpRequest.getRequestURI(),3);
        try {
            path =  URLDecoder.decode(path, "UTF-8");
            logger.info("Command for:" + path);
            return path;
        } catch (UnsupportedEncodingException e) {
            logger.error(e);
        }
        return null;
    }


    public FlowService setHttpServletRequest( HttpServletRequest httpRequest ) {
        this.httpRequest  = httpRequest;
        this.path = getPath();
        logger.info("Request is for folder " + this.path);
        if( StringUtils.isEmpty(path) )
            return this;
        this.isFolder = PathUtils.isFolder(path);


        if( this.isFolder ) {
            this.folderProcess = storageRepository.getFolder(this.getPath());
        } else {
        }

        userEntity = UserEntity.fromToken(httpRequest.getHeader(WorkflowApplication.HEADER_AUTHORIZATION));

        return this;
    }

    public ResponseEntity<?> getProcess() throws Exception {
        if( !isFolder || folderProcess == null) {
            return ResponseEntity.badRequest().body("Get:Se espera un Folder para configurar el proceso");
        }
        Data dataObj = dataRepository.getByParent(folderProcess.getId());
        Map<String,Object> data = new HashMap<>();
        if( dataObj != null )
            data = (Map<String,Object>)StringUtils.JSONFromString(dataObj.getData());

        Map<String,Object> result = new HashMap<>();
        result.put("flow",folderProcess);
        result.put("data",data);
        return ResponseEntity.badRequest().body(result);

    }

    public ResponseEntity<?> createProcess(Map<String,Object> data) throws Exception {

        if( !isFolder || folderProcess == null) {
            return ResponseEntity.badRequest().body("Create:Se espera un Folder para configurar el proceso");
        }
        // crea un proceso

        Data dataObj = dataRepository.getByParent(folderProcess.getId());
        if( dataObj == null )
            dataObj = new Data()
                    .setParent(folderProcess.getId());

        dataObj.setData(StringUtils.toJSON(data,true));
        logger.info(folderProcess.getId());
        logger.info(dataObj.getData());

        dataRepository.save(dataObj);

        Map<String,Object> result = new HashMap<>();
        result.put("flow",folderProcess);
        result.put("data",data);
        return ResponseEntity.badRequest().body(result);

    }

    public ResponseEntity<?> updateProcess( Map<String,Object> data) throws Exception {

        // Actualiza un proceso
        if( !isFolder ) {
            return ResponseEntity.badRequest().body("Update: Se espera un Folder para configurar el proceso");
        }

        return ResponseEntity.badRequest().body("Funcionalidad no implementada");
    }

    public ResponseEntity<?> undeleteProcess( Map<String,Object> data) throws Exception {

        // desborra un proceso
        if( !isFolder ) {
            return ResponseEntity.badRequest().body("Undelete: Se espera un Folder para configurar el proceso");
        }

        return ResponseEntity.badRequest().body("Funcionalidad no implementada");
    }

    public ResponseEntity<?> deleteProcess( Map<String,Object> data) throws Exception {

        // elimina un proceso
        if( !isFolder ) {
            return ResponseEntity.badRequest().body("Delete: Se espera un Folder para configurar el proceso");
        }

        return ResponseEntity.badRequest().body("Funcionalidad no implementada");
    }

}

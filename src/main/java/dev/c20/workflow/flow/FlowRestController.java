package dev.c20.workflow.flow;

import dev.c20.workflow.flow.services.FlowService;
import dev.c20.workflow.storage.services.CommandService;
import dev.c20.workflow.storage.services.SecurityService;
import dev.c20.workflow.storage.services.StorageService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping(
        path = "/flow",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class FlowRestController {

    // POST Create
    // PUT Update
    // GET Read
    // DELETE delete

    protected final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    FlowService flowService;



    @GetMapping( "/**" )
    ResponseEntity<?> getProcess(  HttpServletRequest request) throws Exception {
        // el path el nombre del proceso que se desea leer
        // lee una tarea
        return flowService
                .setHttpServletRequest(request)
                .getProcess();
    }

    @PostMapping( "/**" )
    ResponseEntity<?> createProcess(@RequestBody Map<String,Object> data, HttpServletRequest request) throws Exception {

        // crea un proceso

        return flowService
                .setHttpServletRequest(request)
                .createProcess(data);
    }

    @PutMapping( "/**" )
    ResponseEntity<?> updateProcess( @RequestBody Map<String,Object> data, HttpServletRequest request) throws Exception {

        // Actualiza un proceso

        return flowService
                .setHttpServletRequest(request)
                .updateProcess(data);
    }

    @PutMapping( "/undelete/**" )
    ResponseEntity<?> undeleteProcess( @RequestBody Map<String,Object> data, HttpServletRequest request) throws Exception {

        // desborra un proceso

        return flowService
                .setHttpServletRequest(request)
                .undeleteProcess(data);
    }

    @DeleteMapping( "/**" )
    ResponseEntity<?> deleteProcess( @RequestBody Map<String,Object> data, HttpServletRequest request) throws Exception {

        // elimina un proceso
        return flowService
                .setHttpServletRequest(request)
                .deleteProcess(data);
    }


}

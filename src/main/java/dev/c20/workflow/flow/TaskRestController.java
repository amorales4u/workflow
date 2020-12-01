package dev.c20.workflow.flow;

import dev.c20.workflow.flow.services.TaskService;
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
        path = "/task",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class TaskRestController {

    // POST Create
    // PUT Update
    // GET Read
    // DELETE delete

    protected final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    TaskService taskService;


    @GetMapping( "/**" )
    ResponseEntity<?> getTask( @RequestBody Map<String,Object> data, HttpServletRequest request) throws Exception {
        // Si es una tarea, regresa una tarea, si es un folder regresa a lista de tareas
        return taskService
                .setHttpServletRequest(request)
                .getTask();

    }

    @PostMapping( "/**" )
    ResponseEntity<?> start( @RequestBody Map<String,Object> data, HttpServletRequest request) throws Exception {

        // El path trae el nomnbre del proceso
        // Inicia un proceso

        return taskService
                .setHttpServletRequest(request)
                .create(data);

    }

    @PutMapping( "/complete/**" )
    ResponseEntity<?> commplete( @RequestBody Map<String,Object> data, HttpServletRequest request) throws Exception {
        // El path trae el nomnbre de la tarea
        // Completa un proceso

        return taskService
                .setHttpServletRequest(request)
                .complete(data);

    }


    @PutMapping( "/**" )
    ResponseEntity<?> update( @RequestBody Map<String,Object> data, HttpServletRequest request) throws Exception {

        // El path trae el nomnbre de la tarea
        // Actualiza datos

        return taskService
                .setHttpServletRequest(request)
                .update(data);

    }

    @DeleteMapping( "/**" )
    ResponseEntity<?> delete( @RequestBody Map<String,Object> data, HttpServletRequest request) throws Exception {

        // El path trae el nomnbre de la tarea
        // Finaliza un proceso

        return taskService
                .setHttpServletRequest(request)
                .delete(data);

    }


}

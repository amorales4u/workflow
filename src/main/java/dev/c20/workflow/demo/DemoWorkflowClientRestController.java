package dev.c20.workflow.demo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.Response;
import java.util.Map;

@RestController
@RequestMapping(
        path = "/demo-workflow",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)

public class DemoWorkflowClientRestController {

    protected final Log logger = LogFactory.getLog(this.getClass());


    @PostMapping
    ResponseEntity<?> taskMessage(@RequestBody Map<String,Object> data, HttpServletRequest request) throws Exception {

        logger.info( "En demo-workflow " );
        logger.info( data);

        return ResponseEntity.ok("Ok");
    }

}

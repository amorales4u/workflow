package dev.c20.workflow.storage;

import dev.c20.workflow.storage.services.CommandService;
import dev.c20.workflow.storage.services.SecurityService;
import dev.c20.workflow.tools.StringUtils;
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
        path = "/commands",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class CommandRestController {

    protected final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    CommandService commandsService;

    @Autowired
    SecurityService securityService;

    @PutMapping(value = "/{command}/**", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> runCommand(@PathVariable String command, @RequestBody String params, HttpServletRequest request) throws Exception {
        logger.info(params);
        return commandsService
                .setHttpServletRequest(request)
                .runCommand(command, params.split(" "));

    }



}

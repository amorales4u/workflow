package dev.c20.workflow.rest;

import dev.c20.workflow.services.CommandService;
import dev.c20.workflow.services.SecurityService;
import dev.c20.workflow.tools.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/commands")
public class CommandRest {

    protected final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    CommandService commandsService;

    @Autowired
    SecurityService securityService;

    @PutMapping("/{command}/**")
    ResponseEntity<?> runCommand(@PathVariable String command, @RequestBody String params, HttpServletRequest request) throws Exception {

        Map<String,Object> userData = securityService.setHttpServletRequest(request)
            .readUserFromToken();

        return ResponseEntity.ok(userData);
        /*
        return commandsService
                .setHttpServletRequest(request)
                .runCommand(command, StringUtils.splitAsList(params," ", true));

         */
    }



}

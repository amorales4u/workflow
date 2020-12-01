package dev.c20.workflow.auth;

import dev.c20.workflow.auth.entities.UserEntity;
import dev.c20.workflow.auth.services.AuthenticationService;
import dev.c20.workflow.tools.StringUtils;
import org.apache.log4j.Logger;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import dev.c20.workflow.storage.repositories.StorageRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(
        path = "/authentication",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class AuthenticationRestController {

    Logger logger = Logger.getLogger(AuthenticationRestController.class.getName());

    @Autowired
    private StorageRepository storageRepository;

    @Autowired
    AuthenticationService authService;

    @PutMapping("/")
    public ResponseEntity<?> createAuthenticationToken(HttpServletRequest request, HttpServletResponse response) throws Exception {

        authService
                .setHttpServletRequest(request)
                .authenticate();

        if( !authService.isAuthenticated() )
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        UserEntity userEntity = authService.getAuthenticatedUser();

        String token = StringUtils.getToken(userEntity.asMap());

        response.setHeader("Authorization", "token "+ token);

        logger.info("User authenticated:" + userEntity.getName());

        return ResponseEntity.ok(userEntity);
    }


}

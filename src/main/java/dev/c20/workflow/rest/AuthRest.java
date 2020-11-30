package dev.c20.workflow.rest;

import dev.c20.workflow.services.AuthService;
import dev.c20.workflow.services.SecurityService;
import org.apache.log4j.Logger;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import dev.c20.workflow.repositories.StorageRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthRest {

    Logger logger = Logger.getLogger(AuthRest.class.getName());

    @Autowired
    private StorageRepository storageRepository;

    @Autowired
    AuthService authService;

    @Autowired
    SecurityService securityService;

    @PutMapping("/")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody Map<String,Object> user, HttpServletRequest request, HttpServletResponse response) throws Exception {

        authService
                .setUser((String)user.get("user"))
                .authenticate();

        if( !authService.isAuthenticated() )
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        String token = securityService
                .setUserData(authService.getUserData())
                .setHttpServletResponse(response)
                .getToken(authService.getUserData());

        authService.getUserData().put("token", token);
        return ResponseEntity.ok(authService.getUserData());
    }


    /*
    @GetMapping("/version/**")
    ResponseEntity<?> jwtProtected(@AuthenticationPrincipal Jwt jwt) {
        String role = jwt.getClaimAsString("role");
        return ResponseEntity.ok(role);
    }

*/


}

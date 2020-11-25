package dev.c20.workflow.rest;

import org.apache.log4j.Logger;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import dev.c20.workflow.repositories.StorageRepository;

import java.util.Map;

@RestController
public class AuthRest {

    Logger logger = Logger.getLogger(AuthRest.class.getName());

    @Autowired
    private StorageRepository storageRepository;

    @PutMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody Map<String,Object> user) throws Exception {

        return ResponseEntity.ok("Aca");
    }


    /*
    @GetMapping("/version/**")
    ResponseEntity<?> jwtProtected(@AuthenticationPrincipal Jwt jwt) {
        String role = jwt.getClaimAsString("role");
        return ResponseEntity.ok(role);
    }

*/


}

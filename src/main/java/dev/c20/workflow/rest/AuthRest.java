package dev.c20.workflow.rest;

import dev.c20.workflow.security.JwtRequest;
import dev.c20.workflow.security.JwtResponse;
import dev.c20.workflow.security.JwtTokenUtil;
import dev.c20.workflow.security.JwtUserDetailsService;
import dev.c20.workflow.tools.PathUtils;
import org.apache.log4j.Logger;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import dev.c20.workflow.repositories.StorageRepository;

import javax.servlet.http.HttpServletRequest;

@RestController
public class AuthRest {

    Logger logger = Logger.getLogger(AuthRest.class.getName());

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;


    @Autowired
    private StorageRepository storageRepository;

    @PutMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, password);
            //usernamePasswordAuthenticationToken.setAuthenticated(true);
            authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    @PutMapping("/folder/add/**")
    ResponseEntity<?> createFolder(HttpServletRequest request) {
        logger.info(request.getContextPath());
        String path = request.getRequestURI().substring((request.getContextPath() + "/storage/folder").length());
        String rootPath = PathUtils.getParentFolder(path);
        String folderName = PathUtils.getName(path);
        //String storage = storageRepository.createFolder( new User(),1,rootPath,folderName);
        //return storage != null ? ResponseEntity.ok(storage) :
        //        ResponseEntity.notFound().build();
        logger.info(path);

        return ResponseEntity.ok(path);
    }
    /*
    @GetMapping("/version/**")
    ResponseEntity<?> jwtProtected(@AuthenticationPrincipal Jwt jwt) {
        String role = jwt.getClaimAsString("role");
        return ResponseEntity.ok(role);
    }

*/


}

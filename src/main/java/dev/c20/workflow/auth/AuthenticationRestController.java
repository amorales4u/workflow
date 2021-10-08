package dev.c20.workflow.auth;

import dev.c20.workflow.auth.entities.UserEntity;
import dev.c20.workflow.auth.services.AuthenticateBase;
import dev.c20.workflow.auth.services.AuthenticationService;
import dev.c20.workflow.commons.tools.StringUtils;
import dev.c20.workflow.storage.repositories.StorageRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(
        path = "/authentication",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class AuthenticationRestController {

    protected final Log logger = LogFactory.getLog(this.getClass());
    @Autowired
    private StorageRepository storageRepository;

    @Autowired
    AuthenticationService authService;

    @PutMapping("/")
    public ResponseEntity<UserEntity> createAuthenticationToken(@RequestBody UserEntity userEntity, HttpServletRequest request, HttpServletResponse response) throws InterruptedException {

        authService
                .setUserEntity(userEntity)
                .authenticate();

        if( !authService.isAuthenticated() ) {
            Map<String,Object> res = new HashMap<>();
            res.put("error", true);
            res.put("errorNo", authService.getAuthenticateComponent().getHasError());
            response.addHeader("auth-error", authService.getAuthenticateComponent().getHasError()+ "");
            userEntity.setErrorInLogin(true);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(userEntity);
        }

        userEntity = authService.getAuthenticatedUser();
        userEntity.setErrorInLogin(false);
        response.addHeader(AuthenticateBase.AUTHORIZATION, AuthenticateBase.AUTHORIZATION_TOKEN + StringUtils.getToken(userEntity.asMap()));

        //Thread.sleep(5000);
        return ResponseEntity.ok(userEntity);
    }

    @GetMapping("/revalidate/")
    public ResponseEntity<UserEntity> revalidate(HttpServletRequest request, HttpServletResponse response)  {

        UserEntity userEntity = UserEntity.fromToken(request.getHeader(AuthenticateBase.AUTHORIZATION));

        response.addHeader(AuthenticateBase.AUTHORIZATION, AuthenticateBase.AUTHORIZATION_TOKEN + StringUtils.getToken(userEntity.asMap()));
        return ResponseEntity.ok(userEntity);
    }


}

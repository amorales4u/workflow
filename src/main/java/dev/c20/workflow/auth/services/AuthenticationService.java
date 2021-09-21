package dev.c20.workflow.auth.services;

import dev.c20.workflow.auth.entities.UserEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Getter
@Setter
@Accessors(chain = true)
@Service
public class AuthenticationService {

    @Autowired
    DataBaseAuthenticateComponent authenticateComponent;

    UserEntity userEntity = null;
    public AuthenticationService setHttpServletRequest(HttpServletRequest request) {
        return this;
    }

    public AuthenticationService authenticate() {

        authenticateComponent
                .setAuthenticatedUser(this.userEntity)
                .authenticate();
        return this;
    }



    public boolean isAuthenticated() {
        return authenticateComponent.isAuthenticated();
    }

    public UserEntity getAuthenticatedUser() {
        return authenticateComponent.getAuthenticatedUser();
    }

}

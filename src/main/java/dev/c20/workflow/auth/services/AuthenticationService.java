package dev.c20.workflow.auth.services;

import dev.c20.workflow.WorkflowApplication;
import dev.c20.workflow.commons.auth.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class AuthenticationService {

    @Autowired
    SimpleAuthenticateComponent authenticateComponent;

    String user = null;
    String pass = null;
    public AuthenticationService setHttpServletRequest(HttpServletRequest request) {
        user = request.getHeader(WorkflowApplication.HEADER_USER_NAME );
        return this;
    }

    public AuthenticationService authenticate() {

        authenticateComponent
                .setUser(this.user)
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

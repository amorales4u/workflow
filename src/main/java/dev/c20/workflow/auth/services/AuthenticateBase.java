package dev.c20.workflow.auth.services;

import dev.c20.workflow.auth.entities.UserEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class AuthenticateBase {

    private HttpServletResponse httpResponse = null;
    private HttpServletRequest httpRequest = null;
    private String user = null;
    private UserEntity auhenticatedUser = null;
    boolean authenticated = false;



    public AuthenticateBase setHttpServletResponse(HttpServletResponse httpResponse) {
        this.httpResponse = httpResponse;
        return this;
    }

    public AuthenticateBase setHttpServletRequest(HttpServletRequest httpRequest) {
        this.httpRequest = httpRequest;
        return this;
    }

    public String getUser() {
        return user;
    }

    public AuthenticateBase setUser(String user) {
        this.user = user;
        return this;
    }


    public Boolean isAuthenticated() {
        return authenticated;
    }
    public AuthenticateBase setAuthenticated(Boolean authenticated) {
        this.authenticated = authenticated;
        return this;
    }

    public abstract AuthenticateBase authenticate();

    public UserEntity getAuthenticatedUser() {
        return auhenticatedUser;
    }

    public AuthenticateBase setAuthenticatedUser(UserEntity auhenticatedUser) {
        this.auhenticatedUser = auhenticatedUser;
        return this;
    }

}

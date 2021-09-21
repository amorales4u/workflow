package dev.c20.workflow.auth.services;

import dev.c20.workflow.auth.entities.UserEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Getter
@Setter
@Accessors(chain = true)
public abstract class AuthenticateBase {

    private HttpServletResponse httpResponse = null;
    private HttpServletRequest httpRequest = null;
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

    public boolean isAuthenticated() {
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

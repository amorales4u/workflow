package dev.c20.workflow.auth.entities;

import dev.c20.workflow.auth.AuthenticationRestController;
import dev.c20.workflow.tools.StringUtils;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class UserEntity {

    private String user;
    private String name;
    private String roles;
    private String email;
    private Map<String,Object> extras = new HashMap<>();

    Logger logger = Logger.getLogger(AuthenticationRestController.class.getName());

    public String getUser() {
        return user;
    }

    public UserEntity setUser(String user) {
        this.user = user;
        return this;
    }

    public String getName() {
        return name;
    }

    public UserEntity setName(String name) {
        this.name = name;
        return this;
    }

    public String getRoles() {
        return roles;
    }

    public UserEntity setRoles(String roles) {
        this.roles = roles;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserEntity setEmail(String email) {
        this.email = email;
        return this;
    }

    public Map<String, Object> getExtras() {
        return extras;
    }

    public UserEntity setExtras(Map<String, Object> extras) {
        this.extras = extras;
        return this;
    }

    public String randomString() {
        return StringUtils.randomString(20);
    }

    public Map<String,Object> asMap() {
        Map<String,Object> usr = new HashMap<>();
        usr.put("user",this.user);
        usr.put("userName",this.name);
        usr.put("email",this.email);
        usr.put("roles",this.roles);
        usr.put("extras",this.extras);

        return usr;



    }
    public UserEntity copyFrom(UserEntity userEntity) {
        this.setUser(userEntity.user);
        this.setName(userEntity.getName());
        this.setRoles(userEntity.getRoles());
        this.setRoles(userEntity.getEmail());
        this.setExtras(userEntity.getExtras());
        return this;
    }


    static public UserEntity fromMap(Map<String,Object> usr) {

        return new UserEntity()
                .setEmail((String)usr.get("email"))
                .setExtras((Map<String, Object>)usr.get("extras"))
                .setName((String)usr.get("userName"))
                .setUser(((String)usr.get("user")))
                .setRoles(((String)usr.get("roles")));

    }

    static public UserEntity fromToken(String token) {
        return fromMap( StringUtils.readToken(token));
    }


    }

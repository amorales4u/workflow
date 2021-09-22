package dev.c20.workflow.auth.entities;

import dev.c20.workflow.commons.tools.StringUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Slf4j
@Getter
@Setter
@Accessors(chain = true)
public class UserEntity {

    private String user;
    private String name;
    private String roles;
    private String email;
    private String otp;
    private String password;

    private Map<String,Object> extras = new HashMap<>();

    public List<String> getRolesList() {
        if( this.getRoles() == null ) {
            return new LinkedList<>();
        }
        List<String> permissions = StringUtils.splitAsList(this.getRoles(),",",true );
        permissions.add(this.getUser());

        return permissions;

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

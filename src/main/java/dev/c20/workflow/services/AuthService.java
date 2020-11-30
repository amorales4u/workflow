package dev.c20.workflow.services;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    Map<String,Object> userData = new HashMap<>();
    String user = null;
    boolean authenticated = false;

    public AuthService setUser( String user ) {
        this.user = user;
        return this;
    }

    public AuthService authenticate() {

        if( this.user.equals("amorales") ) {
            authenticated = true;
            userData.put("user",this.user);
            userData.put("userName","Aton Morales");
            userData.put("email","AtonMorales@mail.com");
            readUserData();
        } else if( this.user.equals("emorales")) {
            authenticated = true;
            userData.put("user",this.user);
            userData.put("userName","Efra Morales");
            userData.put("email","EfraMorales@mail.com");
            readUserData();
        } else if( this.user.equals("ymorales")) {
            authenticated = true;
            userData.put("user",this.user);
            userData.put("userName","Axel Morales");
            userData.put("email","AxelMorales@mail.com");
            readUserData();
        }
        return this;
    }

    public AuthService readUserData() {
       userData.put("roles", "ADMIN,TEST,ALGO" );
       return this;
    }

    public Map<String,Object> getUserData() {
        return this.userData;
    }

    public Boolean isAuthenticated() {
        return authenticated;
    }

}

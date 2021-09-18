package dev.c20.workflow.auth.services;

import dev.c20.workflow.commons.auth.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class SimpleAuthenticateComponent extends AuthenticateBase {

    @Override
    public AuthenticateBase authenticate() {
        if( this.getUser().equals("amorales") ) {
            this.setAuthenticated( true );
            UserEntity user = new UserEntity()
                    .setUser(this.getUser())
                    .setName("Aton Morales")
                    .setRoles("STORAGE,COMMAND,WORKFLOW,ADMIN,TEST,DEV");
            this.setAuthenticatedUser(user);
        } else if( this.getUser().equals("emorales")) {
            this.setAuthenticated( true );
            UserEntity user = new UserEntity()
                    .setUser(this.getUser())
                    .setName("Efra Morales")
                    .setRoles("COMMAND, DEV");
            this.setAuthenticatedUser(user);
        } else if( this.getUser().equals("ymorales")) {
            this.setAuthenticated( true );
            UserEntity user = new UserEntity()
                    .setUser(this.getUser())
                    .setName("Axel Morales")
                    .setRoles("TEST");
            this.setAuthenticatedUser(user);
        }
        return this;
    }

}

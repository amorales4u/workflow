package dev.c20.workflow.auth.services;

import dev.c20.workflow.auth.entities.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class SimpleAuthenticateComponent extends AuthenticateBase {

    @Override
    public AuthenticateBase authenticate() {
        if( this.getAuthenticatedUser().getUser().equals("amorales") ) {
            this.setAuthenticated( true );
            UserEntity user = new UserEntity()
                    .setUser(this.getAuthenticatedUser().getUser())
                    .setName("Aton Morales")
                    .setRoles("STORAGE,COMMAND,WORKFLOW,ADMIN,TEST,DEV");
            this.setAuthenticatedUser(user);
        } else if( this.getAuthenticatedUser().getUser().equals("emorales")) {
            this.setAuthenticated( true );
            UserEntity user = new UserEntity()
                    .setUser(this.getAuthenticatedUser().getUser())
                    .setName("Efra Morales")
                    .setRoles("COMMAND, DEV");
            this.setAuthenticatedUser(user);
        } else if( this.getAuthenticatedUser().getUser().equals("ymorales")) {
            this.setAuthenticated( true );
            UserEntity user = new UserEntity()
                    .setUser(this.getAuthenticatedUser().getUser())
                    .setName("Axel Morales")
                    .setRoles("TEST");
            this.setAuthenticatedUser(user);
        }
        return this;
    }

}

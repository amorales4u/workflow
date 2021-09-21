package dev.c20.workflow;

import dev.c20.workflow.auth.entities.UserEntity;
import dev.c20.workflow.commons.tools.PathUtils;
import dev.c20.workflow.storage.entities.Storage;
import dev.c20.workflow.storage.services.responses.ObjectResponse;
import dev.c20.workflow.storage.services.v2.StorageSystemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Order(1)
public class StartupConfig implements CommandLineRunner {

    @Autowired
    StorageSystemService storageSystemService;

    @Override
    public void run(String... args) throws Exception {

        log.info("Startup config start");

        UserEntity userEntity = new UserEntity();
        userEntity.setUser("admin");
        userEntity.setRoles("ADMIN,WKF");

        for( String role : userEntity.getPermissionsList() ) {
            log.info("Role=>"+role);
        }

        storageSystemService.setUserEntity(userEntity);
        ObjectResponse<Storage> storageObjectResponse = storageSystemService.getFolder("/system/business/facts/");

        /*
        if( storageObjectResponse.isError() ) {
            log.error(storageObjectResponse.getErrorDescription());
        } else {
            log.info(storageObjectResponse.getData().getName());
        }
        */

        storageObjectResponse = storageSystemService.createStorage("/Sistema/Usuarios/bfg9000",
                new Storage()
                        .setDescription("Antonio Morales")
        );

        if( storageObjectResponse.isError() ) {
            log.error(storageObjectResponse.getErrorDescription());
        } else {
            log.info(storageObjectResponse.getData().getName());
        }



        log.info(PathUtils.getPathLevel("/") + " => /");
        log.info(PathUtils.getPathLevel("/Sistema/") + " => /Sistema/");
        log.info(PathUtils.getPathLevel("/Sistema/Usuarios/") + " => /Sistema/Usuarios/");
        log.info(PathUtils.getPathLevel("/Sistema/Usuarios/Internos/") + " => /Sistema/Usuarios/Internos/");

        log.info("Startup Config finish");

    }
}

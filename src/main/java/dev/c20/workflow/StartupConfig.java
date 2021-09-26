package dev.c20.workflow;

import dev.c20.workflow.auth.entities.UserEntity;
import dev.c20.workflow.commons.tools.PathUtils;
import dev.c20.workflow.storage.entities.Storage;
import dev.c20.workflow.storage.entities.adds.Value;
import dev.c20.workflow.storage.repositories.ValueRepository;
import dev.c20.workflow.storage.services.responses.ListResponse;
import dev.c20.workflow.storage.services.responses.ObjectResponse;
import dev.c20.workflow.storage.services.v2.StorageSystemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import java.util.List;

@Slf4j
@Component
@Order(1)
public class StartupConfig implements CommandLineRunner {

    @Autowired
    StorageSystemService storageSystemService;

    @Autowired
    ValueRepository valueRepository;

    @org.springframework.beans.factory.annotation.Value("${sys.secret}")
    String secretKey;



    @Override
    public void run(String... args) throws Exception {

        log.info("Startup config start");

        UserEntity userEntity = new UserEntity();
        userEntity.setUser("admin");
        userEntity.setRoles("ADMIN,WKF");

        for( String role : userEntity.getRolesList() ) {
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

        String path = "/Sistema/Usuarios/bfg9000";
        ListResponse<Storage> response = storageSystemService.getFolderList(path);

        if( response.isError() ) {
            log.error(response.getErrorDescription());
            return;
        }

        Storage file = response.getData().get(0);
        /*
        storageSystemService.addValue(path,
                new Value().
                        setName("email").
                        setValue("amorales@c20.dev").
                        setOrder(10)
        );
        */
        /*
        storageSystemService.addValue(path,
                new Value().
                        setName("password").
                        setValue(StringUtils.encrypt("tigger",secretKey)).
                        setOrder(30).
                        setIntValue(1000)
        );
        */
        log.info("User properties:");
        List<Value> properties = valueRepository.getAllProperties(file);

        for( Value property : properties) {
            log.info("Name:" + property.getName() + " Value:" + property.getValue());
        }

        log.info(PathUtils.getPathLevel("/") + " => /");
        log.info(PathUtils.getPathLevel("/Sistema/") + " => /Sistema/");
        log.info(PathUtils.getPathLevel("/Sistema/Usuarios/") + " => /Sistema/Usuarios/");
        log.info(PathUtils.getPathLevel("/Sistema/Usuarios/Internos/") + " => /Sistema/Usuarios/Internos/");

        log.info("Startup Config finish");

    }
}

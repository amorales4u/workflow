package dev.c20.workflow.demo;

import dev.c20.workflow.commons.annotations.RoleException;
import dev.c20.workflow.commons.annotations.Roles;
import dev.c20.workflow.demo.entities.Palabra;
import dev.c20.workflow.demo.entities.PalabraRepository;
import dev.c20.workflow.storage.entities.Storage;
import dev.c20.workflow.storage.entities.adds.Value;
import dev.c20.workflow.storage.repositories.StorageRepository;
import dev.c20.workflow.storage.repositories.ValueRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping(
        path = "/demo-workflow",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)

public class DemoWorkflowClientRestController {

    protected final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    ValueRepository valueRepository;

    @Autowired
    PalabraRepository palabraRepository;

    @Autowired
    StorageRepository storageRepository;

    @PostMapping
    @Roles(value = {"ADMINISTRA"})
    ResponseEntity<?> createTest(HttpServletRequest request) throws Exception {

        logger.info("Create words for entities");

        List<Value> values = new ArrayList<>();

        for( int n = 1; n <= 10000; n++) {
            long randomRecord = ThreadLocalRandom.current().nextLong(1, 15 + 1);
            int randomWordPerRecord = ThreadLocalRandom.current().nextInt(5, 20 + 1);

            Storage storage = storageRepository.getOne(randomRecord);


            for( int w = 1; w <= randomWordPerRecord; w ++ ) {

                long randomWord = ThreadLocalRandom.current().nextLong(105001, 218703 + 1);
                Palabra palabra = palabraRepository.getOne(randomWord);
                if( palabra != null ) {
                    try {
                        Value value = new Value()
                                .setParent(storage)
                                .setName(palabra.getWord())
                                .setValue(palabra.getWord());
                        //logger.info("Add Palabra id:"  + randomWord + " w:" + w + " de " + randomWordPerRecord);
                        values.add(value);
                        if( values.size() == 200 ) {
                            logger.info("Save lote iteration " + n);
                            valueRepository.saveAll(values);
                            values = new ArrayList<>();
                        }
                    } catch( Exception ex ) {
                        //logger.error(ex.getMessage());
                    }
                }
            }
        }

        logger.info("Save lote iteration ");
        valueRepository.saveAll(values);


        return ResponseEntity.ok("Ok");


    }

    @GetMapping
    @Roles(value = {"ADMINISTRA"})
    ResponseEntity<?> taskMessage(HttpServletRequest request) {

        logger.info( "En demo-workflow " );

        return ResponseEntity.ok("Ok");
    }

}

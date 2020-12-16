package dev.c20.workflow.search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(
        path = "/search",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class IndexRestController {

    @Autowired
    IndexService indexService;

    @PutMapping
    public ResponseEntity<?> search( @RequestBody  Map<String,Object> search) {

        try {

            return ResponseEntity.ok().body(indexService.search((String)search.get("search"),(int)search.get("maxHits")));

        } catch(Exception ex ) {

            return ResponseEntity.badRequest().body(ex.getMessage());
        }


    }

    @GetMapping
    public ResponseEntity<?> search() {

        try {

            indexService.add(1l, "es el ejemplo de un primer documento");
            indexService.add(2l, "es segundo ejemplo de un primer documento");
            indexService.add(3l, "Este lo creo Antonio");
            indexService.add(4l, "Axel hizo esta prueba");

            return ResponseEntity.ok().body("ya");

        } catch(Exception ex ) {
            ex.printStackTrace();
            return ResponseEntity.badRequest().body(ex.getMessage());
        }


    }


}

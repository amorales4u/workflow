package dev.c20.workflow.storage;

import dev.c20.workflow.commons.tools.PathUtils;
import dev.c20.workflow.storage.entities.Storage;
import dev.c20.workflow.storage.entities.adds.Log;
import dev.c20.workflow.storage.entities.adds.Value;
import dev.c20.workflow.storage.services.responses.ListResponse;
import dev.c20.workflow.storage.services.responses.ObjectResponse;
import dev.c20.workflow.storage.services.v2.StorageSystemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;

@Slf4j
@RestController
@RequestMapping(
        path = "/",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)

public class StorageRestControllerV2 {

    // POST Create
    // PUT Update
    // GET Read
    // DELETE delete

    @Autowired
    StorageSystemService storageSystemService;

    private String getPath(HttpServletRequest request) {
        String restOfTheUrl = (String) request.getAttribute(
                HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);

        return URLDecoder.decode( PathUtils.getPathFromLevel(restOfTheUrl,2) );
    }
    @GetMapping("/stg/**")
    public ListResponse<Storage> getFolderStorage(HttpServletRequest request) {

        String restOfTheUrl = getPath(request);

        return storageSystemService
                .setHttpServletRequest(request)
                .getFolderList(restOfTheUrl);

    }

    @PostMapping("/stg/**")
    public ObjectResponse<Storage> createStorage(@RequestBody Storage storage, HttpServletRequest request) {

        String restOfTheUrl = getPath(request);

        return storageSystemService
                .setHttpServletRequest(request)
                .createStorage(restOfTheUrl, storage);

    }

    @PutMapping("/stg/**")
    public ObjectResponse<Storage> updateStorage(@RequestBody Storage storage, HttpServletRequest request) {

        String restOfTheUrl = getPath(request);

        return storageSystemService
                .setHttpServletRequest(request)
                .updateStorage(restOfTheUrl, storage);

    }

    @DeleteMapping("/stg/**")
    public ObjectResponse<Storage> deleteStorage(@RequestBody Log log, HttpServletRequest request) {

        String restOfTheUrl = getPath(request);

        return storageSystemService
                .setHttpServletRequest(request)
                .deleteStorage(restOfTheUrl, log);

    }

    @GetMapping("/value/**")
    ListResponse<Value> readValues(HttpServletRequest request) throws Exception {
        String restOfTheUrl = getPath(request);

        return storageSystemService
                .setHttpServletRequest(request)
                .getValues(restOfTheUrl);

    }

    @PostMapping("/value/**")
    ObjectResponse<Value>  createValue(@RequestBody Value value, HttpServletRequest request) throws Exception {

        String restOfTheUrl = getPath(request);

        return storageSystemService
                .setHttpServletRequest(request)
                .addValue(restOfTheUrl,value);

    }

    @PutMapping("/value/**")
    ObjectResponse<Value> updateValue(@RequestBody Value value, HttpServletRequest request) throws Exception {

        String restOfTheUrl = getPath(request);

        return storageSystemService
                .setHttpServletRequest(request)
                .updateValue(restOfTheUrl,value);

    }

    @DeleteMapping("/value/**")
    ObjectResponse<Value> deleteValue(@RequestBody Value value, HttpServletRequest request) throws Exception {

        String restOfTheUrl = getPath(request);

        return storageSystemService
                .setHttpServletRequest(request)
                .deleteValue(restOfTheUrl,value);

    }


}

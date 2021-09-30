package dev.c20.workflow.storage;

import dev.c20.workflow.commons.tools.PathUtils;
import dev.c20.workflow.storage.entities.Storage;
import dev.c20.workflow.storage.entities.adds.Value;
import dev.c20.workflow.storage.services.requests.StorageRequest;
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
    public ListResponse<Storage> getFolderStorage( HttpServletRequest httpServletRequest) {

        String restOfTheUrl = getPath(httpServletRequest);

        return storageSystemService
                .setHttpServletRequest(httpServletRequest)
                .getFolderList(restOfTheUrl);

    }

    @PostMapping("/stg/")
    public ObjectResponse<Storage> createStorage(@RequestBody StorageRequest<Storage> storageRequest, HttpServletRequest httpServletRequest) {

        return storageSystemService
                .setHttpServletRequest(httpServletRequest)
                .createStorage(storageRequest);

    }

    @PutMapping("/stg/")
    public ObjectResponse<Storage> updateStorage(@RequestBody StorageRequest<Storage> storageRequest, HttpServletRequest httpServletRequest) {

        return storageSystemService
                .setHttpServletRequest(httpServletRequest)
                .updateStorage(storageRequest);

    }

    @DeleteMapping("/stg/")
    public ObjectResponse<Storage> deleteStorage(@RequestBody StorageRequest<Storage> storageRequest, HttpServletRequest httpServletRequest) {

        return storageSystemService
                .setHttpServletRequest(httpServletRequest)
                .deleteStorage(storageRequest);

    }

    @GetMapping("/value/**")
    public ListResponse<Value> readValues(HttpServletRequest request)  {
        String restOfTheUrl = getPath(request);

        return storageSystemService
                .setHttpServletRequest(request)
                .getValues(restOfTheUrl);

    }

    private ObjectResponse<Value>  createOrUpdateValue(StorageRequest<Value> storageRequest, HttpServletRequest httpServletRequest)  {

        return storageSystemService
                .setHttpServletRequest(httpServletRequest)
                .addOrUpdateValue(storageRequest);

    }

    @PostMapping("/value/")
    public ObjectResponse<Value>  createValue(@RequestBody StorageRequest<Value> storageRequest, HttpServletRequest httpServletRequest)  {
        return createOrUpdateValue(storageRequest,httpServletRequest);
    }

    @PutMapping("/value/")
    public ObjectResponse<Value> updateValue(@RequestBody StorageRequest<Value> storageRequest, HttpServletRequest httpServletRequest)  {

        return createOrUpdateValue(storageRequest,httpServletRequest);

    }

    @DeleteMapping("/value/")
    public ObjectResponse<Value> deleteValue(@RequestBody StorageRequest<Value> storageRequest, HttpServletRequest httpServletRequest) {

        return storageSystemService
                .setHttpServletRequest(httpServletRequest)
                .deleteValue(storageRequest);

    }


}

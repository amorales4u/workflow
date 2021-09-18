package dev.c20.workflow.storage;

import dev.c20.workflow.commons.tools.PathUtils;
import dev.c20.workflow.storage.entities.Storage;
import dev.c20.workflow.storage.services.responses.ListResponse;
import dev.c20.workflow.storage.services.v2.StorageSystemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;

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
        return PathUtils.getPathFromLevel(restOfTheUrl,2);
    }
    @GetMapping("/stg/**")
    ListResponse<Storage> getFolderStorage(HttpServletRequest request) {

        String restOfTheUrl = getPath(request);

        return storageSystemService.getFolder(restOfTheUrl);

    }


}

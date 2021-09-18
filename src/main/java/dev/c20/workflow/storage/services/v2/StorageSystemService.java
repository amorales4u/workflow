package dev.c20.workflow.storage.services.v2;

import dev.c20.workflow.commons.tools.PathUtils;
import dev.c20.workflow.storage.entities.Storage;
import dev.c20.workflow.storage.repositories.StorageRepository;
import dev.c20.workflow.storage.services.requests.StorageRequest;
import dev.c20.workflow.storage.services.responses.ListResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Getter
@Setter
@Accessors(chain = true)
@Service
public class StorageSystemService {

    @Autowired
    StorageRepository storageRepository;

    StorageRequest<Storage> storage;

    public String getPath() {
/*
        int level =  4;
        String path = PathUtils.getPathFromLevel(httpRequest.getRequestURI(),level);
        try {
            path =  URLDecoder.decode(path, "UTF-8");
            return path;
        } catch (UnsupportedEncodingException e) {
            logger.error(e);
        }

 */
        return null;
    }

    public ListResponse<Storage> getFolder(String path) {

        if( path.endsWith("*") ) {
            path = path.replace("*", "%");
        } else {
            path += "%";
        }

        List<Storage> result = storageRepository.dir(path, PathUtils.getPathLevel(path));

        return new ListResponse().setData(result);

    }
}

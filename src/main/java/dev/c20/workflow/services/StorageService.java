package dev.c20.workflow.services;

import dev.c20.workflow.entities.Storage;
import dev.c20.workflow.repositories.StorageRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Service
public class StorageService {

    protected final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    HttpServletRequest httpRequest;

    @Autowired
    HttpServletResponse httpResponse;

    @Autowired
    StorageRepository storageRepository;

    public Storage addFolder(String path) {
        StorageResponse response = new StorageResponse();


        logger.info("en AddFodler");
        logger.info(httpRequest);
        logger.info(httpRequest.getContextPath());


        return "Aca";
    }

}

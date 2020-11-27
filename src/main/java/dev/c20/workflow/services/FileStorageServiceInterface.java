package dev.c20.workflow.services;

import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.OutputStream;

public interface FileStorageServiceInterface {

    public void init();

    public Long save(MultipartFile file, String key);

    public OutputStream load(String filename, Long fileId, String key);

    public void delete(String fileName, Long fileId);

}

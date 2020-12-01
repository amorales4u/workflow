package dev.c20.workflow.files.services;

import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.OutputStream;

public interface FileStorageServiceInterface {

    public void init();

    public Long save(MultipartFile file, String key);
    public Long save(MultipartFile file, String key, Long fileId);

    public byte[] load(String filename, Long fileId, String key);

    public Long delete(String fileName, Long fileId);

}

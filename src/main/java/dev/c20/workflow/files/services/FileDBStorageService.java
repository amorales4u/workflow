package dev.c20.workflow.files.services;

import dev.c20.workflow.WorkflowApplication;
import dev.c20.workflow.files.entities.DBFile;
import dev.c20.workflow.files.repositories.DBFileRepository;
import dev.c20.workflow.tools.StringUtils;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

@Service
public class FileDBStorageService implements FileStorageServiceInterface {

    @Autowired
    EntityManager em;

    @Autowired
    DBFileRepository dbFileRepository;

    protected final org.apache.commons.logging.Log logger = LogFactory.getLog(this.getClass());

    @Override
    public void init() {

    }


    public ResponseEntity<?> downloadFile(String fileName, Long fileId ) {

        byte[] file = load(null, fileId, "la llabe");

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);

        return ResponseEntity.ok()
                .headers(headers)
                //.contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(file);

    }

    public  ResponseEntity<?>  uploadFile( MultipartFile file) {

        Long fileId = save(file,"la llabe");
        if( fileId == -1 )
            ResponseEntity.badRequest().build();

        return ResponseEntity.ok().body(fileId);
    }

    public  ResponseEntity<?>  updateFile( MultipartFile file, Long fileId ) {

        Long fileSavedId = save(file,"la llabe", fileId);
        if( fileSavedId == -1 )
            ResponseEntity.badRequest().build();

        return ResponseEntity.ok().body(fileSavedId);
    }

    public  ResponseEntity<?>  deleteFile( Long file ) {

        Long fileSavedId = delete(null,file);

        if( fileSavedId == -1 )
            ResponseEntity.badRequest().build();

        return ResponseEntity.ok().body(fileSavedId);
    }

    @Override
    public Long save(MultipartFile file, String key) {
        return save( file, key, -1l );
    }

    @Override
    public Long save(MultipartFile file, String key,Long fileId)  {
        try {

            DBFile dbFile = new DBFile();
            if( fileId != -1 ) {
                dbFile = dbFileRepository.getOne(fileId);

                if( dbFile == null )
                    return -1l;
            }
            //Blob blob = BlobProxy.generateProxy(file.getInputStream());
            ByteArrayOutputStream encrypted = StringUtils.encrypt( file.getInputStream(), WorkflowApplication.FILE_KEY);
            dbFile.setFile(encrypted.toByteArray());
            dbFileRepository.save(dbFile);
            return dbFile.getId();
        } catch( Exception ex ) {
            logger.error(ex);

            return -1l;
        }
    }

    @Override
    public byte[] load(String filename, Long fileId, String key) {
        try {
            DBFile dbFile = null;
            try {
                dbFile = dbFileRepository.getOne(fileId);
                if( dbFile !=  null ) {
                    ByteArrayInputStream input = new ByteArrayInputStream(dbFile.getFile());

                    ByteArrayOutputStream result = StringUtils.decrypt(input,WorkflowApplication.FILE_KEY);
                    return result.toByteArray();
                }

            } catch (Exception e) {
                e.printStackTrace();
                logger.error(e);
            }
        } catch( Exception ex ) {
            ex.printStackTrace();
            logger.error(ex);
        }
        return null;
    }

    @Override
    public Long delete(String fileName, Long fileId) {
        try {
            DBFile dbFile = null;
            try {
                dbFile = dbFileRepository.getOne(fileId);
                if( dbFile !=  null ) {
                    dbFileRepository.delete(dbFile);
                }

                return fileId;
            } catch (Exception e) {
                e.printStackTrace();
                logger.error(e);
            }
        } catch( Exception ex ) {
            ex.printStackTrace();
            logger.error(ex);
        }
        return null;
    }

}

package dev.c20.workflow.services;

import dev.c20.workflow.entities.adds.DBFile;
import dev.c20.workflow.repositories.DBFileRepository;
import dev.c20.workflow.tools.StringUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.LogFactory;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.sql.Blob;
import java.util.stream.Stream;

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

    final String secretKey = "Ch1sasComoAsiSiJalaCon una LLave mas laraga";

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
            ByteArrayOutputStream encrypted = StringUtils.encrypt( file.getInputStream(), secretKey);
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

                    ByteArrayOutputStream result = StringUtils.decrypt(input,secretKey);
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

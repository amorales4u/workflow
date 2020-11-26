package dev.c20.workflow.services;

import dev.c20.workflow.entities.adds.DBFile;
import dev.c20.workflow.tools.StringUtils;
import org.apache.commons.io.IOUtils;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import java.io.InputStream;
import java.nio.file.Path;
import java.sql.Blob;
import java.util.stream.Stream;

@Service
public class FileDBStorageService implements FileStorageServiceInterface {

    @Autowired
    EntityManager em;

    @Override
    public void init() {

    }


    @Override
    public Long save(MultipartFile file, String key) {
        try {
            final String secretKey = "Ch1sasComoAsiSiJalaCon una LLave mas laraga";

            DBFile dbFile = new DBFile();
            Blob blob = BlobProxy.generateProxy(file.getInputStream().);
            dbFile.setFile(IOUtils.toByteArray(StringUtils.encrypt( file.getInputStream(), secretKey)));
            em.persist(dbFile);
            return dbFile.getId();
        } catch( Exception ex ) {
            return -1l;
        }
    }

    @Override
    public Resource load(String filename, Long fileId, String key) {
        try {
            final String secretKey = "Ch1sasComoAsiSiJalaCon una LLave mas laraga";
            DBFile dbFile = null;
            try {
                dbFile = (DBFile)em.createQuery("select o from DBFile o where o.id =?1")
                        .setParameter(1,fileId)
                        .getSingleResult();
                if( dbFile !=  null ) {
                    dbFile.getFile().
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            dbFile.setFile(IOUtils.toByteArray(StringUtils.encrypt( file.getInputStream(), secretKey)));
            em.persist(dbFile);
            return dbFile.getId();
        } catch( Exception ex ) {
            return -1l;
        }
    }

    @Override
    public void delete(String fileName, Long fileId) {

    }

}

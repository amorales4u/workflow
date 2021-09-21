package dev.c20.workflow.storage.services.v2;

import dev.c20.workflow.WorkflowApplication;
import dev.c20.workflow.auth.entities.UserEntity;
import dev.c20.workflow.commons.tools.PathUtils;
import dev.c20.workflow.storage.entities.Storage;
import dev.c20.workflow.storage.entities.adds.Log;
import dev.c20.workflow.storage.repositories.LogRepository;
import dev.c20.workflow.storage.repositories.StorageRepository;
import dev.c20.workflow.storage.services.responses.ListResponse;
import dev.c20.workflow.storage.services.responses.ObjectResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Slf4j
@Getter
@Setter
@Accessors(chain = true)
@Service
public class StorageSystemService {


    public static final long LOG_CREATE = 1000L;
    public static final long LOG_UPDATE = 1001L;
    public static final long LOG_DELETE = 1002L;

    @Autowired
    StorageRepository storageRepository;

    @Autowired
    LogRepository logRepository;

    UserEntity userEntity;

    HttpServletRequest httpServletRequest;
    public StorageSystemService setHttpServletRequest(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
        this.userEntity = UserEntity.fromToken(this.httpServletRequest.getHeader(WorkflowApplication.HEADER_AUTHORIZATION));

        return this;
    }

    public ListResponse<Storage> getFolderList(String path) {

        if( path.endsWith("*") ) {
            path = path.replace("*", "%");
        } else {
            path += "%";
        }

        List<Storage> result = storageRepository.dir(path, PathUtils.getPathLevel(path));

        return new ListResponse<Storage>().setData(result);

    }

    public ObjectResponse<Storage> getFolder(String path) {

        Storage result = storageRepository.userCanReadInFolder(path, userEntity.getPermissionsList());

        if( result == null ) {
            return new ObjectResponse<>( path + " => No existe el folder, no esta visible, esta eliminado o usted no tiene permisos");
        }

        return new ObjectResponse<Storage>().setData(result);

    }

    public ObjectResponse<Storage> createStorage(String path, Storage requestStorage) {

        if( path.equals("/") ) {
            if( !userEntity.getRoles().contains("ADMIN") ) {
                return new ObjectResponse<>("No tiene permisos de admin en root /");
            }
        } else {
            String parentFolderPath = PathUtils.getParentFolder(path);

            ObjectResponse<Storage> result = getFolder(parentFolderPath);

            if (result.isError()) {
                return result;
            }
        }

        Storage storage = storageRepository.getStorage(path);
        if( storage != null ) {
            return new ObjectResponse<>("Ya existe el storage");
        }

        storage = new Storage();
        storage.setPath(path)
                .setCreator(userEntity.getUser())
                .setCreated( new Date())
                .setStatus(requestStorage.getStatus())
                .setClazzName(requestStorage.getClazzName())
                .setDescription(requestStorage.getDescription())
                .setImage(requestStorage.getImage())
                .setExtension(requestStorage.getExtension())
                ;

        storage = storageRepository.save(storage);

        logRepository.save( new Log()
                .setParent(storage)
                .setModifier(storage.getModifier())
                .setModified(new Date())
                .setComment("Registro creado")
                .setType(LOG_CREATE)
        );
        return new ObjectResponse<Storage>().setData(storage);

    }

    public ObjectResponse<Storage> updateStorage(String path, Storage requestStorage) {

        String parentFolderPath = PathUtils.getParentFolder(path);

        ObjectResponse<Storage> result = getFolder(parentFolderPath);

        if( result.isError() ) {
            return result;
        }

        Storage storage = storageRepository.getStorage(path);

        if( storage == null ) {
            return new ObjectResponse<>("No existe el storage");
        }

        storage
                .setModifier(userEntity.getUser())
                .setModifyDate( new Date())
                .setStatus(requestStorage.getStatus())
                .setClazzName(requestStorage.getClazzName())
                .setDescription(requestStorage.getDescription())
                .setImage(requestStorage.getImage())
                .setExtension(requestStorage.getExtension())
        ;

        storage = storageRepository.save(storage);

        logRepository.save( new Log()
                .setParent(storage)
                .setModifier(storage.getModifier())
                .setModified(new Date())
                .setComment("Registro modificado")
                .setType(LOG_UPDATE)
        );

        return new ObjectResponse<Storage>().setData(storage);

    }

    public ObjectResponse<Storage> deleteStorage(String path, Log log) {

        String parentFolderPath = PathUtils.getParentFolder(path);

        ObjectResponse<Storage> result = getFolder(parentFolderPath);

        if( result.isError() ) {
            return result;
        }

        Storage storage = storageRepository.getFile(path);

        if( storage == null ) {
            return new ObjectResponse<>("No existe el storage");
        }

        storage
                .setUserDeleter(userEntity.getUser())
                .setDeleted(true)
                .setDeletedDate(new Date())
        ;

        storage = storageRepository.save(storage);

        log
                .setParent(storage)
                .setModifier(storage.getModifier())
                .setModified(new Date())
                .setType(LOG_DELETE);

        logRepository.save( log );

        return new ObjectResponse<Storage>().setData(storage);

    }


}

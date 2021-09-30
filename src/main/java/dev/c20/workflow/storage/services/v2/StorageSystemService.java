package dev.c20.workflow.storage.services.v2;

import dev.c20.workflow.WorkflowApplication;
import dev.c20.workflow.auth.entities.UserEntity;
import dev.c20.workflow.commons.tools.PathUtils;
import dev.c20.workflow.storage.entities.Storage;
import dev.c20.workflow.storage.entities.adds.Log;
import dev.c20.workflow.storage.entities.adds.Value;
import dev.c20.workflow.storage.repositories.LogRepository;
import dev.c20.workflow.storage.repositories.StorageRepository;
import dev.c20.workflow.storage.repositories.ValueRepository;
import dev.c20.workflow.storage.services.requests.StorageRequest;
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

    @Autowired
    ValueRepository valueRepository;

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

        Storage result = storageRepository.userCanReadInFolder(path, userEntity.getRolesList());

        if( result == null ) {
            return new ObjectResponse<>( path + " => No existe el folder, no esta visible, esta eliminado o usted no tiene permisos");
        }

        return new ObjectResponse<Storage>().setData(result);

    }

    public ObjectResponse<Storage> createStorage(StorageRequest<Storage> storageRequest) {

        if( storageRequest.getPath().equals("/") ) {
            if( !userEntity.getRoles().contains("ADMIN") ) {
                return new ObjectResponse<>("No tiene permisos de admin en root /");
            }
        } else {
            String parentFolderPath = PathUtils.getParentFolder(storageRequest.getPath());

            ObjectResponse<Storage> result = getFolder(parentFolderPath);

            if (result.isError()) {
                return result;
            }
        }

        Storage storage = storageRepository.getStorage(storageRequest.getPath());
        if( storage != null ) {
            return new ObjectResponse<>("Ya existe el storage");
        }

        storage = new Storage();
        storage.setPath(storageRequest.getPath())
                .setCreator(userEntity.getUser())
                .setCreated( new Date())
                .setStatus(storageRequest.getRequest().getStatus())
                .setClazzName(storageRequest.getRequest().getClazzName())
                .setDescription(storageRequest.getRequest().getDescription())
                .setImage(storageRequest.getRequest().getImage())
                .setExtension(storageRequest.getRequest().getExtension())
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

    public ObjectResponse<Storage> updateStorage(StorageRequest<Storage> storageRequest) {

        String parentFolderPath = PathUtils.getParentFolder(storageRequest.getPath());

        ObjectResponse<Storage> result = getFolder(parentFolderPath);

        if( result.isError() ) {
            return result;
        }

        Storage storage = storageRepository.getStorage(storageRequest.getPath());

        if( storage == null ) {
            return new ObjectResponse<>("No existe el storage");
        }

        storage
                .setModifier(userEntity.getUser())
                .setModifyDate( new Date())
                .setStatus(storageRequest.getRequest().getStatus())
                .setClazzName(storageRequest.getRequest().getClazzName())
                .setDescription(storageRequest.getRequest().getDescription())
                .setImage(storageRequest.getRequest().getImage())
                .setExtension(storageRequest.getRequest().getExtension())
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

    public ObjectResponse<Storage> deleteStorage(StorageRequest<Storage> storageRequest) {

        String parentFolderPath = PathUtils.getParentFolder(storageRequest.getPath());

        ObjectResponse<Storage> result = getFolder(parentFolderPath);

        if( result.isError() ) {
            return result;
        }

        Storage storage = storageRepository.getFile(storageRequest.getPath());

        if( storage == null ) {
            return new ObjectResponse<>("No existe el storage");
        }

        storage
                .setUserDeleter(userEntity.getUser())
                .setDeleted(true)
                .setDeletedDate(new Date())
        ;

        storage = storageRepository.save(storage);

        Log log = new Log()
                .setParent(storage)
                .setModifier(storage.getModifier())
                .setModified(new Date())
                .setType(LOG_DELETE);

        logRepository.save( log );

        return new ObjectResponse<Storage>().setData(storage);

    }

    private StorageFile getStorageForAdds(String path ) {
        StorageFile storageFile = new StorageFile();

        String parentFolderPath = PathUtils.getParentFolder(path);

        Storage parentFolder = storageRepository.userCanUpdateInFolder(parentFolderPath, userEntity.getRolesList());

        if( parentFolder == null ) {
            storageFile.errorMessage = "No tiene permisos para el folder => " + parentFolderPath;
            return storageFile;
        }

        Storage storage = storageRepository.getStorage(path);

        if( storage == null ) {
            storageFile.errorMessage = "No existe el storage =>" + path;
            return storageFile;
        }

        storageFile.parentFolder = parentFolder;
        storageFile.file = storage;
        storageFile.isError = false;

        return storageFile;

    }

    public ListResponse<Value> getValues(String path) {
        StorageFile storageFile = getStorageForAdds(path);

        if( storageFile.isError ) {
            return new ListResponse<>(storageFile.errorMessage);
        }

        return new ListResponse<Value>().setData( valueRepository.getAll(storageFile.file));

    }

    public ObjectResponse<Value> addOrUpdateValue(StorageRequest<Value> storageRequest) {
        StorageFile storageFile = getStorageForAdds(storageRequest.getPath());

        if( storageFile.isError ) {
            return new ObjectResponse<>(storageFile.errorMessage);
        }

        Value value = valueRepository.save(storageRequest.getRequest());

        return new ObjectResponse<Value>().setData(value);

    }

    public ObjectResponse<Value> deleteValue(StorageRequest<Value> storageRequest) {
        StorageFile storageFile = getStorageForAdds(storageRequest.getPath());

        if( storageFile.isError ) {
            return new ObjectResponse<>(storageFile.errorMessage);
        }

        valueRepository.delete(storageRequest.getRequest());

        return new ObjectResponse<Value>().setData(storageRequest.getRequest());

    }

    class StorageFile {
        Storage parentFolder;
        Storage file;
        String errorMessage;
        boolean isError = true;
    }

}

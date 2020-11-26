package dev.c20.workflow.services;

import dev.c20.workflow.entities.Storage;
import dev.c20.workflow.entities.adds.*;
import dev.c20.workflow.repositories.*;
import dev.c20.workflow.services.responses.*;
import dev.c20.workflow.tools.PathUtils;
import dev.c20.workflow.tools.StringUtils;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

@Service
public class StorageService {

    protected final org.apache.commons.logging.Log logger = LogFactory.getLog(this.getClass());

    HttpServletRequest httpRequest;

    HttpServletResponse httpResponse;

    @Autowired
    EntityManager em;

    @Autowired
    StorageRepository storageRepository;

    @Autowired
    NoteRepository noteRepository;

    @Autowired
    ValueRepository valueRepository;

    @Autowired
    LogRepository logRepository;

    @Autowired
    PermRepository permRepository;

    @Autowired
    AttachRepository attachRepository;

    @Autowired
    DataRepository dataRepository;

    @Autowired
    FileDBStorageService fileDBStorageService;

    private String user = null;
    private String path = null;
    private boolean isFolder = false;
    private Storage requestedStorage = null;
    private Storage parentFolder = null;

    public StorageService setHttpServletResponse( HttpServletResponse httpResponse ) {
        this.httpResponse = httpResponse;
        return this;
    }

    static public final String USER_HEADER = "USER";
    public String getUser() {

        if( this.httpRequest.getHeader(USER_HEADER) != null )
            this.user = this.httpRequest.getHeader(USER_HEADER);

        return null;
    }

    public StorageService setHttpServletRequest( HttpServletRequest httpRequest ) {
        this.httpRequest  = httpRequest;
        this.path = getPath();
        if( StringUtils.isEmpty(path) )
            return this;
        this.isFolder = PathUtils.isFolder(path);

        if( PathUtils.getPathLevel(this.path) > 0 ) {
            String parent = PathUtils.getParentFolder(this.path);
            parentFolder = storageRepository.getFolder(parent);
        }

        if( this.isFolder ) {
            logger.info("Resquest is for folder");
            requestedStorage = storageRepository.getFolder(this.path);
        } else {
            logger.info("Resquest is for file");
            requestedStorage = storageRepository.getFile(this.path);
        }

        return this;
    }

    public String getPath() {

        String path = PathUtils.getPathFromLevel(httpRequest.getRequestURI(),4);
        try {
            path =  URLDecoder.decode(path, "UTF-8");
            return path;
        } catch (UnsupportedEncodingException e) {
            logger.error(e);
        }
        return null;
    }

    public boolean canCreate() {
        return hasPermition(true, null, null, null, true);
    }

    public boolean canRead() {
        return hasPermition(false, true, null, null, true);
    }

    public boolean canUpdate() {
        return hasPermition(false, false, true, null, true);
    }

    public boolean canDelete() {
        return hasPermition(false, false, false, true, true);
    }

    public boolean hasPermition(Boolean canCreate, Boolean canRead, Boolean canUpdate, Boolean canDelete, Boolean canAdmin) {

        if( this.user == null )
            return false;

        CriteriaBuilder qb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = qb.createQuery(Long.class);
        Root<Perm> permition = cq.from(Perm.class);
        Path<Boolean> createPath = permition.get("create");
        Path<Boolean> readPath   = permition.get("read");
        Path<Boolean> updatePath = permition.get("update");
        Path<Boolean> deletePath = permition.get("delete");
        Path<Boolean> adminPath  = permition.get("admin");


        cq.select(qb.count(cq.from(Perm.class)));

        List<Predicate> predicates = new ArrayList<>();

        if( canCreate != null )
            predicates.add( qb.and( qb.equal(createPath,canCreate)) );

        if( canRead != null )
            predicates.add( qb.and( qb.equal(readPath,canRead)) );

        if( canUpdate != null )
            predicates.add( qb.and( qb.equal(updatePath,canUpdate)) );

        if( canDelete != null )
            predicates.add( qb.and( qb.equal(deletePath,canDelete)) );

        if( canAdmin != null )
            predicates.add( qb.and( qb.equal(adminPath,canAdmin)) );

        cq.where(
                qb.and(
                        predicates.toArray(new Predicate[predicates.size()])
                )
        );
        Long count = em.createQuery(cq).getSingleResult();


        return count > 0;
    }

    public StorageResponse addFolder(Storage storage) {
        if( !this.isFolder )
            return new StorageResponse("En el path no se manda un folder");
        logger.info("addFolder:" + path);

        if( requestedStorage != null )
            return new StorageResponse("Ya existe el folder");

        if( PathUtils.getPathLevel(this.path) > 0 && parentFolder == null )
            return new StorageResponse("No existe el parent folder " + PathUtils.getParentFolder(this.path));

        if( parentFolder != null && parentFolder.getRestrictedByPerm() && !canCreate() )
            return new StorageResponse("El usuario no tiene permisos para crear en " + PathUtils.getParentFolder(this.path));

        requestedStorage = new Storage()
                .setPath(this.path)
                .setPropertiesFrom(storage)
                .setCreator(this.user)
                .setLocked(false)
                .setVisible(true)
                .setDeleted(false)
                .setReadOnly(false)
                .setRestrictedByPerm(false)
                .setChildrenRestrictedByPerm(false);

        storageRepository.save(requestedStorage);

        return new StorageResponse(requestedStorage);

    }

    public StorageResponse delStorage() {

        if( requestedStorage == null )
            return new StorageResponse("No existe el file/folder");

        if( parentFolder.getRestrictedByPerm() && !canDelete() )
            return new StorageResponse("El usuario no tiene permisos en el folder para eliminar en " + this.path);

        if( requestedStorage.getRestrictedByPerm()  && !canDelete() )
            return new StorageResponse("El usuario no tiene permisos en el file para eliminar en " + this.path);

        if( requestedStorage.getDeleted() )
            return new StorageResponse(requestedStorage);

        requestedStorage.setLocked(true)
                .setVisible(false)
                .setDeleted(true)
                .setUserDeleter(this.user)
                .setDeletedDate(new Date());

        storageRepository.save(requestedStorage);

        return new StorageResponse(requestedStorage);

    }

    public StorageResponse updateStorage(Storage request) {

        if( requestedStorage == null )
            return new StorageResponse("No existe el file/folder");

        if( parentFolder.getRestrictedByPerm() && !canUpdate() )
            return new StorageResponse("El usuario no tiene permisos en el folder para modificar en " + this.path);

        if( requestedStorage.getRestrictedByPerm()  && !canUpdate() )
            return new StorageResponse("El usuario no tiene permisos en el file/folder para modificar en " + this.path);

        if( request.getLocked() != null )
            requestedStorage.setLocked(request.getLocked());

        if( request.getVisible() != null )
            requestedStorage.setVisible(request.getVisible());

        if( request.getImage() != null )
            requestedStorage.setImage(request.getImage());

        if( request.getClazzName() != null )
            requestedStorage.setClazzName(request.getClazzName());

        if( request.getStatus() != null )
            requestedStorage.setStatus(request.getStatus());

        if( request.getChildrenRestrictedByPerm() != null )
            requestedStorage.setChildrenRestrictedByPerm(request.getChildrenRestrictedByPerm());

        if( request.getRestrictedByPerm() != null )
            requestedStorage.setRestrictedByPerm(request.getRestrictedByPerm());

        storageRepository.save(requestedStorage);

        return new StorageResponse(requestedStorage);

    }

    public StorageResponse getStorage(Boolean folder) {
        if( requestedStorage == null )
            return new StorageResponse("No existe el file/folder");

        if( requestedStorage.getLevel() > 0 && parentFolder.getRestrictedByPerm() && !canRead() )
            return new StorageResponse("El usuario no tiene permisos en el folder para leer en " + this.path);

        if( requestedStorage.getRestrictedByPerm()  && !canRead() )
            return new StorageResponse("El usuario no tiene permisos en el file/folder para leer en " + this.path);

        if( requestedStorage.getIsFolder() != folder )
            return new StorageResponse("file/folder no es del tipo solicitado " + this.path);

        if( requestedStorage.getDeleted())
            return new StorageResponse("file/folder eliminado " + this.path);

        return new StorageResponse(requestedStorage);

    }

    public StorageResponse addFile(Storage storage) {
        if( this.isFolder )
            return new StorageResponse("En el path no se manda un file");
        logger.info("addFolder:" + path);

        if( requestedStorage != null )
            return new StorageResponse("Ya existe el file");

        if( parentFolder == null )
            return new StorageResponse("No existe el parent folder " + PathUtils.getParentFolder(this.path));

        if( parentFolder.getRestrictedByPerm() && !canCreate() )
            return new StorageResponse("El usuario no tiene permisos para crear en " + PathUtils.getParentFolder(this.path));

        requestedStorage = new Storage()
                .setPath(this.path)
                .setPropertiesFrom(storage)
                .setCreator(this.user)
                .setLocked(false)
                .setVisible(true)
                .setDeleted(false)
                .setReadOnly(false)
                .setRestrictedByPerm(false)
                .setChildrenRestrictedByPerm(false);

        storageRepository.save(requestedStorage);

        return new StorageResponse(requestedStorage);

    }

    private String getStorageForAdds() {
        if( requestedStorage == null )
            return "No existe el storage";

        if( parentFolder != null && parentFolder.getRestrictedByPerm() && !canUpdate() )
            return "El usuario no tiene permisos para modificar en " + PathUtils.getParentFolder(this.path);

        if( requestedStorage.getRestrictedByPerm() && !canUpdate() )
            return "El usuario no tiene permisos para modificar en " + PathUtils.getParentFolder(this.path);

        return null;
    }

    private String getStorageForReads() {
        if( requestedStorage == null )
            return "No existe el storage";

        if( parentFolder != null && parentFolder.getRestrictedByPerm() && !canRead() )
            return "El usuario no tiene permisos para leer en " + PathUtils.getParentFolder(this.path);

        if( requestedStorage.getRestrictedByPerm() && !canRead() )
            return "El usuario no tiene permisos para leer en " + PathUtils.getParentFolder(this.path);

        return null;
    }

    public ListResponse getNotes() {
        String error = getStorageForReads();
        if( error != null )
            return new ListResponse().setErrorDescription(error);

        return new ListResponse(noteRepository.getAll(requestedStorage));

    }

    public ObjectResponse addNote(Note note) {
        String error = getStorageForAdds();
        if( error != null )
            return new ObjectResponse().setErrorDescription(error);

        Note obj = new Note();
        obj.setCreator(this.user);
        obj.setCreated(new Date());
        obj.setComment(note.getComment());
        obj.setImage(note.getImage());
        obj.setParent(requestedStorage);

        noteRepository.save(obj);

        return new ObjectResponse(obj);

    }

    public ObjectResponse addLog(Log log) {

        Log obj = new Log();
        obj.setModifier(this.user);
        obj.setModified(new Date());
        obj.setComment(log.getComment());
        obj.setType(log.getType());
        obj.setParent(requestedStorage);

        logRepository.save(log);

        return new ObjectResponse(obj);

    }

    public ListResponse getLog() {
        String error = getStorageForReads();
        if( error != null )
            return new ListResponse().setErrorDescription(error);

        return new ListResponse(logRepository.getAll(requestedStorage));

    }

    public ListResponse getValues() {
        String error = getStorageForReads();
        if( error != null )
            return new ListResponse().setErrorDescription(error);

        return new ListResponse(valueRepository.getAll(requestedStorage));

    }

    public ObjectResponse addValue(Value value) {
        String error = getStorageForAdds();
        if( error != null )
            return new ObjectResponse().setErrorDescription(error);

        Value obj = new Value();
        obj.setName(value.getName());
        obj.setValue(value.getValue());
        obj.setParent(requestedStorage);

        valueRepository.save(obj);

        return new ObjectResponse(obj);

    }

    public ObjectResponse updateValue(Value value) {
        String error = getStorageForAdds();
        if( error != null )
            return new ObjectResponse().setErrorDescription(error);

        Value obj = valueRepository.getByName(requestedStorage,value.getName());
        obj.setValue(value.getValue());
        obj.setParent(requestedStorage);

        valueRepository.save(obj);

        return new ObjectResponse(obj);

    }

    public ObjectResponse deleteValue(Value value) {
        String error = getStorageForAdds();
        if( error != null )
            return new ObjectResponse().setErrorDescription(error);

        Value obj = valueRepository.getByName(requestedStorage,value.getName());
        obj.setParent(requestedStorage);

        valueRepository.delete(obj);

        return new ObjectResponse(obj);

    }


    public ListResponse getPerms() {
        String error = getStorageForReads();
        if( error != null )
            return new ListResponse().setErrorDescription(error);

        return new ListResponse(permRepository.getAll(requestedStorage));

    }


    public ObjectResponse addPerm(Perm perm) {
        String error = getStorageForAdds();
        if( error != null )
            return new ObjectResponse().setErrorDescription(error);

        Perm obj = new Perm();
        obj.setUser(perm.getUser());
        obj.setCanCreate(perm.getCanCreate());
        obj.setCanRead(perm.getCanRead());
        obj.setCanUpdate(perm.getCanUpdate());
        obj.setCanDelete(perm.getCanDelete());
        obj.setCanAdmin(perm.getCanAdmin());
        obj.setParent(requestedStorage);

        permRepository.save(obj);

        return new ObjectResponse(obj);

    }

    public ObjectResponse updatePerm(Perm perm) {
        String error = getStorageForAdds();
        if( error != null )
            return new ObjectResponse().setErrorDescription(error);

        Perm obj = permRepository.getByUser(requestedStorage,perm.getUser());
        obj.setUser(perm.getUser())
            .setCanCreate(perm.getCanCreate())
            .setCanRead(perm.getCanRead())
            .setCanUpdate(perm.getCanUpdate())
            .setCanDelete(perm.getCanDelete())
            .setCanAdmin(perm.getCanAdmin())
            .setParent(requestedStorage);

        permRepository.save(obj);

        return new ObjectResponse(obj);

    }

    public ObjectResponse deletePerm(Perm perm) {
        String error = getStorageForAdds();
        if( error != null )
            return new ObjectResponse().setErrorDescription(error);

        Perm obj = permRepository.getByUser(requestedStorage,perm.getUser());
        obj.setParent(requestedStorage);

        permRepository.delete(obj);

        return new ObjectResponse(obj);

    }

    public ListResponse getAttachments() {
        String error = getStorageForReads();
        if( error != null )
            return new ListResponse().setErrorDescription(error);

        return new ListResponse(attachRepository.getAll(requestedStorage));

    }

    public ListResponse addAttach(MultipartFile[] attachments) {
        String error = getStorageForAdds();
        if( error != null )
            return new ListResponse().setErrorDescription(error);

        List<Attach> attached = new ArrayList<>();

        try {

            Arrays.asList(attachments).stream().forEach(file -> {
                Long fileId = fileDBStorageService.save(file,"12121213131");
                Attach obj = new Attach()
                        .setParent(requestedStorage)
                        .setFile(fileId)
                        .setModified(new Date())
                        .setModifier(this.user)
                        .setName(file.getOriginalFilename());
                attached.add(obj);
                attachRepository.save(obj);
            });

            return new ListResponse(attached);
        } catch (Exception e) {
            logger.error(e);
            //e.printStackTrace(logger.)
            return new ListResponse().setErrorDescription("Fail to upload files!");
        }



    }

    public ObjectResponse updateAttach(Attach attach) {
        String error = getStorageForAdds();
        if( error != null )
            return new ObjectResponse().setErrorDescription(error);

        Attach obj = attachRepository.getByName(requestedStorage,attach.getName());
        obj.setFile(attach.getFile())
                .setModified(new Date())
                .setModifier(this.user)
                .setName(attach.getName());

        attachRepository.save(obj);

        return new ObjectResponse(obj);

    }

    public ObjectResponse deleteAttach(Attach attach) {
        String error = getStorageForAdds();
        if( error != null )
            return new ObjectResponse().setErrorDescription(error);

        Attach obj = attachRepository.getByName(requestedStorage,attach.getName());

        attachRepository.delete(obj);

        return new ObjectResponse(obj);

    }

    public ObjectResponse getData() {
        String error = getStorageForReads();
        if( error != null )
            return new ObjectResponse().setErrorDescription(error);

        Data data = dataRepository.getByParent(requestedStorage.getId());
        try {
            Object obj = StringUtils.JSONFromString(data.getData());
            return new ObjectResponse(obj);
        } catch( Exception ex ) {
            return new ObjectResponse().setErrorDescription("Error al convertir String a JSON");
        }

    }

    public ObjectResponse addData(Map<String,Object> data) {
        String error = getStorageForAdds();
        if( error != null )
            return new ObjectResponse().setErrorDescription(error);


        try {
            Data obj = new Data()
                    .setParent(requestedStorage.getId())
                    .setData(StringUtils.toJSON(data));
            dataRepository.save(obj);

            return new ObjectResponse(obj);
        } catch( Exception ex ) {
            return new ObjectResponse().setErrorDescription("No se puede convertir a JSON");
        }


    }

    public ObjectResponse updateData(Map<String,Object> data) {
        String error = getStorageForAdds();
        if( error != null )
            return new ObjectResponse().setErrorDescription(error);

        try {
            Data obj = dataRepository.getByParent(requestedStorage.getId());
            obj.setData(StringUtils.toJSON(data) );
            dataRepository.save(obj);

            return new ObjectResponse(obj);
        } catch( Exception ex ) {
            return new ObjectResponse().setErrorDescription("No se puede convertir a JSON");
        }


    }

    public ObjectResponse deleteData() {
        String error = getStorageForAdds();
        if( error != null )
            return new ObjectResponse().setErrorDescription(error);

        Data obj = dataRepository.getByParent(requestedStorage.getId());

        dataRepository.delete(obj);

        return new ObjectResponse(obj);

    }

}

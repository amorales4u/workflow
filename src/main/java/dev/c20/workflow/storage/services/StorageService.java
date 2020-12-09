package dev.c20.workflow.storage.services;

import dev.c20.workflow.WorkflowApplication;
import dev.c20.workflow.commons.auth.UserEntity;
import dev.c20.workflow.storage.entities.Storage;
import dev.c20.workflow.storage.entities.adds.*;
import dev.c20.workflow.files.services.FileDBStorageService;
import dev.c20.workflow.storage.services.responses.*;
import dev.c20.workflow.storage.repositories.*;
import dev.c20.workflow.commons.tools.PathUtils;
import dev.c20.workflow.commons.tools.StringUtils;
import org.apache.commons.cli.*;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

@Service
public class StorageService  {

    protected final org.apache.commons.logging.Log logger = LogFactory.getLog(this.getClass());

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

    EntityManager em;
    @Autowired
    public StorageService setEntityManager( EntityManager em) {
        logger.info( "Set entityManager");
        this.em = em;
        return this;
    }


    private UserEntity user = null;
    private String path = null;
    private boolean isFolder = false;
    private Storage requestedStorage  = null;
    private Storage parentFolder = null;
    private HttpServletResponse httpResponse = null;
    private HttpServletRequest httpRequest = null;

    public Boolean isFolder() {
        return this.isFolder;
    }

    public StorageService setParentFolder(Storage parentFolder ) {
        this.parentFolder = parentFolder;
        return this;
    }

    public StorageService setRequestedStorage(Storage requestedStorage ) {
        this.requestedStorage = requestedStorage;
        return this;
    }

    public Storage getParentFolder() {
        return this.parentFolder;
    }

    public Storage getRequestedStorage() {
        return this.requestedStorage;
    }

    public StorageService readStorageAndParentStorage() {

        if( PathUtils.getPathLevel(this.getPath()) > 0 ) {
            String parent = PathUtils.getParentFolder(this.getPath());
            this.setParentFolder( storageRepository.getFolder(parent) );
        }

        if( this.isFolder() ) {
            logger.info("Resquest is for folder");
            setRequestedStorage( storageRepository.getFolder(this.getPath()) );
        } else {
            logger.info("Resquest is for file");
            setRequestedStorage(  storageRepository.getFile(this.getPath()) );
        }

        return this;

    }

    public StorageService setHttpServletResponse( HttpServletResponse httpResponse ) {
        this.httpResponse = httpResponse;
        return this;
    }

    public UserEntity getUser() {

        this.user = UserEntity.fromToken(this.httpRequest.getHeader(WorkflowApplication.HEADER_AUTHORIZATION));

        return null;
    }

    public StorageService setHttpServletRequest( HttpServletRequest httpRequest ) {
        this.httpRequest  = httpRequest;
        this.path = getPath();
        if( StringUtils.isEmpty(path) )
            return this;
        this.isFolder = PathUtils.isFolder(path);

        if( PathUtils.getPathLevel(this.getPath()) > 0 ) {
            String parent = PathUtils.getParentFolder(this.getPath());
            this.parentFolder = storageRepository.getFolder(parent);
        }

        if( this.isFolder() ) {
            logger.info("Resquest is for folder");
            this.requestedStorage = storageRepository.getFolder(this.getPath());
        } else {
            logger.info("Resquest is for file");
            this.requestedStorage = storageRepository.getFile(this.getPath());
        }

        return this;
    }

    public String getPath() {

        int level =  4;
        String path = PathUtils.getPathFromLevel(httpRequest.getRequestURI(),level);
        try {
            path =  URLDecoder.decode(path, "UTF-8");
            return path;
        } catch (UnsupportedEncodingException e) {
            logger.error(e);
        }
        return null;
    }

    public boolean canCreate() {
        return hasPermition(true, null, null, null, true, false);
    }

    public boolean canRead() {
        return hasPermition(false, true, null, null, true, false);
    }

    public boolean canUpdate() {
        return hasPermition(false, false, true, null, true, false);
    }

    public boolean canDelete() {
        return hasPermition(false, false, false, true, true, false);
    }

    public boolean canAdmin() {
        return hasPermition(false, null, null, null, true, false);
    }

    public boolean canSend() {
        return hasPermition(false, null, null, null, true, true);
    }


    public boolean hasPermition(Boolean canCreate, Boolean canRead, Boolean canUpdate, Boolean canDelete, Boolean canAdmin, Boolean canSend) {

        if( this.getUser() == null )
            return false;

        CriteriaBuilder qb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = qb.createQuery(Long.class);
        Root<Perm> perm = cq.from(Perm.class);
        Path<Boolean> createPath = perm.get("create");
        Path<Boolean> readPath   = perm.get("read");
        Path<Boolean> updatePath = perm.get("update");
        Path<Boolean> deletePath = perm.get("delete");
        Path<Boolean> adminPath  = perm.get("admin");
        Path<Boolean> sendPath  = perm.get("send");


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

        if( canSend != null )
            predicates.add( qb.and( qb.equal(sendPath,canSend)) );

        cq.where(
                qb.and(
                        predicates.toArray(new Predicate[0])
                )
        );
        Long count = em.createQuery(cq).getSingleResult();


        return count > 0;
    }

    public ObjectResponse addFolder(Storage storage) {
        if( !this.isFolder() )
            return new ObjectResponse("En el path no se manda un folder");
        logger.info("addFolder:" + this.getPath() );

        if( this.getRequestedStorage() != null )
            return new ObjectResponse("Ya existe el folder");

        if( PathUtils.getPathLevel(this.getPath()) > 0 && this.getParentFolder() == null )
            return new ObjectResponse("No existe el parent folder " + PathUtils.getParentFolder(this.getPath()));

        if( this.getParentFolder() != null && this.getParentFolder().getRestrictedByPerm() && !canCreate() )
            return new ObjectResponse("El usuario no tiene permisos para crear en " + PathUtils.getParentFolder(this.getPath()));

        this.setRequestedStorage(new Storage()
                .setPath(this.getPath())
                .setPropertiesFrom(storage)
                .setCreator(this.getUser().getUser())
                .setLocked(false)
                .setVisible(true)
                .setDeleted(false)
                .setReadOnly(false)
                .setRestrictedByPerm(false)
                .setChildrenRestrictedByPerm(false));

        storageRepository.save(this.getRequestedStorage());

        return new ObjectResponse(this.getRequestedStorage());

    }

    public ObjectResponse delStorage() {

        if( this.getRequestedStorage() == null )
            return new ObjectResponse("No existe el file/folder");

        if( this.getParentFolder().getRestrictedByPerm() && ( !canDelete() || !canAdmin() ))
            return new ObjectResponse("El usuario no tiene permisos en el folder para eliminar en " + this.getPath());

        if( this.getRequestedStorage().getRestrictedByPerm()  && ( !canDelete() || !canAdmin() ))
            return new ObjectResponse("El usuario no tiene permisos en el file para eliminar en " + this.getPath());

        if( this.getRequestedStorage().getDeleted() )
            return new ObjectResponse(this.getRequestedStorage());

        this.getRequestedStorage().setLocked(true)
                .setVisible(false)
                .setDeleted(true)
                .setUserDeleter(this.getUser().getUser())
                .setDeletedDate(new Date());

        storageRepository.save(this.getRequestedStorage());

        return new ObjectResponse(this.getRequestedStorage());

    }

    public ObjectResponse updateStorage(Storage request) {

        if( this.getRequestedStorage() == null )
            return new ObjectResponse("No existe el file/folder");

        if( this.getParentFolder().getRestrictedByPerm() && ( !canUpdate() || !canAdmin() ))
            return new ObjectResponse("El usuario no tiene permisos en el folder para modificar en " + this.getPath());

        if( this.getRequestedStorage().getRestrictedByPerm()  && ( !canUpdate() || !canAdmin() ))
            return new ObjectResponse("El usuario no tiene permisos en el file/folder para modificar en " + this.getPath());

        if( request.getLocked() != null )
            this.getRequestedStorage().setLocked(request.getLocked());

        if( request.getVisible() != null )
            this.getRequestedStorage().setVisible(request.getVisible());

        if( request.getImage() != null )
            this.getRequestedStorage().setImage(request.getImage());

        if( request.getClazzName() != null )
            this.getRequestedStorage().setClazzName(request.getClazzName());

        if( request.getStatus() != null )
            this.getRequestedStorage().setStatus(request.getStatus());

        if( request.getChildrenRestrictedByPerm() != null )
            this.getRequestedStorage().setChildrenRestrictedByPerm(request.getChildrenRestrictedByPerm());

        if( request.getRestrictedByPerm() != null )
            this.getRequestedStorage().setRestrictedByPerm(request.getRestrictedByPerm());

        storageRepository.save(this.getRequestedStorage());

        return new ObjectResponse(this.getRequestedStorage());

    }

    public ObjectResponse getStorage(Boolean folder) {
        if( this.getRequestedStorage() == null )
            return new ObjectResponse("No existe el file/folder");

        if( this.getRequestedStorage().getLevel() > 0 && this.getParentFolder().getRestrictedByPerm() && ( !canRead() || !canAdmin() ) )
            return new ObjectResponse("El usuario no tiene permisos en el folder para leer en " + this.getPath());


        if( this.getRequestedStorage().getRestrictedByPerm()  && ( !canRead() || !canAdmin() ))
            return new ObjectResponse("El usuario no tiene permisos en el file/folder para leer en " + this.getPath());

        if( this.getRequestedStorage().getIsFolder() != folder )
            return new ObjectResponse("file/folder no es del tipo solicitado " + this.getPath());

        if( this.getRequestedStorage().getDeleted())
            return new ObjectResponse("file/folder eliminado " + this.getPath());

        return new ObjectResponse(this.getRequestedStorage());

    }

    public ObjectResponse addFile(Storage storage) {
        if( this.isFolder() )
            return new ObjectResponse("En el path no se manda un file");
        logger.info("addFolder:" + this.getPath() );

        if( this.getRequestedStorage() != null )
            return new ObjectResponse("Ya existe el file");

        if( this.getParentFolder() == null )
            return new ObjectResponse("No existe el parent folder " + PathUtils.getParentFolder(this.getPath()));

        if( this.getParentFolder().getRestrictedByPerm() && ( !canCreate() || !canAdmin() ))
            return new ObjectResponse("El usuario no tiene permisos para crear en " +
                    PathUtils.getParentFolder(this.getPath()));

        this.setRequestedStorage(  new Storage()
                .setPath(this.getPath())
                .setPropertiesFrom(storage)
                .setCreator(this.getUser().getUser())
                .setLocked(false)
                .setVisible(true)
                .setDeleted(false)
                .setReadOnly(false)
                .setRestrictedByPerm(false)
                .setChildrenRestrictedByPerm(false));

        storageRepository.save(this.getRequestedStorage());

        return new ObjectResponse(this.getRequestedStorage());

    }

    private String getStorageForCreates() {
        if( this.getRequestedStorage() == null )
            return "No existe el storage";

        if( this.getParentFolder() != null && this.getParentFolder().getRestrictedByPerm() && !canCreate() )
            return "El usuario no tiene permisos para modificar en " + PathUtils.getParentFolder(this.getPath());

        if( this.getRequestedStorage().getRestrictedByPerm() && !canCreate() )
            return "El usuario no tiene permisos para modificar en " + PathUtils.getParentFolder(this.getPath());

        return null;
    }

    private String getStorageForUpdates() {
        if( this.getRequestedStorage() == null )
            return "No existe el storage";

        if( this.getParentFolder() != null && this.getParentFolder().getRestrictedByPerm() && !canUpdate() )
            return "El usuario no tiene permisos para modificar en " + PathUtils.getParentFolder(this.getPath());

        if( this.getRequestedStorage().getRestrictedByPerm() && !canUpdate() )
            return "El usuario no tiene permisos para modificar en " + PathUtils.getParentFolder(this.getPath());

        return null;
    }

    private String getStorageForReads() {
        if( this.getRequestedStorage() == null )
            return "No existe el storage";

        if( this.getParentFolder() != null && this.getParentFolder().getRestrictedByPerm() && !canRead() )
            return "El usuario no tiene permisos para leer en " + PathUtils.getParentFolder(this.getPath());

        if( this.getRequestedStorage().getRestrictedByPerm() && !canRead() )
            return "El usuario no tiene permisos para leer en " + PathUtils.getParentFolder(this.getPath());

        return null;
    }

    private String getStorageForDeletes() {
        if( this.getRequestedStorage() == null )
            return "No existe el storage";

        if( this.getParentFolder() != null && this.getParentFolder().getRestrictedByPerm() && !canDelete() )
            return "El usuario no tiene permisos para modificar en " + PathUtils.getParentFolder(this.getPath());

        if( this.getRequestedStorage().getRestrictedByPerm() && !canDelete() )
            return "El usuario no tiene permisos para modificar en " + PathUtils.getParentFolder(this.getPath());

        return null;
    }

    private String getStorageForAdmins() {
        if( this.getRequestedStorage() == null )
            return "No existe el storage";

        if( this.getParentFolder() != null && this.getParentFolder().getRestrictedByPerm() && !canAdmin() )
            return "El usuario no tiene permisos para administrar en " + PathUtils.getParentFolder(this.getPath());

        if( this.getRequestedStorage().getRestrictedByPerm() && !canAdmin() )
            return "El usuario no tiene permisos para administrar en " + PathUtils.getParentFolder(this.getPath());

        return null;
    }

    private String getStorageForSenders() {
        if( this.getRequestedStorage() == null )
            return "No existe el storage";

        if( this.getParentFolder() != null && this.getParentFolder().getRestrictedByPerm() && !canSend() )
            return "El usuario no tiene permisos para administrar en " + PathUtils.getParentFolder(this.getPath());

        if( this.getRequestedStorage().getRestrictedByPerm() && !canSend() )
            return "El usuario no tiene permisos para administrar en " + PathUtils.getParentFolder(this.getPath());

        return null;
    }


    public ListResponse getNotes() {
        String error = getStorageForReads();
        if( error != null )
            return new ListResponse().setErrorDescription(error);

        return new ListResponse(noteRepository.getAll(this.getRequestedStorage()));

    }

    public ObjectResponse addNote(Note note) {
        String error = getStorageForUpdates();
        if( error != null )
            return new ObjectResponse().setErrorDescription(error);

        Note obj = new Note();
        obj.setCreator(this.getUser().getUser());
        obj.setCreated(new Date());
        obj.setComment(note.getComment());
        obj.setImage(note.getImage());
        obj.setParent(this.getRequestedStorage());

        noteRepository.save(obj);

        return new ObjectResponse(obj);

    }

    public ObjectResponse addLog(Log log) {

        Log obj = new Log();
        obj.setModifier(this.getUser().getUser());
        obj.setModified(new Date());
        obj.setComment(log.getComment());
        obj.setType(log.getType());
        obj.setParent(this.getRequestedStorage());

        logRepository.save(log);

        return new ObjectResponse(obj);

    }

    public ListResponse getLog() {
        String error = getStorageForReads();
        if( error != null )
            return new ListResponse().setErrorDescription(error);

        return new ListResponse(logRepository.getAll(this.getRequestedStorage()));

    }

    public ListResponse getValues() {
        String error = getStorageForReads();
        if( error != null )
            return new ListResponse().setErrorDescription(error);

        return new ListResponse(valueRepository.getAll(this.getRequestedStorage()));

    }

    public ObjectResponse addValue(Value value) {
        String error = getStorageForUpdates();
        if( error != null )
            return new ObjectResponse().setErrorDescription(error);

        Value obj = new Value();
        obj.setName(value.getName());
        obj.setValue(value.getValue());
        obj.setParent(this.getRequestedStorage());

        valueRepository.save(obj);

        return new ObjectResponse(obj);

    }

    public ObjectResponse updateValue(Value value) {
        String error = getStorageForUpdates();
        if( error != null )
            return new ObjectResponse().setErrorDescription(error);

        Value obj = valueRepository.getByName(this.getRequestedStorage(),value.getName());
        obj.setValue(value.getValue());
        obj.setParent(this.getRequestedStorage());

        valueRepository.save(obj);

        return new ObjectResponse(obj);

    }

    public ObjectResponse deleteValue(Value value) {
        String error = getStorageForUpdates();
        if( error != null )
            return new ObjectResponse().setErrorDescription(error);

        Value obj = valueRepository.getByName(this.getRequestedStorage(),value.getName());
        obj.setParent(this.getRequestedStorage());

        valueRepository.delete(obj);

        return new ObjectResponse(obj);

    }


    public ListResponse getPerms() {
        String error = getStorageForReads();
        if( error != null )
            return new ListResponse().setErrorDescription(error);

        return new ListResponse(permRepository.getAll(this.getRequestedStorage()));

    }


    public ObjectResponse addPerm(Perm perm) {
        String error = getStorageForUpdates();
        if( error != null )
            return new ObjectResponse().setErrorDescription(error);

        Perm obj = new Perm();
        obj.setUser(perm.getUser());
        obj.setCanCreate(perm.getCanCreate());
        obj.setCanRead(perm.getCanRead());
        obj.setCanUpdate(perm.getCanUpdate());
        obj.setCanDelete(perm.getCanDelete());
        obj.setCanAdmin(perm.getCanAdmin());
        obj.setParent(this.getRequestedStorage());

        permRepository.save(obj);

        return new ObjectResponse(obj);

    }

    public ObjectResponse updatePerm(Perm perm) {
        String error = getStorageForUpdates();
        if( error != null )
            return new ObjectResponse().setErrorDescription(error);

        Perm obj = permRepository.getByUser(this.getRequestedStorage(),perm.getUser());
        obj.setUser(perm.getUser())
            .setCanCreate(perm.getCanCreate())
            .setCanRead(perm.getCanRead())
            .setCanUpdate(perm.getCanUpdate())
            .setCanDelete(perm.getCanDelete())
            .setCanAdmin(perm.getCanAdmin())
            .setParent(this.getRequestedStorage());

        permRepository.save(obj);

        return new ObjectResponse(obj);

    }

    public ObjectResponse deletePerm(Perm perm) {
        String error = getStorageForUpdates();
        if( error != null )
            return new ObjectResponse().setErrorDescription(error);

        Perm obj = permRepository.getByUser(this.getRequestedStorage(),perm.getUser());
        obj.setParent(this.getRequestedStorage());

        permRepository.delete(obj);

        return new ObjectResponse(obj);

    }

    public ListResponse getAttachments() {
        String error = getStorageForReads();
        if( error != null )
            return new ListResponse().setErrorDescription(error);

        return new ListResponse(attachRepository.getAll(this.getRequestedStorage()));

    }

    public ResponseEntity<?> downloadAttach( Attach attach) {
        String error = getStorageForUpdates();
        if( error != null )
            return new ObjectResponse().setErrorDescription(error).error();

        byte[] file = fileDBStorageService.load(null, attach.getId(), "la llabe");

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + attach.getName());

        return ResponseEntity.ok()
                .headers(headers)
                //.contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(file);

    }

    public ListResponse addAttach(MultipartFile[] attachments) {
        String error = getStorageForUpdates();
        if( error != null )
            return new ListResponse().setErrorDescription(error);

        List<Attach> attached = new ArrayList<>();

        try {

            Arrays.asList(attachments).stream().forEach(file -> {
                Long fileId = fileDBStorageService.save(file,"12121213131");
                Attach obj = new Attach()
                        .setParent(this.getRequestedStorage())
                        .setFile(fileId)
                        .setModified(new Date())
                        .setModifier(this.getUser().getUser())
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
        String error = getStorageForUpdates();
        if( error != null )
            return new ObjectResponse().setErrorDescription(error);

        Attach obj = attachRepository.getByName(this.getRequestedStorage(),attach.getName());
        obj.setFile(attach.getFile())
                .setModified(new Date())
                .setModifier(this.getUser().getUser())
                .setName(attach.getName());

        attachRepository.save(obj);

        return new ObjectResponse(obj);

    }

    public ObjectResponse deleteAttach(Attach attach) {
        String error = getStorageForUpdates();
        if( error != null )
            return new ObjectResponse().setErrorDescription(error);

        Attach obj = attachRepository.getByName(this.getRequestedStorage(),attach.getName());

        attachRepository.delete(obj);

        return new ObjectResponse(obj);

    }

    public ObjectResponse getData() {
        String error = getStorageForReads();
        if( error != null )
            return new ObjectResponse().setErrorDescription(error);

        Data data = dataRepository.getByParent(this.getRequestedStorage().getId());
        try {
            Object obj = StringUtils.JSONFromString(data.getData());
            return new ObjectResponse(obj);
        } catch( Exception ex ) {
            return new ObjectResponse().setErrorDescription("Error al convertir String a JSON");
        }

    }

    public ObjectResponse addData(Map<String,Object> data) {
        String error = getStorageForUpdates();
        if( error != null )
            return new ObjectResponse().setErrorDescription(error);


        try {
            Data obj = new Data()
                    .setParent(this.getRequestedStorage().getId())
                    .setData(StringUtils.toJSON(data));
            dataRepository.save(obj);

            return new ObjectResponse(obj);
        } catch( Exception ex ) {
            return new ObjectResponse().setErrorDescription("No se puede convertir a JSON");
        }


    }

    public ObjectResponse updateData(Map<String,Object> data) {
        String error = getStorageForUpdates();
        if( error != null )
            return new ObjectResponse().setErrorDescription(error);

        try {
            Data obj = dataRepository.getByParent(this.getRequestedStorage().getId());
            obj.setData(StringUtils.toJSON(data) );
            dataRepository.save(obj);

            return new ObjectResponse(obj);
        } catch( Exception ex ) {
            return new ObjectResponse().setErrorDescription("No se puede convertir a JSON");
        }


    }

    public ObjectResponse deleteData() {
        String error = getStorageForUpdates();
        if( error != null )
            return new ObjectResponse().setErrorDescription(error);

        Data obj = dataRepository.getByParent(this.getRequestedStorage().getId());

        dataRepository.delete(obj);

        return new ObjectResponse(obj);

    }

    private String commandHelp( String commandName, Options options ) {
        HelpFormatter formatter = new HelpFormatter();
        StringWriter out = new StringWriter();
        PrintWriter pw = new PrintWriter(out);

        formatter.printHelp( pw,80, commandName, commandName + " options", options,
                formatter.getLeftPadding(), formatter.getDescPadding(),
                "list", true);
        pw.flush();
        logger.error( "Parsing failed.  Reason: No send command " + out.toString() );
        return out.toString();

    }
    public ResponseEntity<?> runCommand(String command, List<String> arguments ) {

        String error = getStorageForReads();
        if( error != null )
            return ResponseEntity.badRequest().body(error);

        Options options = new Options();

        logger.info("Command:"+command + " for storage: " + this.getRequestedStorage().getPath());

        try {

            options.addOption(new Option("ls", "list", false, "List directory."))
                .addOption(new Option("r", "recursive", false, "Recursive list."))
                .addOption(new Option("d", "directories", false, "List only directories."))
                .addOption(new Option("f", "files", false, "List only files."))
                .addOption(new Option("d", "sort-date", false, "List sort by date."))
                .addOption(new Option("t", "sort-type", false, "List sort by type."));

            String[] args = arguments.toArray(new String[0]);
            CommandLineParser parser = new DefaultParser();
            CommandLine cmd = parser.parse(options, args);
            if( cmd.hasOption("list") ) {

            } else {
                String apiSyntax = commandHelp( command, options);
                logger.error( "Command failed.  Reason: No send command " + apiSyntax );
                return ResponseEntity.badRequest().body("Command failed.  No send command " + apiSyntax);
            }
            return ResponseEntity.ok().body("ok");
        }catch( ParseException exp ) {
            // oops, something went wrong
            String apiSyntax = commandHelp( "List", options);
            logger.error( "Parsing failed.  Reason: " + apiSyntax );
            return ResponseEntity.badRequest().body("Parsing failed.  Reason: " + apiSyntax);
        } catch( Exception ex ) {
            logger.error(ex);
            return ResponseEntity.badRequest().build();
        }
    }

}

package dev.c20.workflow.storage.services;

import dev.c20.workflow.WorkflowApplication;
import dev.c20.workflow.commons.auth.UserEntity;
import dev.c20.workflow.storage.entities.Storage;
import dev.c20.workflow.storage.entities.adds.Log;
import dev.c20.workflow.storage.entities.adds.Perm;
import dev.c20.workflow.storage.repositories.*;
import dev.c20.workflow.commons.tools.PathUtils;
import dev.c20.workflow.commons.tools.StringUtils;
import org.apache.commons.cli.*;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

@Service
public class CommandService {

    protected final org.apache.commons.logging.Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    SecurityService securityService;

    @Autowired
    StorageRepository storageRepository;

    @Autowired
    PermRepository permRepository;

    @Autowired
    AttachRepository attachRepository;

    @Autowired
    DataRepository dataRepository;

    @Autowired
    LogRepository logRepository;

    @Autowired
    NoteRepository noteRepository;

    @Autowired
    ValueRepository valueRepository;

    EntityManager em;
    @Autowired
    public CommandService setEntityManager(EntityManager em) {
        logger.info( "Set entityManager");
        this.em = em;
        return this;
    }

    private Boolean isFolder = false;
    private Boolean isFile = false;
    private String path;
    private Storage parentFolder;
    private Storage requestedStorage;
    private HttpServletRequest httpRequest;
    private UserEntity userEntity;
    public String getPath() {

        String path = PathUtils.getPathFromLevel(httpRequest.getRequestURI(),4);
        try {
            path =  URLDecoder.decode(path, "UTF-8");
            logger.info("Command for:" + path);
            return path;
        } catch (UnsupportedEncodingException e) {
            logger.error(e);
        }
        return null;
    }


    public CommandService setHttpServletRequest( HttpServletRequest httpRequest ) {
        this.httpRequest  = httpRequest;
        this.path = getPath();
        if( StringUtils.isEmpty(path) )
            return this;
        this.isFolder = PathUtils.isFolder(path);

        if( PathUtils.getPathLevel(this.getPath()) > 0 ) {
            String parent = PathUtils.getParentFolder(this.getPath());
            this.parentFolder = storageRepository.getFolder(parent);
        }

        if( this.isFolder ) {
            logger.info("Resquest is for folder");
            this.requestedStorage = storageRepository.getFolder(this.getPath());
        } else {
            logger.info("Resquest is for file");
            this.requestedStorage = storageRepository.getFile(this.getPath());
        }

        userEntity = UserEntity.fromToken(httpRequest.getHeader(WorkflowApplication.HEADER_AUTHORIZATION));

        return this;
    }

    public Log newLog(Storage parent, String user, String comment ) {
        return newLog( parent, user, comment,null, null );
    }

    public Log newLog( Storage parent, String user, String comment, Long commentId ) {
        return newLog( parent, user, comment,commentId, null );
    }

    public Log newLog( Storage parent, String user, String comment, Long commentId, Long type ) {
        Log log = new Log()
                .setParent(parent)
                .setModifier(user)
                .setModified(new Date())
                .setComment(comment)
                .setCommentId(commentId)
                .setType(type);
        logRepository.save(log);
        return log;
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

    public ResponseEntity<?> listCommand( String[] args, Options options) throws ParseException {

        options.addOption(new Option("r", "recursive", false, "Recursive list."))
                .addOption(new Option("d", "directories", false, "List only directories."))
                .addOption(new Option("f", "files", false, "List only files."))
                .addOption(new Option("d", "sort-date", false, "List sort by date."))
                .addOption(new Option("t", "sort-type", false, "List sort by type."));

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);
        if( !isFolder ) {
            return ResponseEntity.badRequest().body("Solo se puede lista por directorio");
        }

        if( requestedStorage.getRestrictedByPerm() ) {
            // checar permisos
        }


        CriteriaBuilder qb = em.getCriteriaBuilder();
        CriteriaQuery<Storage> cq = qb.createQuery(Storage.class);
        Root<Storage> storage = cq.from(Storage.class);
        Path<String> pathPath = storage.get("path");
        Path<Integer> levelPath = storage.get("level");

        List<Predicate> predicates = new ArrayList<>();

        Map<String,Object> result = new HashMap<>();

        result.put("args", args);
        result.put("recursive", cmd.hasOption("recursive"));

        predicates.add(qb.and(qb.greaterThan(levelPath, requestedStorage.getLevel())));

        if( cmd.hasOption("recursive") ) {
            predicates.add(qb.and(qb.like(pathPath, requestedStorage.getPath() + "%")));
        } else {
            predicates.add(qb.and(qb.equal(pathPath, requestedStorage.getPath())));
        }

        if( cmd.hasOption("directories") ) {
            Path<Boolean> thisPath = storage.get("isFolder");
            predicates.add(qb.and(qb.equal(thisPath, true)));
        }

        if( cmd.hasOption("files") ) {
            Path<Boolean> thisPath = storage.get("isFolder");
            predicates.add(qb.and(qb.equal(thisPath, false)));
        }


        cq.where(
                qb.and(
                        predicates.toArray(new Predicate[0])
                )
        );

        cq.orderBy(
                qb.asc(storage.get("path")),
                qb.asc(storage.get("isFolder"))
        );

        List<Storage> list = em.createQuery(cq).getResultList();

        Integer level = requestedStorage.getLevel() + 1;
        for( Storage stg : list ) {
            if( stg.getLevel() == level ) {
                requestedStorage.getChildren().add(stg);
            } else {
                tree(list, stg);
            }
        }

        result.put("root",requestedStorage);

        return ResponseEntity.ok().body(result);
    }

    public void tree( List<Storage> list, Storage storage ) {
        String parentPath = PathUtils.getParentFolder( storage.getPath() );

        for( Storage parent : list ) {
            if( parent.getPath().equals(parentPath)) {
                parent.getChildren().add(storage);
                return;
            }
        }

    }

    public ResponseEntity<?> copyCommand( String[] args, Options options) throws ParseException {

        options.addOption(new Option("d", "destination", true, "Destination directory."));

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);

        logger.info("    Copy file:" + requestedStorage.getPath());
        logger.info("           To:" + cmd.getOptionValue("d"));
        logger.info("Target folder:" + PathUtils.getParentFolder( cmd.getOptionValue("d")) );
        if( isFolder ) {
            return ResponseEntity.badRequest().body("Funcionalidad no implementada");
        }

        if( parentFolder.getRestrictedByPerm() ) {
            Perm perm = permRepository.getByUser(parentFolder,userEntity.getUser());
            if( !( perm.getCanAdmin() || perm.getCanRead() ) ) {
                throw new RuntimeException(userEntity.getUser() + " No se tiene permisos de lectura o administración en el origen");
            }
        }

        Storage targetFolder = storageRepository.getFolder(PathUtils.getParentFolder( cmd.getOptionValue("d")));

        if( targetFolder == null ) {
            throw new RuntimeException("No existe el folder destino");
        }

        if( targetFolder.getRestrictedByPerm() ) {
            Perm perm = permRepository.getByUser(targetFolder,userEntity.getUser());
            if( !( perm.getCanAdmin() || perm.getCanCreate() ) ) {
                throw new RuntimeException(userEntity.getUser() + " No se tiene permisos de lectura o administración en el destino");
            }
        }

        Storage target = new Storage().setPropertiesFrom(requestedStorage);
        target.setCreated( new Date() )
                .setCreator(userEntity.getUser());
        target.setPath(cmd.getOptionValue("d"));

        storageRepository.save(target);
        // copiar propiedades adicionales

        attachRepository.copyTo(target, requestedStorage );
        dataRepository.copyTo(target.getId(), requestedStorage.getId() );
        logRepository.copyTo(target, requestedStorage );
        noteRepository.copyTo(target, requestedStorage );
        permRepository.copyTo(target, requestedStorage );
        valueRepository.copyTo(target, requestedStorage );

        newLog(target, userEntity.getUser(), "Se copio desde " + requestedStorage.getPath());

        return ResponseEntity.ok().body(target);
    }

    public ResponseEntity<?> renameCommand( String[] args, Options options) throws ParseException {

        options.addOption(new Option("t", "target", true, "Rename."))
                .addOption(new Option("d", "directory", false, "Rename Directory."));

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);

        logger.info("  Rename file:" + requestedStorage.getPath());
        logger.info("           To:" + cmd.getOptionValue("t"));

        if( isFolder ) {
            return ResponseEntity.badRequest().body("Funcionalidad no implementada");
        }

        if( parentFolder.getRestrictedByPerm() ) {
            Perm perm = permRepository.getByUser(parentFolder,userEntity.getUser());
            if( !( perm.getCanAdmin() || perm.getCanUpdate() ) ) {
                throw new RuntimeException(userEntity.getUser() + " No se tiene permisos de actualización o administración en el origen");
            }
        }
        requestedStorage.setPath(cmd.getOptionValue("t"));
        requestedStorage.setModified(true);
        requestedStorage.setModifier(userEntity.getUser());
        requestedStorage.setModifyDate(new Date());

        storageRepository.save(requestedStorage);

        newLog(requestedStorage, userEntity.getUser(), "Cambio de nombre");
        return ResponseEntity.ok().body(requestedStorage);
    }


    public ResponseEntity<?> runCommand(String command, String[] arguments) {
        Options options = new Options();

        try {
            if (command.equals("list")) {
                return listCommand(arguments,options);
            } else if (command.equals("copy")) {
                return copyCommand(arguments,options);
            } else if (command.equals("rename")) {
                return renameCommand(arguments,options);
            }
        } catch( ParseException exp ) {
            // oops, something went wrong
            String apiSyntax = commandHelp( command, options);
            logger.error( "Parsing failed.  Reason: " + apiSyntax );
            return ResponseEntity.badRequest().body("Parsing failed.  Reason: " + apiSyntax);
        } catch( Exception ex ) {
            logger.error(ex);
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.badRequest().build();

    }

}

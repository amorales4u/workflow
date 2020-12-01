package dev.c20.workflow.storage.services;

import dev.c20.workflow.storage.entities.Storage;
import dev.c20.workflow.storage.entities.adds.Perm;
import dev.c20.workflow.storage.repositories.StorageRepository;
import dev.c20.workflow.tools.PathUtils;
import dev.c20.workflow.tools.StringUtils;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommandService {

    protected final org.apache.commons.logging.Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    SecurityService securityService;

    @Autowired
    StorageRepository storageRepository;

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

        return this;
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

        List<Predicate> predicates = new ArrayList<>();

        Map<String,Object> result = new HashMap<>();

        result.put("args", args);
        result.put("recursive", cmd.hasOption("recursive"));

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

        result.put("list",list);

        return ResponseEntity.ok().body(result);
    }

    public ResponseEntity<?> copyCommand( String[] args, Options options) throws ParseException {

        options.addOption(new Option("ls", "copy", false, "List directory."))
                .addOption(new Option("r", "recursive", false, "Recursive list."))
                .addOption(new Option("d", "directories", false, "List only directories."))
                .addOption(new Option("f", "files", false, "List only files."))
                .addOption(new Option("d", "sort-date", false, "List sort by date."))
                .addOption(new Option("t", "sort-type", false, "List sort by type."));

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);

        return ResponseEntity.ok().body("ok");
    }

    public ResponseEntity<?> moveCommand( String[] args, Options options) throws ParseException {

        options.addOption(new Option("ls", "move", false, "List directory."))
                .addOption(new Option("r", "recursive", false, "Recursive list."))
                .addOption(new Option("d", "directories", false, "List only directories."))
                .addOption(new Option("f", "files", false, "List only files."))
                .addOption(new Option("d", "sort-date", false, "List sort by date."))
                .addOption(new Option("t", "sort-type", false, "List sort by type."));

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);
        return ResponseEntity.ok().body("ok");
    }

    public ResponseEntity<?> renameCommand( String[] args, Options options) throws ParseException {

        options.addOption(new Option("ls", "rename", false, "List directory."))
                .addOption(new Option("r", "recursive", false, "Recursive list."))
                .addOption(new Option("d", "directories", false, "List only directories."))
                .addOption(new Option("f", "files", false, "List only files."))
                .addOption(new Option("d", "sort-date", false, "List sort by date."))
                .addOption(new Option("t", "sort-type", false, "List sort by type."));

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);
        return ResponseEntity.ok().body("ok");
    }


    public ResponseEntity<?> runCommand(String command, String[] arguments) {
        Options options = new Options();

        try {
            if (command.equals("list")) {
                return listCommand(arguments,options);
            } else if (command.equals("copy")) {
                return copyCommand(arguments,options);
            } else if (command.equals("move")) {
                return moveCommand(arguments,options);
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

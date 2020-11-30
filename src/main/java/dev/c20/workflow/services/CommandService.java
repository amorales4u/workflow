package dev.c20.workflow.services;

import org.apache.commons.cli.*;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

@Service
public class CommandService {

    protected final org.apache.commons.logging.Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    SecurityService securityService;

    EntityManager em;
    @Autowired
    public CommandService setEntityManager(EntityManager em) {
        logger.info( "Set entityManager");
        this.em = em;
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
    public ResponseEntity<?> runCommand(String command, List<String> arguments, HttpServletRequest request) {

        /*
        String error = securityService
                .setHttpServletRequest(request)
                .;
        if( error != null )
            return ResponseEntity.badRequest().body(error);
        */
        Options options = new Options();

        //logger.info("Command:"+command + " for storage: " + this.getRequestedStorage().getPath());

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

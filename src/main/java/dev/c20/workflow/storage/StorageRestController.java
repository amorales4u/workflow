package dev.c20.workflow.storage;

import dev.c20.workflow.annotations.Roles;
import dev.c20.workflow.storage.entities.Storage;
import dev.c20.workflow.storage.entities.adds.Attach;
import dev.c20.workflow.storage.entities.adds.Note;
import dev.c20.workflow.storage.entities.adds.Perm;
import dev.c20.workflow.storage.entities.adds.Value;
import dev.c20.workflow.storage.services.StorageService;
import dev.c20.workflow.tools.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(
        path = "/storage",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)

public class StorageRestController {

    // POST Create
    // PUT Update
    // GET Read
    // DELETE delete

    //Logger logger = Logger.getLogger(StorageRest.class.getName());
    protected final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    StorageService storageService;

    @Roles({"ADMIN","TEST"})
    @GetMapping("/version")
    ResponseEntity<?> version(HttpServletRequest request ) {
        Map<String,Object> map = new HashMap<>();
        map.put("version", "2020/11/25");
        map.put("entity", new Storage());
        return ResponseEntity.ok(map);
    }

    @PutMapping("/test")
    ResponseEntity<?> test(@RequestBody Storage storage) throws Exception {
        String key = "EjEm92-0.12rtyuo";
        String source = "Hola Mundo!";
        String target = StringUtils.encrypt(source,key);

        System.out.println( source );
        System.out.println( target );
        System.out.println( StringUtils.decrypt(target,key) );
        return ResponseEntity.ok(StringUtils.decrypt(target,key));
    }


    @PutMapping("/command/{command}/**")
    ResponseEntity<?> runCommand( @PathVariable String command, @RequestBody String params, HttpServletRequest request) throws Exception {

        return storageService
                .setHttpServletRequest(request)
                .runCommand(command, StringUtils.splitAsList(params," ", true));
    }

    @GetMapping("/folder/**")
    ResponseEntity<?> getFolderStorage( HttpServletRequest request) throws Exception {

        return storageService
                .setHttpServletRequest(request)
                .getStorage(true)
                .response();

    }

    @PostMapping("/folder/**")
    ResponseEntity<?> createFolder( Storage storage, HttpServletRequest request) throws Exception {

        return storageService
                .setHttpServletRequest(request)
                .addFolder(storage)
                .response();

    }

    @DeleteMapping("/folder/**")
    ResponseEntity<?> deleteFolder(HttpServletRequest request) throws Exception {
        return storageService
                .setHttpServletRequest(request)
                .delStorage()
                .response();
    }

    @PutMapping("/folder/**")
    ResponseEntity<?> updateStorage(@RequestBody Storage storage, HttpServletRequest request) throws Exception {
        return storageService
                .setHttpServletRequest(request)
                .updateStorage(storage)
                .response();
    }

    @GetMapping("/file/**")
    ResponseEntity<?> getFileStorage( HttpServletRequest request) throws Exception {

        return storageService
                .setHttpServletRequest(request)
                .getStorage(false)
                .response();

    }

    @PostMapping("/file/**")
    ResponseEntity<?> createFile(Storage storage, HttpServletRequest request) throws Exception {
        return storageService
                .setHttpServletRequest(request)
                .addFile(storage)
                .response();
    }

    @PutMapping("/file/**")
    ResponseEntity<?> updateFile(Storage storage,HttpServletRequest request) throws Exception {
        return storageService
                .setHttpServletRequest(request)
                .updateStorage(storage)
                .response();
    }

    @DeleteMapping("/file/**")
    ResponseEntity<?> deleteFile(HttpServletRequest request) throws Exception {
        return storageService
                .setHttpServletRequest(request)
                .delStorage()
                .response();
    }

    @PostMapping("/note/**")
    ResponseEntity<?> createNote(@RequestBody Note note, HttpServletRequest request) throws Exception {
        return storageService
                .setHttpServletRequest(request)
                .addNote(note)
                .response();

    }

    @GetMapping("/note/**")
    ResponseEntity<?> readNote(HttpServletRequest request) throws Exception {
        return storageService
                .setHttpServletRequest(request)
                .getNotes()
                .response();

    }

    @PostMapping("/log/**")
    ResponseEntity<?> createLog(@RequestBody dev.c20.workflow.storage.entities.adds.Log log, HttpServletRequest request) throws Exception {
        return storageService
                .setHttpServletRequest(request)
                .addLog(log)
                .response();

    }

    @GetMapping("/log/**")
    ResponseEntity<?> readLog(HttpServletRequest request) throws Exception {
        return storageService
                .setHttpServletRequest(request)
                .getLog()
                .response();

    }

    @GetMapping("/value/**")
    ResponseEntity<?> readValues(HttpServletRequest request) throws Exception {
        return storageService
                .setHttpServletRequest(request)
                .getValues()
                .response();

    }

    @PostMapping("/value/**")
    ResponseEntity<?> createValue(@RequestBody Value value, HttpServletRequest request) throws Exception {
        return storageService
                .setHttpServletRequest(request)
                .addValue(value)
                .response();

    }

    @PutMapping("/value/**")
    ResponseEntity<?> updateValue(@RequestBody Value value, HttpServletRequest request) throws Exception {
        return storageService
                .setHttpServletRequest(request)
                .updateValue(value)
                .response();

    }

    @DeleteMapping("/value/**")
    ResponseEntity<?> deleteValue(@RequestBody Value value, HttpServletRequest request) throws Exception {
        return storageService
                .setHttpServletRequest(request)
                .deleteValue(value)
                .response();

    }


    @GetMapping("/download/**")
    ResponseEntity<?> downloadAttach(@RequestBody Attach attach, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return storageService
                .setHttpServletRequest(request)
                .setHttpServletResponse(response)
                .downloadAttach(attach);

    }
    @GetMapping("/attach/**")
    ResponseEntity<?> readAttachments(HttpServletRequest request) throws Exception {
        return storageService
                .setHttpServletRequest(request)
                .getAttachments()
                .response();

    }

    @PostMapping("/attach/**")
    ResponseEntity<?> createAttach(@RequestParam("files") MultipartFile[] files, HttpServletRequest request) throws Exception {
        return storageService
                .setHttpServletRequest(request)
                .addAttach(files)
                .response();

    }

    @PutMapping("/attach/**")
    ResponseEntity<?> updateAttach(@RequestBody Attach attach, HttpServletRequest request) throws Exception {
        return storageService
                .setHttpServletRequest(request)
                .updateAttach(attach)
                .response();

    }

    @DeleteMapping("/attach/**")
    ResponseEntity<?> deleteAttach(@RequestBody Attach attach, HttpServletRequest request) throws Exception {
        return storageService
                .setHttpServletRequest(request)
                .deleteAttach(attach)
                .response();

    }

    @GetMapping("/data/**")
    ResponseEntity<?> getData( HttpServletRequest request) throws Exception {
        return storageService
                .setHttpServletRequest(request)
                .getData()
                .response();
    }

    @PostMapping("/data/**")
    ResponseEntity<?> createData(@RequestBody Map<String,Object> data, HttpServletRequest request) throws Exception {
        return storageService
                .setHttpServletRequest(request)
                .addData(data)
                .response();
    }

    @PutMapping("/data/**")
    ResponseEntity<?> updateData(@RequestBody Map<String,Object> data, HttpServletRequest request) throws Exception {
        return storageService
                .setHttpServletRequest(request)
                .updateData(data)
                .response();
    }

    @DeleteMapping("/data/**")
    ResponseEntity<?> deleteData(@RequestBody Map<String,Object> data, HttpServletRequest request) throws Exception {
        return storageService
                .setHttpServletRequest(request)
                .deleteData()
                .response();
    }

    @GetMapping("/perm/**")
    ResponseEntity<?> getPerm( HttpServletRequest request) throws Exception {
        return storageService
                .setHttpServletRequest(request)
                .getPerms()
                .response();
    }

    @PostMapping("/perm/**")
    ResponseEntity<?> createPerm(@RequestBody Perm perm, HttpServletRequest request) throws Exception {
        return storageService
                .setHttpServletRequest(request)
                .addPerm(perm)
                .response();
    }

    @PutMapping("/perm/**")
    ResponseEntity<?> updatePerm(@RequestBody Perm data, HttpServletRequest request) throws Exception {
        return storageService
                .setHttpServletRequest(request)
                .updatePerm(data)
                .response();
    }

    @DeleteMapping("/perm/**")
    ResponseEntity<?> deletePerm(@RequestBody Perm data, HttpServletRequest request) throws Exception {
        return storageService
                .setHttpServletRequest(request)
                .deletePerm(data)
                .response();
    }


}

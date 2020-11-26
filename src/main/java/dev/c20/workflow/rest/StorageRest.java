package dev.c20.workflow.rest;

import dev.c20.workflow.entities.Storage;
import dev.c20.workflow.entities.adds.Attach;
import dev.c20.workflow.entities.adds.Note;
import dev.c20.workflow.entities.adds.Value;
import dev.c20.workflow.services.StorageService;
import dev.c20.workflow.tools.PathUtils;
import dev.c20.workflow.tools.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/storage")

public class StorageRest {

    //Logger logger = Logger.getLogger(StorageRest.class.getName());
    protected final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    StorageService storageService;

    @GetMapping(value = "/version")
    ResponseEntity<?> version() {
        Map<String,Object> map = new HashMap<>();
        map.put("version", "2020/11/25");
        map.put("entity", new Storage());
        return ResponseEntity.ok(map);
    }

    @PostMapping("/test")
    ResponseEntity<?> test(@RequestBody Storage storage) {
        Map<String,Object> map = new HashMap<>();
        map.put("version", "2020/11/25");
        map.put("entity", storage);
        return ResponseEntity.ok(map);
    }


    @GetMapping("/folder/**")
    ResponseEntity<?> getFolderStorage( HttpServletRequest request) throws Exception {

        return storageService
                .setHttpServletRequest(request)
                .getStorage(true)
                .response();

    }

    @PutMapping("/folder/**")
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

    @PostMapping("/folder/**")
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

    @PutMapping("/file/**")
    ResponseEntity<?> createFile(Storage storage, HttpServletRequest request) throws Exception {
        return storageService
                .setHttpServletRequest(request)
                .addFile(storage)
                .response();
    }

    @PostMapping("/file/**")
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

    @PutMapping("/note/**")
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

    @PutMapping("/log/**")
    ResponseEntity<?> createLog(@RequestBody dev.c20.workflow.entities.adds.Log log, HttpServletRequest request) throws Exception {
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

    @PutMapping("/value/**")
    ResponseEntity<?> createValue(@RequestBody Value value, HttpServletRequest request) throws Exception {
        return storageService
                .setHttpServletRequest(request)
                .addValue(value)
                .response();

    }

    @PostMapping("/value/**")
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


    @GetMapping("/attach/**")
    ResponseEntity<?> readAttachments(HttpServletRequest request) throws Exception {
        return storageService
                .setHttpServletRequest(request)
                .getAttachments()
                .response();

    }

    @PutMapping("/attach/**")
    ResponseEntity<?> createAttach(@RequestBody Attach attach, HttpServletRequest request) throws Exception {
        return storageService
                .setHttpServletRequest(request)
                .addAttach(attach)
                .response();

    }

    @PostMapping("/attach/**")
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

    @PutMapping("/data/**")
    ResponseEntity<?> createData(@RequestBody Map<String,Object> data, HttpServletRequest request) throws Exception {
        return storageService
                .setHttpServletRequest(request)
                .addData(data)
                .response();
    }

    @PostMapping("/data/**")
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

}

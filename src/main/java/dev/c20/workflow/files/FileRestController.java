package dev.c20.workflow.files;

import dev.c20.workflow.files.services.FileDBStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(
        path = "/files",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class FileRestController {


    @Autowired
    FileDBStorageService fileDBStorageService;

    @GetMapping("/{file}/{fileName}")
    ResponseEntity<?> downloadFile(@PathVariable("file") Long file, @PathVariable("fileName") String fileName, HttpServletRequest request) throws Exception {
        return fileDBStorageService
                .downloadFile(fileName,file);
    }

    @PostMapping("/")
    ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws Exception {
        return fileDBStorageService
                .uploadFile(file);
    }

    @PutMapping("/{file}")
    ResponseEntity<?> updateFile(@PathVariable("file") Long fileId, MultipartFile file, HttpServletRequest request) throws Exception {
        return fileDBStorageService
                .updateFile(file,fileId);
    }

    @DeleteMapping("/{file}")
    ResponseEntity<?> updateFile(@PathVariable("file") Long file, HttpServletRequest request) throws Exception {
        return fileDBStorageService
                .deleteFile(file);
    }



}

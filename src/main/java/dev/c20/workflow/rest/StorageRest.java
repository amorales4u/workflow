package dev.c20.workflow.rest;

import dev.c20.workflow.entities.Storage;
import dev.c20.workflow.repositories.StorageRepository;
import dev.c20.workflow.services.StorageService;
import dev.c20.workflow.tools.PathUtils;
import dev.c20.workflow.tools.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PutMapping("/folder/**")
    ResponseEntity<?> createFolder( HttpServletRequest request) throws Exception {
        System.out.println( "Has ADMIN" );
        String path = StringUtils.getPathFromURI( "/storage/folder", request);


        Map<String,Object> map = new HashMap<>();

        if( !PathUtils.isFolder(path) ) {
            map.put("error", "No es un folder");
            return ResponseEntity.badRequest().body(map);
        }

        return ResponseEntity.ok(new Storage());
    }

    @DeleteMapping("/folder/**")
    ResponseEntity<?> deleteFolder(HttpServletRequest request) throws Exception {
        logger.info(request.getContextPath());
        String path = StringUtils.getPathFromURI( "/storage/folder", request);


        Map<String,Object> map = new HashMap<>();

        if( !PathUtils.isFolder(path) ) {
            map.put("error", "No es un folder");
            return ResponseEntity.badRequest().body(map);
        }

        map.put("path", path);
        map.put("parentFolder",PathUtils.getParentFolder(path));
        map.put("folderName",PathUtils.getName(path));
        map.put("level", PathUtils.getPathLevel(path));
        map.put("isFolder", PathUtils.isFolder(path));

        return ResponseEntity.ok(map);
    }

    @PutMapping("/file/**")
    ResponseEntity<?> createFile(HttpServletRequest request) throws Exception {
        logger.info(request.getContextPath());
        String path = StringUtils.getPathFromURI( "/storage/file", request);


        Map<String,Object> map = new HashMap<>();

        if( !PathUtils.isFile(path) ) {
            map.put("error", "No es un file");
            return ResponseEntity.badRequest().body(map);
        }

        map.put("path", path);
        map.put("parentFolder",PathUtils.getParentFolder(path));
        map.put("folderName",PathUtils.getName(path));
        map.put("level", PathUtils.getPathLevel(path));
        map.put("isFolder", PathUtils.isFolder(path));

        return ResponseEntity.ok(map);
    }

    @PostMapping("/file/**")
    ResponseEntity<?> updateFile(HttpServletRequest request) throws Exception {
        logger.info(request.getContextPath());
        String path = StringUtils.getPathFromURI( "/storage/file", request);


        Map<String,Object> map = new HashMap<>();

        if( !PathUtils.isFile(path) ) {
            map.put("error", "No es un file");
            return ResponseEntity.badRequest().body(map);
        }

        map.put("path", path);
        map.put("parentFolder",PathUtils.getParentFolder(path));
        map.put("folderName",PathUtils.getName(path));
        map.put("level", PathUtils.getPathLevel(path));
        map.put("isFolder", PathUtils.isFolder(path));

        return ResponseEntity.ok(map);
    }

    @DeleteMapping("/file/**")
    ResponseEntity<?> deleteFile(HttpServletRequest request) throws Exception {
        logger.info(request.getContextPath());
        String path = StringUtils.getPathFromURI( "/storage/file", request);


        Map<String,Object> map = new HashMap<>();

        if( !PathUtils.isFile(path) ) {
            map.put("error", "No es un file");
            return ResponseEntity.badRequest().body(map);
        }

        map.put("path", path);
        map.put("parentFolder",PathUtils.getParentFolder(path));
        map.put("folderName",PathUtils.getName(path));
        map.put("level", PathUtils.getPathLevel(path));
        map.put("isFolder", PathUtils.isFolder(path));

        return ResponseEntity.ok(map);
    }

    @PutMapping("/note/**")
    ResponseEntity<?> createNote(HttpServletRequest request) throws Exception {
        logger.info(request.getContextPath());
        String path = StringUtils.getPathFromURI( "/storage/note", request);
        logger.info("Path:" + path );

        storageService.addFolder(null);


        Map<String,Object> map = new HashMap<>();

        map.put("path", path);
        map.put("parentFolder",PathUtils.getParentFolder(path));
        map.put("folderName",PathUtils.getName(path));
        map.put("level", PathUtils.getPathLevel(path));
        map.put("isFolder", PathUtils.isFolder(path));

        return ResponseEntity.ok(map);
    }

    @PutMapping("/log/**")
    ResponseEntity<?> createLog(HttpServletRequest request) throws Exception {
        logger.info(request.getContextPath());
        String path = StringUtils.getPathFromURI( "/storage/note", request);


        Map<String,Object> map = new HashMap<>();

        map.put("path", path);
        map.put("parentFolder",PathUtils.getParentFolder(path));
        map.put("folderName",PathUtils.getName(path));
        map.put("level", PathUtils.getPathLevel(path));
        map.put("isFolder", PathUtils.isFolder(path));

        return ResponseEntity.ok(map);
    }

    @PutMapping("/value/**")
    ResponseEntity<?> createValue(HttpServletRequest request) throws Exception {
        logger.info(request.getContextPath());
        String path = StringUtils.getPathFromURI( "/storage/note", request);


        Map<String,Object> map = new HashMap<>();

        map.put("path", path);
        map.put("parentFolder",PathUtils.getParentFolder(path));
        map.put("folderName",PathUtils.getName(path));
        map.put("level", PathUtils.getPathLevel(path));
        map.put("isFolder", PathUtils.isFolder(path));

        return ResponseEntity.ok(map);
    }

    @PostMapping("/value/**")
    ResponseEntity<?> updateValue(HttpServletRequest request) throws Exception {
        logger.info(request.getContextPath());
        String path = StringUtils.getPathFromURI( "/storage/note", request);


        Map<String,Object> map = new HashMap<>();

        map.put("path", path);
        map.put("parentFolder",PathUtils.getParentFolder(path));
        map.put("folderName",PathUtils.getName(path));
        map.put("level", PathUtils.getPathLevel(path));
        map.put("isFolder", PathUtils.isFolder(path));

        return ResponseEntity.ok(map);
    }

    @DeleteMapping("/value/**")
    ResponseEntity<?> deleteValue(HttpServletRequest request) throws Exception {
        logger.info(request.getContextPath());
        String path = StringUtils.getPathFromURI( "/storage/note", request);


        Map<String,Object> map = new HashMap<>();

        map.put("path", path);
        map.put("parentFolder",PathUtils.getParentFolder(path));
        map.put("folderName",PathUtils.getName(path));
        map.put("level", PathUtils.getPathLevel(path));
        map.put("isFolder", PathUtils.isFolder(path));

        return ResponseEntity.ok(map);
    }


    @PutMapping("/attach/**")
    ResponseEntity<?> createAttach(HttpServletRequest request) throws Exception {
        logger.info(request.getContextPath());
        String path = StringUtils.getPathFromURI( "/storage/note", request);


        Map<String,Object> map = new HashMap<>();

        map.put("path", path);
        map.put("parentFolder",PathUtils.getParentFolder(path));
        map.put("folderName",PathUtils.getName(path));
        map.put("level", PathUtils.getPathLevel(path));
        map.put("isFolder", PathUtils.isFolder(path));

        return ResponseEntity.ok(map);
    }

    @PostMapping("/attach/**")
    ResponseEntity<?> updateAttach(HttpServletRequest request) throws Exception {
        logger.info(request.getContextPath());
        String path = StringUtils.getPathFromURI( "/storage/note", request);


        Map<String,Object> map = new HashMap<>();

        map.put("path", path);
        map.put("parentFolder",PathUtils.getParentFolder(path));
        map.put("folderName",PathUtils.getName(path));
        map.put("level", PathUtils.getPathLevel(path));
        map.put("isFolder", PathUtils.isFolder(path));

        return ResponseEntity.ok(map);
    }

    @DeleteMapping("/attach/**")
    ResponseEntity<?> deleteAttach(HttpServletRequest request) throws Exception {
        logger.info(request.getContextPath());
        String path = StringUtils.getPathFromURI( "/storage/note", request);


        Map<String,Object> map = new HashMap<>();

        map.put("path", path);
        map.put("parentFolder",PathUtils.getParentFolder(path));
        map.put("folderName",PathUtils.getName(path));
        map.put("level", PathUtils.getPathLevel(path));
        map.put("isFolder", PathUtils.isFolder(path));

        return ResponseEntity.ok(map);
    }


    @PutMapping("/data/**")
    ResponseEntity<?> createData(HttpServletRequest request) throws Exception {
        logger.info(request.getContextPath());
        String path = StringUtils.getPathFromURI( "/storage/note", request);


        Map<String,Object> map = new HashMap<>();

        map.put("path", path);
        map.put("parentFolder",PathUtils.getParentFolder(path));
        map.put("folderName",PathUtils.getName(path));
        map.put("level", PathUtils.getPathLevel(path));
        map.put("isFolder", PathUtils.isFolder(path));

        return ResponseEntity.ok(map);
    }

    @PostMapping("/data/**")
    ResponseEntity<?> updateData(HttpServletRequest request) throws Exception {
        logger.info(request.getContextPath());
        String path = StringUtils.getPathFromURI( "/storage/note", request);


        Map<String,Object> map = new HashMap<>();

        map.put("path", path);
        map.put("parentFolder",PathUtils.getParentFolder(path));
        map.put("folderName",PathUtils.getName(path));
        map.put("level", PathUtils.getPathLevel(path));
        map.put("isFolder", PathUtils.isFolder(path));

        return ResponseEntity.ok(map);
    }

    @DeleteMapping("/data/**")
    ResponseEntity<?> deleteData(HttpServletRequest request) throws Exception {
        logger.info(request.getContextPath());
        String path = StringUtils.getPathFromURI( "/storage/note", request);


        Map<String,Object> map = new HashMap<>();

        map.put("path", path);
        map.put("parentFolder",PathUtils.getParentFolder(path));
        map.put("folderName",PathUtils.getName(path));
        map.put("level", PathUtils.getPathLevel(path));
        map.put("isFolder", PathUtils.isFolder(path));

        return ResponseEntity.ok(map);
    }



}

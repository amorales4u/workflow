package dev.c20.workflow.rest;

import dev.c20.workflow.tools.PathUtils;
import dev.c20.workflow.tools.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/storage")
public class StorageRest {

    Logger logger = Logger.getLogger(StorageRest.class.getName());

    @PutMapping("/folder/**")
    ResponseEntity<?> createFolder(Principal principal, HttpServletRequest request) throws Exception {
        Authentication authentication = (Authentication) principal;
        User user = (User) authentication.getPrincipal();
        user.getUsername();
        String path = StringUtils.getPathFromURI( "/storage/folder", request);


        Map<String,Object> map = new HashMap<>();

        if( !PathUtils.isFolder(path) ) {
            map.put("error", "No es un folder");
            return ResponseEntity.badRequest().body(map);
        }

        map.put("user", user.getUsername() );
        map.put("path", path);
        map.put("parentFolder",PathUtils.getParentFolder(path));
        map.put("folderName",PathUtils.getName(path));
        map.put("level", PathUtils.getPathLevel(path));
        map.put("isFolder", PathUtils.isFolder(path));

        return ResponseEntity.ok(map);
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


        Map<String,Object> map = new HashMap<>();

        map.put("path", path);
        map.put("parentFolder",PathUtils.getParentFolder(path));
        map.put("folderName",PathUtils.getName(path));
        map.put("level", PathUtils.getPathLevel(path));
        map.put("isFolder", PathUtils.isFolder(path));

        return ResponseEntity.ok(map);
    }



}

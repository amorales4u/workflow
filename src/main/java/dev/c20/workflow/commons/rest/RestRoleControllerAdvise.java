package dev.c20.workflow.commons.rest;

import dev.c20.workflow.commons.annotations.RoleException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class RestRoleControllerAdvise extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ RoleException.class })
    public ResponseEntity<Object> handleAccessDeniedException(
            RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "Su Rol no le permite hacer esta operación";
        Map<String,Object> response = new HashMap<>();
        response.put("timestamp", new Date());
        response.put("status", 401);
        response.put("error", bodyOfResponse);
        response.put("message",HttpStatus.UNAUTHORIZED);
        response.put("path", request.getContextPath());
        return new ResponseEntity<>(
                response, new HttpHeaders(), HttpStatus.UNAUTHORIZED);
    }
}

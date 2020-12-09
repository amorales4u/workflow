package dev.c20.workflow.commons.rest;

import dev.c20.workflow.commons.annotations.RoleException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestRoleControllerAdvise extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ RoleException.class })
    public ResponseEntity<Object> handleAccessDeniedException(
            RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "You not have Role for this operation";
        return new ResponseEntity<>(
                bodyOfResponse, new HttpHeaders(), HttpStatus.UNAUTHORIZED);
    }
}

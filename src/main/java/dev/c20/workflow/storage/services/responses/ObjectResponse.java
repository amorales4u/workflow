package dev.c20.workflow.storage.services.responses;

import dev.c20.workflow.storage.entities.Storage;
import org.springframework.http.ResponseEntity;

public class ObjectResponse<T> {

    private boolean error = false;
    private String errorDescription = "";
    private T data = null;

    public ObjectResponse( ) {

    }

    public ObjectResponse(String obj) {
        this.error = true;
        this.errorDescription = obj;
    }

    public ObjectResponse(T obj) {
        this.data = obj;
    }

    public ResponseEntity<ObjectResponse> response() {

        return this.error ? error() : ok();
    }

    public ResponseEntity<ObjectResponse> ok() {
        return ResponseEntity.ok(this);
    }

    public ResponseEntity<ObjectResponse> error() {
        return ResponseEntity.badRequest().body(this);
    }

    public boolean isError() {
        return error;
    }

    public ObjectResponse setError(boolean error) {
        this.error = error;
        return this;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public ObjectResponse setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
        this.error = errorDescription != null;
        return this;
    }

    public T getData() {
        return data;
    }

    public ObjectResponse<T> setData(T data) {
        this.data = data;
        return this;
    }

}

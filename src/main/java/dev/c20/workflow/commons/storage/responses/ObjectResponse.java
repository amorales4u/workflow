package dev.c20.workflow.commons.storage.responses;

import org.springframework.http.ResponseEntity;

public class ObjectResponse<T> {

    private boolean error = false;
    private String errorDescription = "";
    private T data = null;

    public ObjectResponse( ) {

    }

    public ObjectResponse(String obj) {
        this.error = true;
        this.errorDescription = "No existe";
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

    public Object getData() {
        return data;
    }

    public ObjectResponse setData(T data) {
        this.data = data;
        return this;
    }

}

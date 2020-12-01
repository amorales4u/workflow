package dev.c20.workflow.storage.services.responses;

import org.springframework.http.ResponseEntity;

public class ObjectResponse {

    private boolean error = false;
    private String errorDescription = "";
    private Object data = null;

    public ObjectResponse( ) {

    }

    public ObjectResponse(Object obj) {
        if( obj == null ) {
            this.error = true;
            this.errorDescription = "No existe";
            return;
        }

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

    public Object getData() {
        return data;
    }

    public Object setData(Object data) {
        this.data = data;
        return this;
    }

}

package dev.c20.workflow.commons.wrapper.responses;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    public ObjectResponse setResponse(String data) {
        if( data != null  ) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                ObjectResponse<T> res =
                        mapper.readValue(data, new TypeReference<ObjectResponse<T>>() {
                        });

                this.error = res.error;
                this.errorDescription = res.errorDescription;
                this.data = res.data;
                return this;
            } catch( Exception ex ) {
                this.error = true;
                this.errorDescription = "Error al parsear el body:" + ex.getMessage();
            }
        }

        return this;

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

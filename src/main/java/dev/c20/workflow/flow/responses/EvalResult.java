package dev.c20.workflow.flow.responses;

public class EvalResult {

    public boolean error = true;
    public String errorMessage = null;
    public Object response = null;
    public String message = null;


    public String getMessage() {
        return message;
    }

    public EvalResult setMessage(String message) {
        this.message = message;
        return this;
    }

    public EvalResult() {

    }

    public EvalResult( String errorMessage) {
        this.error = true;
        this.errorMessage = errorMessage;
    }

    public boolean isError() {
        return error;
    }

    public EvalResult setError(boolean error) {
        this.error = error;
        return this;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public EvalResult setErrorMessage(String errorMessage) {
        this.error = errorMessage != null;
        this.errorMessage = errorMessage;
        return this;
    }

    public Object getResponse() {
        return response;
    }

    public EvalResult setResponse(Object response) {
        this.error = false;
        this.errorMessage = null;
        this.response = response;
        return this;
    }


}

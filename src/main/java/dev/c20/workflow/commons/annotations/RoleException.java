package dev.c20.workflow.commons.annotations;

public class RoleException extends Exception {
    static final long serialVersionUID = -7034297190745766939L;

    public RoleException() {
        super();
    }

    public RoleException(String message) {
        super(message);
    }

    public RoleException(String message, Throwable cause) {
        super(message, cause);
    }

    public RoleException(Throwable cause) {
        super(cause);
    }

    protected RoleException(String message, Throwable cause,
                               boolean enableSuppression,
                               boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}

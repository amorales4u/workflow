package dev.c20.workflow.commons.tools;

public class WFException extends Exception {
    static final long serialVersionUID = -7034297190745766939L;

    public WFException() {
        super();
    }

    public WFException(String message) {
        super(message);
    }

    public WFException(String message, Throwable cause) {
        super(message, cause);
    }

    public WFException(Throwable cause) {
        super(cause);
    }

    protected WFException(String message, Throwable cause,
                          boolean enableSuppression,
                          boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}

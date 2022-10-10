package com.cdfive.es.exception;

/**
 * @author cdfive
 */
public class EsException extends RuntimeException {

    public EsException() {
        super();
    }

    public EsException(String message) {
        super(message);
    }

    public EsException(String message, Throwable cause) {
        super(message, cause);
    }

    public EsException(Throwable cause) {
        super(cause);
    }
}

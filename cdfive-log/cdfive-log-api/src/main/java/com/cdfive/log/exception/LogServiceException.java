package com.cdfive.log.exception;


import com.cdfive.common.exception.ServiceException;

/**
 * @author cdfive
 */
public class LogServiceException extends ServiceException {

    private static final long serialVersionUID = 5495179260580460835L;

    public LogServiceException() {
    }

    public LogServiceException(String message) {
        super(message);
    }

    public LogServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public LogServiceException(Throwable cause) {
        super(cause);
    }

    public LogServiceException(Integer code) {
        super(code);
    }

    public LogServiceException(String message, Integer code) {
        super(message, code);
    }

    public LogServiceException(String message, Throwable cause, Integer code) {
        super(message, cause, code);
    }

    public LogServiceException(Throwable cause, Integer code) {
        super(cause, code);
    }
}

package com.cdfive.log.exception;

import com.cdfive.framework.exception.ServiceException;

/**
 * @author cdfive
 */
public class LogServiceException extends ServiceException {

    private static final long serialVersionUID = 5495179260580460835L;

    public LogServiceException() {
    }

    public LogServiceException(Integer code) {
        super(code);
    }

    public LogServiceException(String message) {
        super(message);
    }

    public LogServiceException(Integer code, String message) {
        super(code, message);
    }

    public LogServiceException(Integer code, String message, Throwable cause) {
        super(code, message, cause);
    }

    public LogServiceException(Integer code, Throwable cause) {
        super(code, cause);
    }
}

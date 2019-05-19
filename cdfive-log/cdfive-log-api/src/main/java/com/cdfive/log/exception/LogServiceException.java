package com.cdfive.log.exception;

import com.cdfive.common.exception.ServiceException;

/**
 * @author cdfive
 */
public class LogServiceException extends ServiceException {

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
}

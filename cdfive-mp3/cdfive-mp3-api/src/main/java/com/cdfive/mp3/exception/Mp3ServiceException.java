package com.cdfive.mp3.exception;

import com.cdfive.common.exception.ServiceException;

/**
 * @author cdfive
 */
public class Mp3ServiceException extends ServiceException {
    public Mp3ServiceException() {
    }

    public Mp3ServiceException(String message) {
        super(message);
    }

    public Mp3ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public Mp3ServiceException(Throwable cause) {
        super(cause);
    }
}

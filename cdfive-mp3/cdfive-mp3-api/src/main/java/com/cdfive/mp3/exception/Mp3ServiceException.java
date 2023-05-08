package com.cdfive.mp3.exception;


import com.cdfive.framework.exception.ServiceException;

/**
 * @author cdfive
 */
public class Mp3ServiceException extends ServiceException {

    private static final long serialVersionUID = 2874579385560030288L;

    public Mp3ServiceException() {
    }

    public Mp3ServiceException(Integer code) {
        super(code);
    }

    public Mp3ServiceException(String message) {
        super(message);
    }

    public Mp3ServiceException(Integer code, String message) {
        super(code, message);
    }

    public Mp3ServiceException(Integer code, String message, Throwable cause) {
        super(code, message, cause);
    }

    public Mp3ServiceException(Integer code, Throwable cause) {
        super(code, cause);
    }
}

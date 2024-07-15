package com.cdfive.mp3.exception;


import com.cdfive.common.exception.ServiceException;

/**
 * @author cdfive
 */
public class Mp3ServiceException extends ServiceException {

    private static final long serialVersionUID = 2874579385560030288L;

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

    public Mp3ServiceException(Integer code) {
        super(code);
    }

    public Mp3ServiceException(String message, Integer code) {
        super(message, code);
    }

    public Mp3ServiceException(String message, Throwable cause, Integer code) {
        super(message, cause, code);
    }

    public Mp3ServiceException(Throwable cause, Integer code) {
        super(cause, code);
    }
}

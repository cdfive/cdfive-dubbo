package com.cdfive.common.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * @author cdfive
 */
public class ServiceException extends RuntimeException {

    private static final long serialVersionUID = -5869572661506966910L;

    @Getter
    @Setter
    private Integer code = 100000;

    public ServiceException() {

    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    public ServiceException(Integer code) {
        this.code = code;
    }

    public ServiceException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public ServiceException(String message, Throwable cause, Integer code) {
        super(message, cause);
        this.code = code;
    }

    public ServiceException(Throwable cause, Integer code) {
        super(cause);
        this.code = code;
    }
}

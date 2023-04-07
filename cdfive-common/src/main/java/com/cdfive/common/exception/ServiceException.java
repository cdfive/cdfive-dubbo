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

    @Getter
    @Setter
    private String description;

    public ServiceException(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    public ServiceException(String message, Integer code, String description) {
        super(message);
        this.code = code;
        this.description = description;
    }

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

    public ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

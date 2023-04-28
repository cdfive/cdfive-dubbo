package com.cdfive.framework.exception;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author cdfive
 */
@NoArgsConstructor
@Data
public class ServiceException extends RuntimeException {

    @Getter
    @Setter
    private Integer code = 100000;

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

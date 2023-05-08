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

    private static final long serialVersionUID = -1579765760968375988L;

    @Getter
    @Setter
    private Integer code = 100000;

    public ServiceException(Integer code) {
        this.code = code;
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public ServiceException(Integer code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public ServiceException(Integer code, Throwable cause) {
        super(cause);
        this.code = code;
    }
}

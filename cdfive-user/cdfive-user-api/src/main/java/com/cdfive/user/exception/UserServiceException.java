package com.cdfive.user.exception;


import com.cdfive.common.exception.ServiceException;

/**
 * @author cdfive
 */
public class UserServiceException extends ServiceException {

    private static final long serialVersionUID = 7520583092450621860L;

    public UserServiceException() {
    }

    public UserServiceException(String message) {
        super(message);
    }

    public UserServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserServiceException(Throwable cause) {
        super(cause);
    }

    public UserServiceException(Integer code) {
        super(code);
    }

    public UserServiceException(String message, Integer code) {
        super(message, code);
    }

    public UserServiceException(String message, Throwable cause, Integer code) {
        super(message, cause, code);
    }

    public UserServiceException(Throwable cause, Integer code) {
        super(cause, code);
    }
}

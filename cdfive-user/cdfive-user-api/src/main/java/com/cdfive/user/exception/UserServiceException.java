package com.cdfive.user.exception;


import com.cdfive.framework.exception.ServiceException;

/**
 * @author cdfive
 */
public class UserServiceException extends ServiceException {

    private static final long serialVersionUID = 7520583092450621860L;

    public UserServiceException() {
    }

    public UserServiceException(Integer code) {
        super(code);
    }

    public UserServiceException(String message) {
        super(message);
    }

    public UserServiceException(Integer code, String message) {
        super(code, message);
    }

    public UserServiceException(Integer code, String message, Throwable cause) {
        super(code, message, cause);
    }

    public UserServiceException(Integer code, Throwable cause) {
        super(code, cause);
    }
}

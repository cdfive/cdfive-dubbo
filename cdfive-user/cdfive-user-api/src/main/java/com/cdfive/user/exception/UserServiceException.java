package com.cdfive.user.exception;

import com.cdfive.common.exception.ServiceException;

/**
 * @author cdfive
 */
public class UserServiceException extends ServiceException {

    public UserServiceException() {
        super();
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
}

package com.cdfive.search.exception;


import com.cdfive.framework.exception.ServiceException;

/**
 * @author cdfive
 */
public class SearchServiceException extends ServiceException {

    private static final long serialVersionUID = -3876645489832154467L;

    public SearchServiceException() {
    }

    public SearchServiceException(Integer code) {
        super(code);
    }

    public SearchServiceException(String message) {
        super(message);
    }

    public SearchServiceException(Integer code, String message) {
        super(code, message);
    }

    public SearchServiceException(Integer code, String message, Throwable cause) {
        super(code, message, cause);
    }

    public SearchServiceException(Integer code, Throwable cause) {
        super(code, cause);
    }
}

package com.cdfive.search.exception;


import com.cdfive.common.exception.ServiceException;

/**
 * @author cdfive
 */
public class SearchServiceException extends ServiceException {

    private static final long serialVersionUID = -3876645489832154467L;

    public SearchServiceException() {
    }

    public SearchServiceException(String message) {
        super(message);
    }

    public SearchServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public SearchServiceException(Throwable cause) {
        super(cause);
    }

    public SearchServiceException(Integer code) {
        super(code);
    }

    public SearchServiceException(String message, Integer code) {
        super(message, code);
    }

    public SearchServiceException(String message, Throwable cause, Integer code) {
        super(message, cause, code);
    }

    public SearchServiceException(Throwable cause, Integer code) {
        super(cause, code);
    }
}

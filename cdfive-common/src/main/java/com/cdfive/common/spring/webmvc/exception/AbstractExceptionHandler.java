package com.cdfive.common.spring.webmvc.exception;

import com.cdfive.common.util.GenericClassUtil;

/**
 * @author cdfive
 */
public abstract class AbstractExceptionHandler<E extends Exception> implements ExceptionHandler<E> {

    @Override
    public boolean supportException(Exception ex) {
        return GenericClassUtil.getGenericTypeFromSuperClass(this.getClass(), 0).isInstance(ex);
    }
}

package com.cdfive.common.base;

/**
 * @author cdfive
 */
public abstract class AbstractService {

    protected void check(boolean condition, String msg) {
        if (!condition) {
            fail(msg);
        }
    }

    protected void checkNotNull(Object obj, String msg) {
        if (obj == null) {
            fail(msg);
        }
    }

    protected abstract void fail(String msg);
}

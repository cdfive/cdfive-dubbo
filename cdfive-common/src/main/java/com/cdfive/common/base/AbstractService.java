package com.cdfive.common.base;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ObjectUtils;

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

    protected void checkNotBlank(String str, String msg) {
        if (StringUtils.isBlank(str)) {
            fail(msg);
        }
    }

    protected void checkNotEmpty(Object obj, String msg) {
        if (ObjectUtils.isEmpty(obj)) {
            fail(msg);
        }
    }

    protected abstract void fail(String msg);
}

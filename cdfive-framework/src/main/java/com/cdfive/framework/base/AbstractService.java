package com.cdfive.framework.base;

import com.cdfive.framework.exception.ServiceException;
import com.cdfive.framework.util.SpringContextUtil;
import com.cdfive.framework.util.StringUtil;
import org.springframework.context.ApplicationEvent;
import org.springframework.util.ObjectUtils;

import java.util.Date;

/**
 * @author cdfive
 */
public abstract class AbstractService {

    protected void publishEvent(ApplicationEvent event) {
        SpringContextUtil.publishEvent(event);
    }

    protected void publishEvent(Object event) {
        SpringContextUtil.publishEvent(event);
    }

    protected void checkCondition(boolean condition, String msg) {
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
        if (StringUtil.isEmpty(str)) {
            fail(msg);
        }
    }

    protected void checkNotEmpty(Object obj, String msg) {
        if (ObjectUtils.isEmpty(obj)) {
            fail(msg);
        }
    }

    protected boolean isEmpty(Object obj) {
        return ObjectUtils.isEmpty(obj);
    }

    protected boolean isNotEmpty(Object obj) {
        return !ObjectUtils.isEmpty(obj);
    }

    protected Date now() {
        return new Date();
    }

    protected void fail(String msg) {
        throw exception(msg);
    }

    protected abstract ServiceException exception(String msg);
}

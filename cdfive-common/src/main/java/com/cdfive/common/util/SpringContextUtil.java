package com.cdfive.common.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;

/**
 * @author cdfive
 */
public class SpringContextUtil implements ApplicationContextAware {

    private static ApplicationContext APPLICATION_CONTEXT;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        APPLICATION_CONTEXT = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return APPLICATION_CONTEXT;
    }

    public static <T> T getBean(Class<T> beanType) {
        return getApplicationContext() != null ? getApplicationContext().getBean(beanType) : null;
    }

    public static Object getBean(String beanName) {
        return getApplicationContext() != null ? getApplicationContext().getBean(beanName) : null;
    }

    public static void publishEvent(ApplicationEvent event) {
        getApplicationContext().publishEvent(event);
    }
}

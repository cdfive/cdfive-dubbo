package com.cdfive.framework.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;

/**
 * @author cdfive
 */
public class SpringContextUtil implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return context;
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

    public static void publishEvent(Object event) {
        getApplicationContext().publishEvent(event);
    }
}

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
        return (T) context.getBean(beanType);
    }

    public static Object getBean(String beanName) {
        return context.getBean(beanName);
    }

    public static void publishEvent(ApplicationEvent event) {
        context.publishEvent(event);
    }

    public static void publishEvent(Object event) {
        context.publishEvent(event);
    }
}

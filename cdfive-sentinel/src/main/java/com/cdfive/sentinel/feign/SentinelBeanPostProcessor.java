package com.cdfive.sentinel.feign;

import feign.Feign;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * @author cdfive
 */
public class SentinelBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof Feign.Builder) {
            Feign.Builder builder = (Feign.Builder) bean;
            builder.addCapability(new SentinelCapability());
        }

        return bean;
    }
}

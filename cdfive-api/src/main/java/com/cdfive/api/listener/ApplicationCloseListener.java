package com.cdfive.api.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.serviceregistry.EurekaAutoServiceRegistration;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

/**
 * @author cdfive
 */
@Slf4j
@Component
public class ApplicationCloseListener implements ApplicationListener<ContextClosedEvent> {

    @Autowired
    private EurekaAutoServiceRegistration eurekaAutoServiceRegistration;

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        log.info("application closed!");

        log.info("eurekaAutoServiceRegistration stop start");
        eurekaAutoServiceRegistration.stop();
        log.info("eurekaAutoServiceRegistration stop end");
    }
}
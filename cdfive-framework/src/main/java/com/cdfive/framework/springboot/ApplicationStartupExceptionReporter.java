package com.cdfive.framework.springboot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootExceptionReporter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * @author cdfive
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class ApplicationStartupExceptionReporter implements SpringBootExceptionReporter {

    private ApplicationContext context;

    public ApplicationStartupExceptionReporter(ConfigurableApplicationContext context) {
        this.context = context;
    }

    @Override
    public boolean reportException(Throwable failure) {
        String appName = context.getEnvironment().getProperty("spring.application.name", "unknown");

        log.error("***************************");
        log.error("{} application startup failed", appName);
        log.error("***************************");
        log.error("error=>", failure);

        // TODO report to service admin
        return true;
    }
}

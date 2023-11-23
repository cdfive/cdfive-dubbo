package com.cdfive.framework.spring.boot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringBootExceptionReporter;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * @author cdfive
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class ApplicationStartupExceptionReporter implements SpringBootExceptionReporter {

    public ApplicationStartupExceptionReporter(ConfigurableApplicationContext context) {

    }

    @Override
    public boolean reportException(Throwable failure) {
        log.error("***************************");
        log.error("application startup failed", failure);
        log.error("***************************");

        // TODO report to service admin
        return true;
    }
}

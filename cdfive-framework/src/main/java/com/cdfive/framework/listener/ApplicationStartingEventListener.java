package com.cdfive.framework.listener;

import com.cdfive.framework.springboot.ApplicationStartupInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationListener;

/**
 * @author cdfive
 */
//@Slf4j
public class ApplicationStartingEventListener implements ApplicationListener<ApplicationStartingEvent> {

    @Override
    public void onApplicationEvent(ApplicationStartingEvent event) {
        // Note: logging system has not been initialized yet
//        log.info("spring-boot application is starting");

        ApplicationStartupInfo.starting();
    }
}

package com.cdfive.framework.listener;

import com.cdfive.framework.message.producer.LogRequestProducer;
import com.cdfive.framework.properties.AppProperties;
import com.cdfive.framework.springboot.ApplicationStartupInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author cdfive
 */
@Slf4j
@Component
public class ApplicationReadyEventListener implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private AppProperties appProperties;

    @Autowired(required = false)
    private LogRequestProducer logRequestProducer;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        ApplicationStartupInfo.started();
        log.info("{} application startup success,cost={}ms", appProperties.getAppName(), ApplicationStartupInfo.getStartTimeCostMs());

        // TODO report to service admin
    }
}

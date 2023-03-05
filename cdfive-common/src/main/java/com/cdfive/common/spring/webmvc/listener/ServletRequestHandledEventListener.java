package com.cdfive.common.spring.webmvc.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.ServletRequestHandledEvent;

/**
 * @author cdfive
 */
@Slf4j
@Component
public class ServletRequestHandledEventListener implements ApplicationListener<ServletRequestHandledEvent> {

    @Override
    public void onApplicationEvent(ServletRequestHandledEvent event) {
        if (event.wasFailure()) {
            log.error("[springwebmvc process error]" + event.toString());
        }
//        if (event.wasFailure()) {
//            log.error("[mvc_error]requestUrl={},clientAddress={},cost={}ms"
//                    , event.getRequestUrl(), event.getClientAddress(), event.getProcessingTimeMillis(), event.getFailureCause());
//        } else {
//            log.info("[mvc_success]requestUrl={},clientAddress={},cost={}ms"
//                    , event.getRequestUrl(), event.getClientAddress(), event.getProcessingTimeMillis());
//        }
    }
}

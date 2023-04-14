package com.cdfive.common.message.producer;

import com.cdfive.common.util.JacksonUtil;
import com.cdfive.common.vo.AppRestApiLogContextVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Queue;

/**
 * @author xiejihan
 * @date 2023-04-07
 */
@Slf4j
@Component
public class AppRestApiProducer {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    @Qualifier("appRestApiLogQueue")
    private Queue appRestApiLogQueue;

//    @Value("${cdfive.framework.queue.appRestApiLogQueue:appRestApiLogQueue}")
//    private String appRestApiLogQueue;

    public void send(AppRestApiLogContextVo contextVo) {
        jmsMessagingTemplate.convertAndSend(appRestApiLogQueue, contextVo);
        if (log.isDebugEnabled()) {
            log.debug("AppRestApiProducer send={}", JacksonUtil.objToJson(appRestApiLogQueue));
        }
    }
}

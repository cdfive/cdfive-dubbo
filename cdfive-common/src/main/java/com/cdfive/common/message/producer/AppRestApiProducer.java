package com.cdfive.common.message.producer;

import com.cdfive.common.util.JacksonUtil;
import com.cdfive.common.vo.AppRestApiLogContextVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Queue;

/**
 * @author xiejihan
 * @date 2023-04-07
 */
@Slf4j
@ConditionalOnClass({JmsMessagingTemplate.class, Queue.class, ActiveMQQueue.class})
@Component
public class AppRestApiProducer {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    @Qualifier("appRestApiLogQueue")
    private Queue appRestApiLogQueue;

    @Bean
    public Queue appRestApiLogQueue() {
        log.info("appRestApiLogQueue init");
        return new ActiveMQQueue("appRestApiLogQueue");
    }

//    @Value("${cdfive.framework.queue.appRestApiLogQueue:appRestApiLogQueue}")
//    private String appRestApiLogQueue;

    public void send(AppRestApiLogContextVo contextVo) {
        if (log.isInfoEnabled()) {
            log.info("AppRestApiProducer contextVo={}", JacksonUtil.objToJson(contextVo));
        }

        jmsMessagingTemplate.convertAndSend(appRestApiLogQueue, contextVo);
    }
}

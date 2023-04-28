package com.cdfive.framework.message.producer;

import com.cdfive.framework.message.vo.LogRequestMessageVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Queue;


/**
 * @author cdfive
 */
@Slf4j
@ConditionalOnClass({JmsMessagingTemplate.class, Queue.class, ActiveMQQueue.class})
@Component
public class LogRequestProducer {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    @Qualifier("logRequestQueue")
    private Queue logRequestQueue;

    @Bean
    public Queue logRequestQueue() {
        return new ActiveMQQueue("logRequestQueue");
    }

    public void send(LogRequestMessageVo messageVo) {
        jmsMessagingTemplate.convertAndSend(logRequestQueue, messageVo);
    }
}

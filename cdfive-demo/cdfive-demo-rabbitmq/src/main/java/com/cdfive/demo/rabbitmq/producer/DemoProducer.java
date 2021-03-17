package com.cdfive.demo.rabbitmq.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author cdfive
 */
@Slf4j
@Component
public class DemoProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void produceMessage(String message) {
        long start = System.currentTimeMillis();
        log.info("DemoProducer produceMessage start,message={}", message);
        rabbitTemplate.convertAndSend("demo_queue",  message);
        log.info("DemoProducer produceMessage end,cost={}ms", System.currentTimeMillis() - start);
    }
}

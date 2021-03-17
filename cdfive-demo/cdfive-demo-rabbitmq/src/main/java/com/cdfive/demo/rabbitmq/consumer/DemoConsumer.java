package com.cdfive.demo.rabbitmq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author cdfive
 */
@Slf4j
@Component
//@RabbitListener(queues = "demo_queue", queuesToDeclare = @Queue("demo_queue"))
//@RabbitListener(queues = "demo_queue")
@RabbitListener(queuesToDeclare = @Queue("demo_queue"))
public class DemoConsumer {

    @RabbitHandler
    public void handle(String message) {
        log.info("DemoConsumer handle message={}", message);
    }
}
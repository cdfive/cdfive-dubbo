package com.cdfive.demo.rabbitmq.consumer;

import com.cdfive.demo.rabbitmq.util.JsonUtil;
import com.cdfive.demo.rabbitmq.vo.TestVo;
import com.fasterxml.jackson.databind.ObjectMapper;
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
@RabbitListener(queuesToDeclare = @Queue("demo_obj_queue"))
public class DemoObjConsumer {

    private static ObjectMapper objectMapper = new ObjectMapper();

    @RabbitHandler
    public void handle(TestVo vo) {
        log.info("DemoObjConsumer handle message={}", JsonUtil.objToJson(vo));
    }
}

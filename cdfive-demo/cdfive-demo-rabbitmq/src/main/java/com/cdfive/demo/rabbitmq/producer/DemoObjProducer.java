package com.cdfive.demo.rabbitmq.producer;

import com.cdfive.demo.rabbitmq.util.JsonUtil;
import com.cdfive.demo.rabbitmq.vo.TestVo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author cdfive
 */
@Slf4j
@Component
public class DemoObjProducer {

    private static ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void produceObjMessage(TestVo vo) {
        log.info("DemoProducer produceObjMessage start,message={}", JsonUtil.objToJson(vo));

        long start = System.currentTimeMillis();
        rabbitTemplate.convertAndSend("demo_obj_queue",  vo);
        log.info("DemoProducer produceObjMessage end,cost={}ms", System.currentTimeMillis() - start);
    }
}

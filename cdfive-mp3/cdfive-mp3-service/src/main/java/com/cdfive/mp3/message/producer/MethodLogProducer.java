package com.cdfive.mp3.message.producer;

import com.alibaba.fastjson.JSON;
import com.cdfive.log.vo.AddMethodLogVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Queue;


/**
 * @author cdfive
 */
@Slf4j
@Component
public class MethodLogProducer {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    @Qualifier("methodLogQueue")
    private Queue methodLogQueue;

    public void send(AddMethodLogVo addMethodLogVo) {
        jmsMessagingTemplate.convertAndSend(methodLogQueue, addMethodLogVo);
        log.info("MethodLogProducer send={}", JSON.toJSONString(addMethodLogVo));
    }
}

package com.cdfive.demo.rabbitmq.controller;

import com.cdfive.demo.rabbitmq.producer.DemoObjProducer;
import com.cdfive.demo.rabbitmq.producer.DemoProducer;
import com.cdfive.demo.rabbitmq.util.JsonUtil;
import com.cdfive.demo.rabbitmq.vo.TestVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cdfive
 */
@RequestMapping("test")
@RestController
public class TestController {

    @Autowired
    private DemoProducer demoProducer;

    @Autowired
    private DemoObjProducer demoObjProducer;

    @RequestMapping("send")
    public String send(String message) {
        demoProducer.produceMessage(message);
        return message;
    }

    @RequestMapping("sendObj")
    public String send(@RequestBody TestVo vo) {
        demoObjProducer.produceObjMessage(vo);
        return JsonUtil.objToJson(vo);
    }
}

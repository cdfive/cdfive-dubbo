package com.cdfive.gateway.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cdfive
 */
@Slf4j
@RefreshScope
@RequestMapping("/test")
@RestController
public class TestController {

    @Value("${biz.name:default}")
    private String bizName;

    @RequestMapping("/name")
    public String name() {
        log.info("bizName={}", bizName);
        return bizName;
    }
}

package com.cdfive.web;

import com.alibaba.csp.sentinel.init.InitExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * @author cdfive
 */
@Slf4j
@ImportResource("classpath:/config/applicationContext.xml")
@SpringBootApplication(scanBasePackages = {"com.cdfive"})
public class WebApplication {
    public static void main(String[] args) {
        InitExecutor.doInit();

        SpringApplication.run(WebApplication.class, args);
        log.info("cdfive-web application started");
    }
}
package com.cdfive.api;

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
public class ApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
        log.info("cdfive-api application started");
    }
}

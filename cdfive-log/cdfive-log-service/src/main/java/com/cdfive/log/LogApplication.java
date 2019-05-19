package com.cdfive.log;

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
public class LogApplication {
    public static void main(String[] args) {
        SpringApplication.run(LogApplication.class, args);
        log.info("cdfive-log application started");
    }
}
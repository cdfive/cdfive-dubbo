package com.cdfive.mp3;

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
public class Mp3Application {
    public static void main(String[] args) {
        SpringApplication.run(Mp3Application.class, args);
        log.info("cdfive-mp3 application started");
    }
}
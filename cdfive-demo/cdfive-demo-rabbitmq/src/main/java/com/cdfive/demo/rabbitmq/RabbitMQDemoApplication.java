package com.cdfive.demo.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author cdfive
 */
@Slf4j
@SpringBootApplication
public class RabbitMQDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(RabbitMQDemoApplication.class, args);
        log.info("RabbitMQDemoApplication started!");
    }
}

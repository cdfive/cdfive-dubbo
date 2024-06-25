package com.cdfive.demo.mybatis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author cdfive
 */
@Slf4j
@SpringBootApplication
public class MybatisDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MybatisDemoApplication.class, args);
        log.info("MybatisDemoApplication started!");
    }
}

package com.cdfive.demo.springdatajpa;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author cdfive
 */
@Slf4j
@SpringBootApplication
public class SpringDataJPADemoApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .web(WebApplicationType.NONE)
                .sources(SpringDataJPADemoApplication.class)
                .build()
                .run(args);
        log.info("SpringDataJPADemoApplication started!");
    }
}

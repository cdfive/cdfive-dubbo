package com.cdfive.search;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.jms.annotation.EnableJms;

/**
 * @author cdfive
 */
@Slf4j
@EnableJms
@ImportResource({"classpath:/config/applicationContext.xml"})
@SpringBootApplication(scanBasePackages = "com.cdfive")
public class SearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(SearchApplication.class, args);
        log.info("cdfive-search application started!");
    }
}

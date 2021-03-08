package com.cdfive.demo.springdatajpa;

import com.cdfive.demo.springdatajpa.service.MenuNPlusOneService;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author cdfive
 */
@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .web(WebApplicationType.NONE)
                .sources(DemoApplication.class)
                .build()
                .run(args);
        System.out.println("cdfive-demo-spring-data-jpa started!");
    }
}

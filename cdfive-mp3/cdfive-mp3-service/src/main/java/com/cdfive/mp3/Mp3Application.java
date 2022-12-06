package com.cdfive.mp3;

import com.cdfive.support.jpa.repository.BaseRepositoryFactoryBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EurekaClientAutoConfiguration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author cdfive
 */
@Slf4j
@ImportResource({"classpath:/config/applicationContext.xml"})
@EnableCaching
@EnableJpaRepositories(repositoryFactoryBeanClass = BaseRepositoryFactoryBean.class, basePackages = "com.cdfive.mp3")
@SpringBootApplication(scanBasePackages = {"com.cdfive"}, exclude = {EurekaClientAutoConfiguration.class})
public class Mp3Application {
    public static void main(String[] args) {
        SpringApplication.run(Mp3Application.class, args);
        log.info("cdfive-mp3 application started!");
    }
}
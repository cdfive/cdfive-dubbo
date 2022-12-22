package com.cdfive.log;

import com.cdfive.support.jpa.repository.BaseRepositoryFactoryBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EurekaClientAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClientConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author cdfive
 */
@Slf4j
@ImportResource("classpath:/config/applicationContext.xml")
@EnableJpaRepositories(repositoryFactoryBeanClass = BaseRepositoryFactoryBean.class, basePackages = "com.cdfive.log")
@SpringBootApplication(scanBasePackages = {"com.cdfive"}, exclude = {EurekaClientAutoConfiguration.class, EurekaDiscoveryClientConfiguration.class})
public class LogApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(LogApplication.class, args);
        log.info("cdfive-log application started!");
    }
}
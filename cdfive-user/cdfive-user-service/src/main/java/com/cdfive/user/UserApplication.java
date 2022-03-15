package com.cdfive.user;

import com.cdfive.support.jpa.repository.BaseRepositoryFactoryBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author cdfive
 */
@Slf4j
@ImportResource({"classpath:/config/applicationContext.xml"})
@EnableJpaRepositories(repositoryFactoryBeanClass = BaseRepositoryFactoryBean.class, basePackages = "com.cdfive.user")
@SpringBootApplication(scanBasePackages = {"com.cdfive"})
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
        log.info("cdfive-user application started!");
    }
}

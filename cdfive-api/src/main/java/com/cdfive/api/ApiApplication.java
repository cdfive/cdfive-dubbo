package com.cdfive.api;

import com.alibaba.cloud.nacos.discovery.NacosDiscoveryAutoConfiguration;
import com.alibaba.cloud.nacos.discovery.NacosDiscoveryClientConfiguration;
import com.alibaba.cloud.nacos.endpoint.NacosDiscoveryEndpointAutoConfiguration;
import com.alibaba.cloud.nacos.registry.NacosServiceRegistryAutoConfiguration;
import com.cdfive.api.controller.ApplicationController;
import com.cdfive.api.listener.ApplicationCloseListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EurekaClientAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClientConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.ImportResource;

/**
 * @author cdfive
 */
@Slf4j
@EnableFeignClients(basePackages = {"com.cdfive.mp3.api"})
@ImportResource("classpath:/config/applicationContext.xml")
@ComponentScan(excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {ApplicationController.class, ApplicationCloseListener.class})})
//@SpringBootApplication(scanBasePackages = {"com.cdfive"}, exclude = {EurekaClientAutoConfiguration.class, EurekaDiscoveryClientConfiguration.class})
@SpringBootApplication(scanBasePackages = {"com.cdfive"}, exclude = {NacosDiscoveryClientConfiguration.class, NacosDiscoveryAutoConfiguration.class
        , NacosServiceRegistryAutoConfiguration.class, NacosDiscoveryEndpointAutoConfiguration.class})
public class ApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
        log.info("cdfive-api application started!");
    }
}

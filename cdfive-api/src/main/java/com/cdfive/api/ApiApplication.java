package com.cdfive.api;

import com.alibaba.cloud.nacos.NacosServiceAutoConfiguration;
import com.alibaba.cloud.nacos.discovery.NacosDiscoveryAutoConfiguration;
import com.alibaba.cloud.nacos.discovery.NacosDiscoveryClientConfiguration;
import com.alibaba.cloud.nacos.discovery.reactive.NacosReactiveDiscoveryClientConfiguration;
import com.alibaba.cloud.nacos.endpoint.NacosDiscoveryEndpointAutoConfiguration;
import com.alibaba.cloud.nacos.registry.NacosServiceRegistryAutoConfiguration;
import com.alibaba.cloud.nacos.ribbon.RibbonNacosAutoConfiguration;
import com.cdfive.api.controller.ApplicationController;
import com.cdfive.api.listener.ApplicationCloseListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EurekaClientAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClientConfiguration;
import org.springframework.cloud.netflix.ribbon.ServerIntrospector;
import org.springframework.cloud.netflix.ribbon.eureka.EurekaRibbonClientConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;

import java.util.Map;

/**
 * @author cdfive
 */
@Slf4j
@EnableFeignClients(basePackages = {"com.cdfive.mp3.api", "com.cdfive.search.api"})
@ImportResource("classpath:/config/applicationContext.xml")
//@ComponentScan(basePackages = {"com.cdfive"}, excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {ApplicationController.class, ApplicationCloseListener.class})})
//@SpringBootApplication(scanBasePackages = {"com.cdfive"}, exclude = {EurekaClientAutoConfiguration.class, EurekaDiscoveryClientConfiguration.class})
//@SpringBootApplication(scanBasePackages = {"com.cdfive"}, exclude = {
//        NacosDiscoveryClientConfiguration.class
//        , NacosDiscoveryAutoConfiguration.class
//        , NacosServiceRegistryAutoConfiguration.class
//        , NacosDiscoveryEndpointAutoConfiguration.class})
//@SpringBootApplication
@SpringBootApplication(scanBasePackages = {"com.cdfive"})
public class ApiApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(ApiApplication.class, args);
        log.info("cdfive-api application started!");

//        EurekaRibbonClientConfiguration bean = ctx.getBean(EurekaRibbonClientConfiguration.class);
//        ServerIntrospector bean = ctx.getBean(ServerIntrospector.class);
//        System.out.println(bean);

//        Map<String, HandlerMethodReturnValueHandler> beansOfType = ctx.getBeansOfType(HandlerMethodReturnValueHandler.class);
//        System.out.println("done");
    }
}

package com.cdfive.mp3;

//import com.cdfive.springboot.config.ExtAutoConfig;
//import com.cdfive.springboot.config.ExtAutoConfig;
import com.cdfive.support.jpa.repository.BaseRepositoryFactoryBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author cdfive
 */
@Slf4j
@EnableFeignClients(basePackages = {"com.cdfive.log.api"})
@ImportResource({"classpath:/config/applicationContext.xml"})
@EnableCaching
@EnableJpaRepositories(repositoryFactoryBeanClass = BaseRepositoryFactoryBean.class, basePackages = "com.cdfive.mp3")
//@ComponentScan(excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {SwaggerConfig.class, SwaggerDubboConfig.class})})
//@SpringBootApplication(scanBasePackages = {"com.cdfive"}, exclude = {EurekaClientAutoConfiguration.class, EurekaDiscoveryClientConfiguration.class})
//@SpringBootApplication(scanBasePackages = {"com.cdfive"}, exclude = {
//        NacosDiscoveryAutoConfiguration.class
//        , NacosDiscoveryEndpointAutoConfiguration.class
//        , NacosServiceRegistryAutoConfiguration.class
//        , NacosDiscoveryClientConfiguration.class
//        , NacosReactiveDiscoveryClientConfiguration.class
////        , LoadBalancerNacosAutoConfiguration.class
//        , NacosServiceAutoConfiguration.class
////        , UtilIPv6AutoConfiguration.class
//})
@SpringBootApplication(scanBasePackages = {"com.cdfive"})
//@SpringBootApplication(scanBasePackages = {"com.cdfive"}, exclude = ExtAutoConfig.class)
public class Mp3Application {

    public static void main(String[] args) {
        SpringApplication.run(Mp3Application.class, args);
        log.info("cdfive-mp3 application started!");
    }
}
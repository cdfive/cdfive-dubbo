package com.cdfive.gateway;

//import com.cdfive.gateway.config.RibbonConfig;
import com.alibaba.cloud.nacos.NacosServiceAutoConfiguration;
import com.alibaba.cloud.nacos.discovery.NacosDiscoveryAutoConfiguration;
import com.alibaba.cloud.nacos.discovery.NacosDiscoveryClientConfiguration;
import com.alibaba.cloud.nacos.discovery.configclient.NacosConfigServerAutoConfiguration;
import com.alibaba.cloud.nacos.discovery.reactive.NacosReactiveDiscoveryClientConfiguration;
import com.alibaba.cloud.nacos.endpoint.NacosDiscoveryEndpointAutoConfiguration;
import com.alibaba.cloud.nacos.loadbalancer.LoadBalancerNacosAutoConfiguration;
import com.alibaba.cloud.nacos.registry.NacosServiceRegistryAutoConfiguration;
import com.alibaba.cloud.nacos.utils.UtilIPv6AutoConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EurekaClientAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClientConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.filter.reactive.HiddenHttpMethodFilter;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * @author cdfive
 */
@Slf4j
//@ComponentScan(excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {RibbonConfig.class})})
//@SpringBootApplication(exclude = {EurekaClientAutoConfiguration.class, EurekaDiscoveryClientConfiguration.class})
//@SpringBootApplication
@SpringBootApplication(scanBasePackages = {"com.cdfive"}, exclude = {
        NacosDiscoveryAutoConfiguration.class, NacosDiscoveryEndpointAutoConfiguration.class
        , NacosServiceRegistryAutoConfiguration.class, NacosDiscoveryClientConfiguration.class
        , NacosReactiveDiscoveryClientConfiguration.class, LoadBalancerNacosAutoConfiguration.class
        , NacosServiceAutoConfiguration.class, UtilIPv6AutoConfiguration.class})
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
        log.info("cdfive-gateway application started!");
    }

    @Bean
    public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
        return new HiddenHttpMethodFilter() {
            @Override
            public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
                return chain.filter(exchange);
            }
        };
    }
}

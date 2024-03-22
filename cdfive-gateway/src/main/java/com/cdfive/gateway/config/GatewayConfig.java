package com.cdfive.gateway.config;

import com.cdfive.gateway.predicate.CustomReadBodyRoutePredicateFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @author cdfive
 */
@Configuration
public class GatewayConfig {

//    @Primary
    @Bean
    public CustomReadBodyRoutePredicateFactory customReadBodyRoutePredicateFactory() {
        return new CustomReadBodyRoutePredicateFactory();
    }
}

package com.cdfive.framework.config;

import com.cdfive.framework.springcloud.ribbon.SmartPing;
import com.cdfive.framework.springcloud.ribbon.SmartRule;
import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.IRule;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author cdfive
 */
@ConditionalOnProperty(value = "framework.ribbon.useSmartRule", havingValue = "true", matchIfMissing = false)
@Configuration("frameworkRibbonConfig")
public class RibbonConfig {

    @ConditionalOnClass({IRule.class})
    @Bean
    public IRule smartRule() {
        return new SmartRule();
    }

    @ConditionalOnClass({RestTemplate.class, IPing.class})
    @Bean
    public IPing smartPing() {
        return new SmartPing();
    }
}

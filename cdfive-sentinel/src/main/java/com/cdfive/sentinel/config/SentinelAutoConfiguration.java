package com.cdfive.sentinel.config;

import com.alibaba.csp.sentinel.Env;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author cdfive
 */
@Configuration
@ConditionalOnProperty(name = "sentinel.enabled", matchIfMissing = true)
@ConditionalOnClass(Env.class)
@EnableConfigurationProperties(SentinelProperties.class)
public class SentinelAutoConfiguration {

    @ConditionalOnMissingBean({SentinelConfiguration.class})
    @Bean
    public SentinelConfiguration getSentinelConfig() {
        return new SentinelConfiguration();
    }
}

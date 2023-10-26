package com.cdfive.sentinel.config;

import com.alibaba.csp.sentinel.Env;
import com.alibaba.csp.sentinel.adapter.servlet.CommonFilter;
import com.alibaba.csp.sentinel.adapter.spring.webmvc.SentinelWebInterceptor;
import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.DefaultBlockExceptionHandler;
import com.alibaba.csp.sentinel.adapter.spring.webmvc.config.SentinelWebMvcConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author cdfive
 */
@Configuration
@ConditionalOnProperty(name = "sentinel.enabled", matchIfMissing = true)
@ConditionalOnClass(Env.class)
@EnableConfigurationProperties(SentinelProperties.class)
public class SentinelAutoConfiguration {

    @Bean
    public SentinelConfiguration getSentinelConfig() {
        return new SentinelConfiguration();
    }

    @ConditionalOnProperty(name = "sentinel.enableSpringWebMvc", matchIfMissing = true)
    @ConditionalOnClass(SentinelWebInterceptor.class)
    @Bean
    public WebMvcConfigurer sentinelWebMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                SentinelWebMvcConfig config = new SentinelWebMvcConfig();
                config.setBlockExceptionHandler(new DefaultBlockExceptionHandler());
                config.setHttpMethodSpecify(true);
                config.setWebContextUnify(true);

                SentinelWebInterceptor sentinelWebInterceptor = new SentinelWebInterceptor(config);

                registry.addInterceptor(sentinelWebInterceptor);
            }
        };
    }

    @ConditionalOnProperty(name = "sentinel.enableServlet", matchIfMissing = false)
    @ConditionalOnClass(CommonFilter.class)
    @Bean
    public FilterRegistrationBean<CommonFilter> sentinelFilterRegistration() {
        FilterRegistrationBean<CommonFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new CommonFilter());
        registration.addUrlPatterns("/*");
        registration.setName("sentinelFilter");
        registration.setOrder(1);
        return registration;
    }
}

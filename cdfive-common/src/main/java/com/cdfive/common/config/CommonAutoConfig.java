package com.cdfive.common.config;

import com.cdfive.common.servlet.filter.AppRestApiLogFilter;
import com.cdfive.common.util.SpringContextUtil;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jms.Queue;

/**
 * @author cdfive
 */
@Configuration
public class CommonAutoConfig {

    @ConditionalOnMissingBean({SpringContextUtil.class})
    @Bean
    public SpringContextUtil getSpringContextUtil() {
        return new SpringContextUtil();
    }

    @Bean
    public FilterRegistrationBean<AppRestApiLogFilter> appRestApiLogFilterRegistrationBean(AppRestApiLogFilter appRestApiLogFilter) {
        FilterRegistrationBean<AppRestApiLogFilter> filterRegistrationBean = new FilterRegistrationBean<AppRestApiLogFilter>(appRestApiLogFilter);
        filterRegistrationBean.addUrlPatterns(new String[]{"/*"});
        return filterRegistrationBean;
    }

    @Bean
    public Queue appRestApiLogQueue() {
        return new ActiveMQQueue("appRestApiLogQueue");
    }
}

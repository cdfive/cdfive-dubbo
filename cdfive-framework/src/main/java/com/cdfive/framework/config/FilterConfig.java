package com.cdfive.framework.config;

import com.cdfive.framework.servlet.filter.LogRequestFilter;
import com.cdfive.framework.servlet.filter.SlfMdcTraceIdFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import javax.servlet.Filter;

/**
 * @author cdfive
 */
@ConditionalOnClass({Filter.class, FilterRegistrationBean.class})
@Configuration("frameworkFilterConfig")
public class FilterConfig {

    @ConditionalOnBean(LogRequestFilter.class)
    @Bean
    public FilterRegistrationBean<LogRequestFilter> logRequestFilterRegistrationBean(LogRequestFilter filter) {
        FilterRegistrationBean<LogRequestFilter> filterRegistrationBean = new FilterRegistrationBean<LogRequestFilter>(filter);
        filterRegistrationBean.addUrlPatterns(new String[]{"/*"});
        filterRegistrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return filterRegistrationBean;
    }

    @ConditionalOnBean(SlfMdcTraceIdFilter.class)
    @Bean
    public FilterRegistrationBean<SlfMdcTraceIdFilter> slfMdcFilterrRegistrationBean(SlfMdcTraceIdFilter filter) {
        FilterRegistrationBean<SlfMdcTraceIdFilter> filterRegistrationBean = new FilterRegistrationBean<SlfMdcTraceIdFilter>(filter);
        filterRegistrationBean.addUrlPatterns(new String[]{"/*"});
        filterRegistrationBean.setOrder(10);
        return filterRegistrationBean;
    }
}

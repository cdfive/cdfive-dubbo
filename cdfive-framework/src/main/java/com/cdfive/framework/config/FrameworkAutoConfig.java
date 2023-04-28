package com.cdfive.framework.config;

import com.cdfive.framework.servlet.filter.LogRequestFilter;
import com.cdfive.framework.servlet.filter.SlfMdcFilter;
import com.cdfive.framework.util.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author cdfive
 */
@Slf4j
@ComponentScan({"cn.cdfive.framework"})
@Configuration
public class FrameworkAutoConfig {

    @ConditionalOnMissingBean({SpringContextUtil.class})
    @Bean
    public SpringContextUtil springContextUtil() {
        return new SpringContextUtil();
    }

    @ConditionalOnBean(LogRequestFilter.class)
    @Bean
    public FilterRegistrationBean<LogRequestFilter> logRequestFilterRegistrationBean(LogRequestFilter appRestApiLogFilter) {
        FilterRegistrationBean<LogRequestFilter> filterRegistrationBean = new FilterRegistrationBean<LogRequestFilter>(appRestApiLogFilter);
        filterRegistrationBean.addUrlPatterns(new String[]{"/*"});
        filterRegistrationBean.setOrder(1);
        return filterRegistrationBean;
    }

    @ConditionalOnBean(SlfMdcFilter.class)
    @Bean
    public FilterRegistrationBean<SlfMdcFilter> slfMdcFilterrRegistrationBean(SlfMdcFilter slfMdcFilter) {
        FilterRegistrationBean<SlfMdcFilter> filterRegistrationBean = new FilterRegistrationBean<SlfMdcFilter>(slfMdcFilter);
        filterRegistrationBean.addUrlPatterns(new String[]{"/*"});
        filterRegistrationBean.setOrder(10);
        return filterRegistrationBean;
    }
}

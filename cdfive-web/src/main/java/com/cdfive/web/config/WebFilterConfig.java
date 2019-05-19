package com.cdfive.web.config;

import com.cdfive.common.servlet.filter.PageFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author cdfive
 */
@Configuration
public class WebFilterConfig {

    @Bean
    public PageFilter pageFilter() {
        return new PageFilter();
    }

    @Bean
    public FilterRegistrationBean pageFilterRegistrationBean() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(pageFilter());
        registration.addUrlPatterns("/*");
        registration.setName("pageFilter");
        registration.addInitParameter("prefix","/WEB-INF/view");
        registration.addInitParameter("suffix",".jsp");
        registration.addInitParameter("ignorePath","/static/,/v1,/2017/mp3");
        registration.setOrder(4);
        return registration;
    }
}

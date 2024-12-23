package com.cdfive.demo.mybatis.ext;

import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DelegatingWebMvcConfiguration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

/**
 * Note: not recommend!
 * {@link WebMvcAutoConfiguration} will not take effect
 *
 * @author cdfive
 */
// !!!Note: WebMvcAutoConfiguration will not be in effective
//@Configuration
//public class ExtWebMvcConfigurationSupport extends WebMvcConfigurationSupport {
public class ExtWebMvcConfigurationSupport extends DelegatingWebMvcConfiguration {

    @Override
    protected RequestMappingHandlerAdapter createRequestMappingHandlerAdapter() {
        return new ExtRequestMappingHandlerAdapter();
    }
}

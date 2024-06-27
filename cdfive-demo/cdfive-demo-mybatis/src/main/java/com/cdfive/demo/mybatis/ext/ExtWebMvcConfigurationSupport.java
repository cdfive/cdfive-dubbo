package com.cdfive.demo.mybatis.ext;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

/**
 * @author cdfive
 */
@Configuration
public class ExtWebMvcConfigurationSupport extends WebMvcConfigurationSupport {

    @Override
    protected RequestMappingHandlerAdapter createRequestMappingHandlerAdapter() {
        return new ExtRequestMappingHandlerAdapter();
    }
}

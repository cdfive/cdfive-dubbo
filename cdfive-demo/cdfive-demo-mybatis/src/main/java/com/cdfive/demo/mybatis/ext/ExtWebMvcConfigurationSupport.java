package com.cdfive.demo.mybatis.ext;

import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

/**
 * Note: not recommend!
 * {@link WebMvcAutoConfiguration} will not take effect
 *
 * @author cdfive
 */
@Configuration
public class ExtWebMvcConfigurationSupport extends WebMvcConfigurationSupport {

    @Override
    protected RequestMappingHandlerAdapter createRequestMappingHandlerAdapter() {
        return new ExtRequestMappingHandlerAdapter();
    }
}

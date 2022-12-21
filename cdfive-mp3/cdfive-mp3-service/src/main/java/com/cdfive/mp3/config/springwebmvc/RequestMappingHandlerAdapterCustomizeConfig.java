package com.cdfive.mp3.config.springwebmvc;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author cdfive
 */
@Configuration
public class RequestMappingHandlerAdapterCustomizeConfig implements InitializingBean {

    @Autowired
    private RequestMappingHandlerAdapter requestMappingHandlerAdapter;

    @Override
    public void afterPropertiesSet() throws Exception {
        if (requestMappingHandlerAdapter == null) {
            return;
        }
        replaceRequestResponseBodyMethodProcessor();
    }

    public void replaceRequestResponseBodyMethodProcessor() {
        List<HandlerMethodArgumentResolver> handlerMethodArgumentResolver = requestMappingHandlerAdapter.getArgumentResolvers();
        if (!CollectionUtils.isEmpty(handlerMethodArgumentResolver)) {
            Optional<HandlerMethodArgumentResolver> resolverOptional = handlerMethodArgumentResolver.stream().filter(c -> c instanceof RequestResponseBodyMethodProcessor).findAny();
            if (resolverOptional.isPresent()) {
                List<HandlerMethodArgumentResolver> newHandlerMethodArgumentResolvers = new ArrayList<>(handlerMethodArgumentResolver);
                Collections.replaceAll(newHandlerMethodArgumentResolvers, resolverOptional.get(), new RequestResponseBodyMethodProcessorWrapper(resolverOptional.get()));
                requestMappingHandlerAdapter.setArgumentResolvers(newHandlerMethodArgumentResolvers);
            }
        }
    }
}

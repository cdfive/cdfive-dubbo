package com.cdfive.web.config;

import com.cdfive.common.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.PostConstruct;

/**
 * @author cdfive
 */
@Slf4j
@Configuration
//public class WebMvcConfig extends WebMvcConfigurerAdapter {
public class WebMvcConfig implements WebMvcConfigurer {

    @PostConstruct
    public void init() {
        log.info("WebMvcConfig init");
    }

    @Value("${resources.static.pathPatterns}")
    private String staticPathPatterns;

    @Value("${resources.static.resourceLocations}")
    private String staticResourceLocations;

    @Value("${resources.mp3.pathPatterns}")
    private String mp3PathPatterns;

    @Value("${resources.mp3.resourceLocations}")
    private String resourceLocations;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        addResourceMapping(registry, staticPathPatterns, staticResourceLocations);
        addResourceMapping(registry, mp3PathPatterns, resourceLocations);
//        super.addResourceHandlers(registry);
    }

    private void addResourceMapping(ResourceHandlerRegistry registry, String pathPatterns, String resourceLocations) {
        if (StringUtil.isBlank(pathPatterns) || StringUtil.isBlank(resourceLocations)) {
            return;
        }

        registry.addResourceHandler(pathPatterns).addResourceLocations(resourceLocations);
        log.info("addResourceMapping: " + pathPatterns + "=>" + resourceLocations);
    }
}

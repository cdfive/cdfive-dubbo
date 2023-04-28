package com.cdfive.framework.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * @author cdfive
 */
@Data
@RefreshScope
@Configuration
public class AppProperties {

    @Value("${spring.application.name}")
    private String appName;

    @Value("${server.port}")
    private Integer appPort;
}

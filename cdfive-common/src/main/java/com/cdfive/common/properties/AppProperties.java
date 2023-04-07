package com.cdfive.common.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;

/**
 * @author xiejihan
 * @date 2023-03-31
 */
@Data
@Configuration
public class AppProperties implements Serializable {

    private static final long serialVersionUID = 8931508322598392458L;

    @Value("${spring.application.name}")
    private String appName;

    @Value("${server.port}")
    private Integer serverPort;
}

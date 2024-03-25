package com.cdfive.framework.component.jwt;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author cdfive
 */
@Data
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    private String secret;

    private Long expireTimeMs;
}

package com.cdfive.ctf.redis.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author cdfive
 */
@ConfigurationProperties(prefix = "redis")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RedisProperties {

    private String host;

    private Integer port;

    private Integer timeoutMs;

}

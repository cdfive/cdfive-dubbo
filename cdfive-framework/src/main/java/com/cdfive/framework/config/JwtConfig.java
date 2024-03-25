package com.cdfive.framework.config;

import com.cdfive.framework.component.jwt.JwtComponent;
import com.cdfive.framework.component.jwt.JwtProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;

/**
 * @author cdfive
 */
@EnableConfigurationProperties(JwtProperties.class)
@Configuration
public class JwtConfig {

    @Autowired
    private JwtProperties jwtProperties;

    @ConditionalOnProperty(value = "jwt.enable", havingValue = "true", matchIfMissing = false)
    @Bean
    public JwtComponent jwtComponent() {
        String secret = jwtProperties.getSecret();
        Assert.hasText(secret, "jwt.secret can't be empty");

        Long expireTimeMs = jwtProperties.getExpireTimeMs();
        Assert.notNull(secret, "jwt.expireTimeMs can't be empty");

        JwtComponent component = new JwtComponent(secret, expireTimeMs);
        return component;
    }
}

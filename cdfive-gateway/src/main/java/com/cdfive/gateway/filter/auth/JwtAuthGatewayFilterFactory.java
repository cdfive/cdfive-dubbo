package com.cdfive.gateway.filter.auth;

import com.cdfive.framework.component.jwt.JwtComponent;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * @author cdfive
 */
@Component
public class JwtAuthGatewayFilterFactory extends AbstractGatewayFilterFactory<JwtAuthGatewayFilterFactory.Config> {

    @Autowired
    private JwtComponent jwtComponent;

    public JwtAuthGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return new JwtAuthGatewayFilter(jwtComponent, config.whiteList);
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("whiteList", "jwtSecret", "accessTokenExpire");
    }

    @Getter
    @Setter
    public static class Config {
        private List<String> whiteList;
    }
}

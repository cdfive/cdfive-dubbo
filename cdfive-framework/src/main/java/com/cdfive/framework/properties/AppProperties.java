package com.cdfive.framework.properties;

import com.cdfive.framework.util.HostNameUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

/**
 * @author cdfive
 */
@Slf4j
@Data
@RefreshScope
@Configuration
public class AppProperties implements InitializingBean {

    @Value("${spring.application.name}")
    private String appName;

    @Value("${server.ip:#{null}")
    private String appIp;

    @Value("${server.port}")
    private Integer appPort;

    @Override
    public void afterPropertiesSet() throws Exception {
        if (!StringUtils.hasText(appIp)) {
            appIp = HostNameUtil.getIp();
        }

        log.debug("appName={},appIp={},appPort={}", appName, appIp, appPort);
    }
}

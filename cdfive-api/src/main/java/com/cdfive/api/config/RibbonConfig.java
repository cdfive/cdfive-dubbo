package com.cdfive.api.config;

import com.netflix.loadbalancer.ServerListUpdater;
import com.netflix.niws.loadbalancer.EurekaNotificationServerListUpdater;
import org.springframework.cloud.netflix.ribbon.eureka.EurekaRibbonClientConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author cdfive
 */
@Configuration
public class RibbonConfig {

    /**
     * use {@link com.netflix.niws.loadbalancer.EurekaNotificationServerListUpdater}
     * instead of {@link com.netflix.loadbalancer.PollingServerListUpdater}
     *
     * @see {@link org.springframework.cloud.netflix.ribbon.eureka.EurekaRibbonClientConfiguration}
     */
    @Bean
    public ServerListUpdater ribbonServerListUpdater() {
        return new EurekaNotificationServerListUpdater();
    }
}
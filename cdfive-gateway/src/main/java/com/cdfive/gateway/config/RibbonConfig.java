//package com.cdfive.gateway.config;
//
//import com.netflix.loadbalancer.ServerListUpdater;
//import com.netflix.niws.loadbalancer.EurekaNotificationServerListUpdater;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * @author cdfive
// */
//@Configuration
//public class RibbonConfig {
//
//    /**
//     * use {@link com.netflix.niws.loadbalancer.EurekaNotificationServerListUpdater}
//     * instead of {@link com.netflix.loadbalancer.PollingServerListUpdater}
//     */
//    @Bean
//    public ServerListUpdater ribbonServerListUpdater() {
//        return new EurekaNotificationServerListUpdater();
//    }
//}

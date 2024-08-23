package com.cdfive.gateway.filter;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.gateway.config.LoadBalancerProperties;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.LoadBalancerClientFilter;
import org.springframework.cloud.gateway.support.DelegatingServiceInstance;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.*;

/**
 * @author cdfive
 */
@Slf4j
@Setter
@Profile(value = {"test"})
@Component
public class LatestUpdateTimeLoadBalancerClientFilter implements GlobalFilter, Ordered {

    private static final Log log = LogFactory.getLog(LatestUpdateTimeLoadBalancerClientFilter.class);

    protected LoadBalancerClient loadBalancer;

    private LoadBalancerProperties properties;

    private EurekaClient eurekaClient;

    public LatestUpdateTimeLoadBalancerClientFilter(LoadBalancerClient loadBalancer, LoadBalancerProperties properties, EurekaClient eurekaClient) {
        this.loadBalancer = loadBalancer;
        this.properties = properties;
        this.eurekaClient = eurekaClient;
    }

    @Override
    public int getOrder() {
        return LoadBalancerClientFilter.LOAD_BALANCER_CLIENT_FILTER_ORDER - 1;
    }

    @Override
    @SuppressWarnings("Duplicates")
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        URI url = exchange.getAttribute(GATEWAY_REQUEST_URL_ATTR);
        String schemePrefix = exchange.getAttribute(GATEWAY_SCHEME_PREFIX_ATTR);
        if (url == null
                || (!"lb".equals(url.getScheme()) && !"lb".equals(schemePrefix))) {
            return chain.filter(exchange);
        }
        // preserve the original url
        addOriginalRequestUrl(exchange, url);

        if (log.isTraceEnabled()) {
            log.trace("LoadBalancerClientFilter url before: " + url);
        }

        final ServiceInstance instance = choose(exchange);

        if (instance == null) {
            throw NotFoundException.create(properties.isUse404(),
                    "Unable to find instance for " + url.getHost());
        }

        URI uri = exchange.getRequest().getURI();

        // if the `lb:<scheme>` mechanism was used, use `<scheme>` as the default,
        // if the loadbalancer doesn't provide one.
        String overrideScheme = instance.isSecure() ? "https" : "http";
        if (schemePrefix != null) {
            overrideScheme = url.getScheme();
        }

        URI requestUrl = loadBalancer.reconstructURI(
                new DelegatingServiceInstance(instance, overrideScheme), uri);

        if (log.isTraceEnabled()) {
            log.trace("LoadBalancerClientFilter url chosen: " + requestUrl);
        }

        exchange.getAttributes().put(GATEWAY_REQUEST_URL_ATTR, requestUrl);
        return chain.filter(exchange);
    }

    protected ServiceInstance choose(ServerWebExchange exchange) {
        URI url = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR);
        String serviceId = url.getHost();

        List<InstanceInfo> instanceInfos = eurekaClient.getInstancesByVipAddress(serviceId, false);
        log.debug("debugStart=>" + instanceInfos.size());
        InstanceInfo info = null;
        Long time = null;
        for (InstanceInfo instanceInfo : instanceInfos) {
            log.debug("debug=>" + instanceInfo.getInstanceId() + "=>" + instanceInfo.getStatus());
            if (InstanceInfo.InstanceStatus.UP.equals(instanceInfo.getStatus()) && (time == null || instanceInfo.getLastUpdatedTimestamp() > time)) {
                time = instanceInfo.getLastUpdatedTimestamp();
                info = instanceInfo;
            }
        }

        if (info != null) {
            log.debug("debugEnd=>" + info.getInstanceId() + "," + info.getStatus());
            return new EurekaDiscoveryClient.EurekaServiceInstance(info);
        }

        return null;
    }
}

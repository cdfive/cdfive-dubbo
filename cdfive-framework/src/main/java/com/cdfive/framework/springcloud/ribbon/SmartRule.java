package com.cdfive.framework.springcloud.ribbon;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author cdfive
 */
public class SmartRule extends AbstractLoadBalancerRule {

    private static final Logger log = LoggerFactory.getLogger(SmartRule.class);

    private AtomicInteger nextServerCyclicCounter;

    public SmartRule() {
        nextServerCyclicCounter = new AtomicInteger(0);
    }

    public SmartRule(ILoadBalancer lb) {
        this();
        setLoadBalancer(lb);
    }

    @Override
    public void initWithNiwsConfig(IClientConfig clientConfig) {

    }

    @Override
    public Server choose(Object key) {
        ILoadBalancer lb = getLoadBalancer();
        if (lb == null) {
            log.error("no load balancer");
            return null;
        }

        List<Server> reachableServers = lb.getReachableServers();
        if (reachableServers == null || reachableServers.size() == 0) {
            log.error("no reachable server");
            return null;
        }

        int count = 0;
        int serverCount = reachableServers.size();
        boolean failed = false;
        while (true) {
            count++;

            int nextServerIndex = incrementAndGetModulo(reachableServers.size());
            Server server = reachableServers.get(nextServerIndex);

            if (server == null) {
                log.error("empty server");
                return null;
            }

            if (!server.isAlive()) {
                failed = true;
                log.error("server is not alive,server={}", server);
            } else {
                BaseLoadBalancer baseLoadBalancer = (BaseLoadBalancer) lb;
                IPing ping = baseLoadBalancer.getPing();
                if (ping.isAlive(server)) {
                    if (!failed) {
                        log.debug("choose server={}", server);
                    } else {
                        log.error("choose server={}", server);
                    }
                    return server;
                }

                failed = true;
                log.error("server is not really alive,server={}", server);
                lb.markServerDown(server);
            }

            if (count >= serverCount) {
                log.error("exceed serverCount,count={},serverCount={}", count, serverCount);
                return null;
            }
        }
    }

    private int incrementAndGetModulo(int modulo) {
        while (true) {
            int current = nextServerCyclicCounter.get();
            int next = (current + 1) % modulo;
            if (nextServerCyclicCounter.compareAndSet(current, next)) {
                return next;
            }
        }
    }
}

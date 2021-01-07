package com.cdfive.mp3.config;

import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.datasource.redis.RedisDataSource;
import com.alibaba.csp.sentinel.datasource.redis.config.RedisConnectionConfig;
import com.alibaba.csp.sentinel.init.InitExecutor;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRule;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRuleManager;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRuleManager;
import com.alibaba.csp.sentinel.slots.system.SystemRule;
import com.alibaba.csp.sentinel.slots.system.SystemRuleManager;
import com.alibaba.csp.sentinel.transport.config.TransportConfig;
import com.alibaba.csp.sentinel.util.AppNameUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.common.base.Joiner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author cdfive
 */
@Slf4j
@Configuration
public class SentinelConfig {

    @Value("${redis.host}")
    private String redisHost;

    @Value("${redis.port}")
    private Integer redisPort;

    @PostConstruct
    public void init() {
        RedisConnectionConfig config = RedisConnectionConfig.builder()
                .withHost(redisHost)
                .withPort(redisPort)
                .build();

        String app = AppNameUtil.getAppName();
        log.info("app={}", app);
        String ip = TransportConfig.getHeartbeatClientIp();
        log.info("ip={}", ip);
        String port = TransportConfig.getPort();
        log.info("port={}", port);
        String appId = Joiner.on("-").join(app, ip, port);
        log.info("appId={}", appId);

        String flowRuleKey = Joiner.on("-").join(appId, "flow", "rules");
        ReadableDataSource<String, List<FlowRule>> flowRuleRedisDataSource = new RedisDataSource<List<FlowRule>>(config,
                flowRuleKey, flowRuleKey, o -> JSON.parseObject(o, new TypeReference<List<FlowRule>>() {}));
        FlowRuleManager.register2Property(flowRuleRedisDataSource.getProperty());

        String degradeRuleKey = Joiner.on("-").join(appId, "degrade", "rules");
        ReadableDataSource<String, List<DegradeRule>> degradeRuleRedisDataSource = new RedisDataSource<List<DegradeRule>>(config,
                degradeRuleKey, degradeRuleKey, o -> JSON.parseObject(o, new TypeReference<List<DegradeRule>>() {}));
        DegradeRuleManager.register2Property(degradeRuleRedisDataSource.getProperty());

        String systemRuleKey = Joiner.on("-").join(appId, "system", "rules");
        ReadableDataSource<String, List<SystemRule>> systemRuleRedisDataSource = new RedisDataSource<List<SystemRule>>(config,
                systemRuleKey, systemRuleKey, o -> JSON.parseObject(o, new TypeReference<List<SystemRule>>() {}));
        SystemRuleManager.register2Property(systemRuleRedisDataSource.getProperty());

        String authorityRuleKey = Joiner.on("-").join(appId, "authority", "rules");
        ReadableDataSource<String, List<AuthorityRule>> authorityRuleRedisDataSource = new RedisDataSource<List<AuthorityRule>>(config,
                authorityRuleKey, authorityRuleKey, o -> JSON.parseObject(o, new TypeReference<List<AuthorityRule>>() {}));
        AuthorityRuleManager.register2Property(authorityRuleRedisDataSource.getProperty());

        String paramFlowRuleKey = Joiner.on("-").join(appId, "paramFlow", "rules");
        ReadableDataSource<String, List<ParamFlowRule>> paramFlowRuleRedisDataSource = new RedisDataSource<List<ParamFlowRule>>(config,
                paramFlowRuleKey, paramFlowRuleKey, o -> JSON.parseObject(o, new TypeReference<List<ParamFlowRule>>() {}));
        ParamFlowRuleManager.register2Property(paramFlowRuleRedisDataSource.getProperty());

        InitExecutor.doInit();
    }
}

package com.cdfive.sentinel.datasource;

import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.csp.sentinel.datasource.redis.RedisDataSource;
import com.alibaba.csp.sentinel.datasource.redis.config.RedisConnectionConfig;
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
import com.alibaba.csp.sentinel.util.StringUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.cdfive.sentinel.config.SentinelProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.List;

import static com.cdfive.sentinel.constant.SentinelConstant.LOG_PRIFEX;

/**
 * @author cdfive
 */
@Slf4j
@Component
public class RedisDataSourceBuilder extends AbstractDataSourceBuilder {

    @Override
    public DataSourceType dataSourceType() {
        return DataSourceType.REDIS;
    }

    @Override
    public void buildDataSource(SentinelProperties sentinelProperties, String appName, String ip, Integer port) {
        SentinelProperties.RedisProperties redisProperties = sentinelProperties.getDataSource().getRedis();
        Assert.notNull(redisProperties, "empty RedisProperties");

        String redisHost = redisProperties.getHost();
        Assert.hasText(redisHost, "redisHost empty");

        String redisPort = redisProperties.getPort();
        Assert.hasText(redisPort, "redisPort empty");

        String redisPassword = redisProperties.getPassword();
        log.info("{}SentinelConfiguration init redis datasource,host={},port={},password={}", LOG_PRIFEX, redisHost, redisPort, redisPassword);

        RedisConnectionConfig.Builder redisConfigBuilder = RedisConnectionConfig.builder();
        redisConfigBuilder.withHost(redisHost.replace("@", "")).withPort(Integer.parseInt(redisPort.replace("@", "")));
        if (StringUtil.isNotBlank(redisPassword)) {
            redisConfigBuilder.withPassword(redisPassword);
        }
        RedisConnectionConfig redisConfig = redisConfigBuilder.build();

        // Init RedisDataSource for flow rules
        String flowRuleKey = buildRuleKey(appName, ip, port, "flow");
        RedisDataSource<List<FlowRule>> flowRuleRedisDataSource = new RedisDataSource<>(redisConfig, flowRuleKey, flowRuleKey, new Converter<String, List<FlowRule>>() {
            @Override
            public List<FlowRule> convert(String source) {
                return JSON.parseObject(source, new TypeReference<List<FlowRule>>() {
                });
            }
        });
        FlowRuleManager.register2Property(flowRuleRedisDataSource.getProperty());

        // Init RedisDataSource for degrade rules
        String degradeRuleKey = buildRuleKey(appName, ip, port, "degrade");
        RedisDataSource<List<DegradeRule>> degradeRuleRedisDataSource = new RedisDataSource<>(redisConfig, degradeRuleKey, degradeRuleKey, new Converter<String, List<DegradeRule>>() {
            @Override
            public List<DegradeRule> convert(String source) {
                return JSON.parseObject(source, new TypeReference<List<DegradeRule>>() {
                });
            }
        });
        DegradeRuleManager.register2Property(degradeRuleRedisDataSource.getProperty());

        // Init RedisDataSource for system rules
        String systemRuleKey = buildRuleKey(appName, ip, port, "system");
        RedisDataSource<List<SystemRule>> systemRuleRedisDataSource = new RedisDataSource<>(redisConfig, systemRuleKey, systemRuleKey, new Converter<String, List<SystemRule>>() {
            @Override
            public List<SystemRule> convert(String source) {
                return JSON.parseObject(source, new TypeReference<List<SystemRule>>() {
                });
            }
        });
        SystemRuleManager.register2Property(systemRuleRedisDataSource.getProperty());

        // Init RedisDataSource for authority rules
        String authorityRuleKey = buildRuleKey(appName, ip, port, "authority");
        RedisDataSource<List<AuthorityRule>> authorityRuleRedisDataSource = new RedisDataSource<>(redisConfig, authorityRuleKey, authorityRuleKey, new Converter<String, List<AuthorityRule>>() {
            @Override
            public List<AuthorityRule> convert(String source) {
                return JSON.parseObject(source, new TypeReference<List<AuthorityRule>>() {
                });
            }
        });
        AuthorityRuleManager.register2Property(authorityRuleRedisDataSource.getProperty());

        // Init RedisDataSource for paramFlow rules
        String paramFlowRuleKey = buildRuleKey(appName, ip, port, "paramFlow");
        RedisDataSource<List<ParamFlowRule>> paramFlowRuleRedisDataSource = new RedisDataSource<>(redisConfig, paramFlowRuleKey, paramFlowRuleKey, new Converter<String, List<ParamFlowRule>>() {
            @Override
            public List<ParamFlowRule> convert(String source) {
                return JSON.parseObject(source, new TypeReference<List<ParamFlowRule>>() {
                });
            }
        });
        ParamFlowRuleManager.register2Property(paramFlowRuleRedisDataSource.getProperty());

    }
}

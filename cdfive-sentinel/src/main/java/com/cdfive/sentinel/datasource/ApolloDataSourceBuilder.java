package com.cdfive.sentinel.datasource;

import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.csp.sentinel.datasource.apollo.ApolloDataSource;
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
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.cdfive.sentinel.config.SentinelProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author cdfive
 */
@Slf4j
@Component
public class ApolloDataSourceBuilder extends AbstractDataSourceBuilder {

    @Override
    public DataSourceType dataSourceType() {
        return DataSourceType.APOLLO;
    }

    @Override
    public void buildDataSource(SentinelProperties sentinelProperties, String appName, String ip, Integer port) {
        SentinelProperties.ApolloDataSourceProperties apolloDataSourceProperties = sentinelProperties.getDataSource().getApolloDataSourceProperties();
        Assert.notNull(apolloDataSourceProperties, "apolloDataSourceProperties can't be empty");

        String serverAddress = apolloDataSourceProperties.getServerAddress();
        Assert.hasText(serverAddress, "apollo serverAddress can't be empty");

        String namespaceName = apolloDataSourceProperties.getNamespaceName();
        Assert.hasText(namespaceName, "apollo namespaceName can't be empty");

        System.setProperty("apollo.meta", serverAddress);
        String defaultRules = "[]";

        // Init ApolloDataSource for flow rules
        String flowRuleKey = buildRuleKey(appName, ip, port, "flow");
        ApolloDataSource<List<FlowRule>> flowRuleDataSource = new ApolloDataSource<>(namespaceName,
                flowRuleKey, defaultRules, new Converter<String, List<FlowRule>>() {
            @Override
            public List<FlowRule> convert(String source) {
                return JSON.parseObject(source, new TypeReference<List<FlowRule>>() {
                });
            }
        });
        FlowRuleManager.register2Property(flowRuleDataSource.getProperty());

        // Init ApolloDataSource for degrade rules
        String degradeRuleKey = buildRuleKey(appName, ip, port, "degrade");
        ApolloDataSource<List<DegradeRule>> degradeRuleDataSource = new ApolloDataSource<>(namespaceName,
                degradeRuleKey, defaultRules, new Converter<String, List<DegradeRule>>() {
            @Override
            public List<DegradeRule> convert(String source) {
                return JSON.parseObject(source, new TypeReference<List<DegradeRule>>() {
                });
            }
        });
        DegradeRuleManager.register2Property(degradeRuleDataSource.getProperty());

        // Init ApolloDataSource for system rules
        String systemRuleKey = buildRuleKey(appName, ip, port, "system");
        ApolloDataSource<List<SystemRule>> systemRuleDataSource = new ApolloDataSource<>(namespaceName,
                systemRuleKey, defaultRules, new Converter<String, List<SystemRule>>() {
            @Override
            public List<SystemRule> convert(String source) {
                return JSON.parseObject(source, new TypeReference<List<SystemRule>>() {
                });
            }
        });
        SystemRuleManager.register2Property(systemRuleDataSource.getProperty());

        // Init ApolloDataSource for authority rules
        String authorityRuleKey = buildRuleKey(appName, ip, port, "authority");
        ApolloDataSource<List<AuthorityRule>> authorityRuleDataSource = new ApolloDataSource<>(namespaceName,
                authorityRuleKey, defaultRules, new Converter<String, List<AuthorityRule>>() {
            @Override
            public List<AuthorityRule> convert(String source) {
                return JSON.parseObject(source, new TypeReference<List<AuthorityRule>>() {
                });
            }
        });
        AuthorityRuleManager.register2Property(authorityRuleDataSource.getProperty());

        // Init ApolloDataSource for paramFlow rules
        String paramFlowRuleKey = buildRuleKey(appName, ip, port, "paramFlow");
        ApolloDataSource<List<ParamFlowRule>> paramFlowRuleDataSource = new ApolloDataSource<>(namespaceName,
                paramFlowRuleKey, defaultRules, new Converter<String, List<ParamFlowRule>>() {
            @Override
            public List<ParamFlowRule> convert(String source) {
                return JSON.parseObject(source, new TypeReference<List<ParamFlowRule>>() {
                });
            }
        });
        ParamFlowRuleManager.register2Property(paramFlowRuleDataSource.getProperty());
    }
}

package com.cdfive.sentinel.datasource;

import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.csp.sentinel.datasource.zookeeper.ZookeeperDataSource;
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
public class ZookeeperDataSourceBuilder extends AbstractDataSourceBuilder {

    @Override
    public DataSourceType dataSourceType() {
        return DataSourceType.ZOOKEEPER;
    }

    @Override
    public void buildDataSource(SentinelProperties sentinelProperties, String appName, String ip, Integer port) {
        SentinelProperties.ZookeeperDataSourceProperties zookeeperDataSourceProperties = sentinelProperties.getDataSource().getZookeeperDataSourceProperties();
        Assert.notNull(zookeeperDataSourceProperties, "zookeeperDataSourceProperties can't be empty");

        String serverAddr = zookeeperDataSourceProperties.getServerAddr();
        Assert.hasText(serverAddr, "zookeeper serverAddr can't be empty");

        // Init ZookeeperDataSource for flow rules
        String flowRuleKey = buildRuleKey(appName, ip, port, "flow");
        ZookeeperDataSource<List<FlowRule>> flowRuleZookeeperDataSource = new ZookeeperDataSource<>(serverAddr, flowRuleKey, new Converter<String, List<FlowRule>>() {
            @Override
            public List<FlowRule> convert(String source) {
                return JSON.parseObject(source, new TypeReference<List<FlowRule>>() {
                });
            }
        });
        FlowRuleManager.register2Property(flowRuleZookeeperDataSource.getProperty());

        // Init ZookeeperDataSource for degrade rules
        String degradeRuleKey = buildRuleKey(appName, ip, port, "degrade");
        ZookeeperDataSource<List<DegradeRule>> degradeRuleZookeeperDataSource = new ZookeeperDataSource<>(serverAddr, degradeRuleKey, new Converter<String, List<DegradeRule>>() {
            @Override
            public List<DegradeRule> convert(String source) {
                return JSON.parseObject(source, new TypeReference<List<DegradeRule>>() {
                });
            }
        });
        DegradeRuleManager.register2Property(degradeRuleZookeeperDataSource.getProperty());

        // Init ZookeeperDataSource for system rules
        String systemRuleKey = buildRuleKey(appName, ip, port, "system");
        ZookeeperDataSource<List<SystemRule>> systemRuleZookeeperDataSource = new ZookeeperDataSource<>(serverAddr, systemRuleKey, new Converter<String, List<SystemRule>>() {
            @Override
            public List<SystemRule> convert(String source) {
                return JSON.parseObject(source, new TypeReference<List<SystemRule>>() {
                });
            }
        });
        SystemRuleManager.register2Property(systemRuleZookeeperDataSource.getProperty());

        // Init ZookeeperDataSource for authority rules
        String authorityRuleKey = buildRuleKey(appName, ip, port, "authority");
        ZookeeperDataSource<List<AuthorityRule>> authorityRuleZookeeperDataSource = new ZookeeperDataSource<>(serverAddr, authorityRuleKey, new Converter<String, List<AuthorityRule>>() {
            @Override
            public List<AuthorityRule> convert(String source) {
                return JSON.parseObject(source, new TypeReference<List<AuthorityRule>>() {
                });
            }
        });
        AuthorityRuleManager.register2Property(authorityRuleZookeeperDataSource.getProperty());

        // Init ZookeeperDataSource for paramFlow rules
        String paramFlowRuleKey = buildRuleKey(appName, ip, port, "paramFlow");
        ZookeeperDataSource<List<ParamFlowRule>> paramFlowRuleZookeeperDataSource = new ZookeeperDataSource<>(serverAddr, authorityRuleKey, new Converter<String, List<ParamFlowRule>>() {
            @Override
            public List<ParamFlowRule> convert(String source) {
                return JSON.parseObject(source, new TypeReference<List<ParamFlowRule>>() {
                });
            }
        });
        ParamFlowRuleManager.register2Property(paramFlowRuleZookeeperDataSource.getProperty());
    }
}

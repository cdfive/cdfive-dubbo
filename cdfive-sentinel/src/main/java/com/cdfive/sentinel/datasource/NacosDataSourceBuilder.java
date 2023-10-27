package com.cdfive.sentinel.datasource;

import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.csp.sentinel.datasource.nacos.NacosDataSource;
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
import com.alibaba.nacos.api.PropertyKeyConst;
import com.cdfive.sentinel.config.SentinelProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Properties;

/**
 * @author cdfive
 */
@Slf4j
@Component
public class NacosDataSourceBuilder extends AbstractDataSourceBuilder {

    @Override
    public DataSourceType dataSourceType() {
        return DataSourceType.NACOS;
    }

    @Override
    public void buildDataSource(SentinelProperties sentinelProperties, String appName, String ip, Integer port) {
        SentinelProperties.NacosDataSourceProperties nacosDataSourceProperties = sentinelProperties.getDataSource().getNacosDataSourceProperties();
        Assert.notNull(nacosDataSourceProperties, "nacosDataSourceProperties can't be empty");

        String serverAddr = nacosDataSourceProperties.getServerAddr();
        Assert.hasText(serverAddr, "nacos serverAddr can't be empty");
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.SERVER_ADDR, serverAddr);

        String namespace = nacosDataSourceProperties.getNamespace();
        if (StringUtils.hasText(namespace)) {
            properties.put(PropertyKeyConst.NAMESPACE, namespace);
        }

        String groupId = nacosDataSourceProperties.getGroupId();

        String flowRuleKey = buildRuleKey(appName, ip, port, "flow");
        NacosDataSource<List<FlowRule>> flowRuleNacosDataSource = new NacosDataSource<>(properties, groupId, flowRuleKey, new Converter<String, List<FlowRule>>() {
            @Override
            public List<FlowRule> convert(String source) {
                return JSON.parseObject(source, new TypeReference<List<FlowRule>>() {
                });
            }
        });
        FlowRuleManager.register2Property(flowRuleNacosDataSource.getProperty());

        String degradeRuleKey = buildRuleKey(appName, ip, port, "degrade");
        NacosDataSource<List<DegradeRule>> degradeRuleNacosDataSource = new NacosDataSource<>(properties, groupId, degradeRuleKey, new Converter<String, List<DegradeRule>>() {
            @Override
            public List<DegradeRule> convert(String source) {
                return JSON.parseObject(source, new TypeReference<List<DegradeRule>>() {
                });
            }
        });
        DegradeRuleManager.register2Property(degradeRuleNacosDataSource.getProperty());

        String systemRuleKey = buildRuleKey(appName, ip, port, "system");
        NacosDataSource<List<SystemRule>> systemRuleNacosDataSource = new NacosDataSource<>(properties, groupId, systemRuleKey, new Converter<String, List<SystemRule>>() {
            @Override
            public List<SystemRule> convert(String source) {
                return JSON.parseObject(source, new TypeReference<List<SystemRule>>() {
                });
            }
        });
        SystemRuleManager.register2Property(systemRuleNacosDataSource.getProperty());

        String authorityRuleKey = buildRuleKey(appName, ip, port, "authority");
        NacosDataSource<List<AuthorityRule>> authorityRuleNacosDataSource = new NacosDataSource<>(properties, groupId, authorityRuleKey, new Converter<String, List<AuthorityRule>>() {
            @Override
            public List<AuthorityRule> convert(String source) {
                return JSON.parseObject(source, new TypeReference<List<AuthorityRule>>() {
                });
            }
        });
        AuthorityRuleManager.register2Property(authorityRuleNacosDataSource.getProperty());

        String paramFlowRuleKey = buildRuleKey(appName, ip, port, "paramFlow");
        NacosDataSource<List<ParamFlowRule>> paramFlowRuleNacosDataSource = new NacosDataSource<>(properties, groupId, paramFlowRuleKey, new Converter<String, List<ParamFlowRule>>() {
            @Override
            public List<ParamFlowRule> convert(String source) {
                return JSON.parseObject(source, new TypeReference<List<ParamFlowRule>>() {
                });
            }
        });
        ParamFlowRuleManager.register2Property(paramFlowRuleNacosDataSource.getProperty());
    }
}

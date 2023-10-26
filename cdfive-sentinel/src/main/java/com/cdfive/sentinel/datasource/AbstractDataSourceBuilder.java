package com.cdfive.sentinel.datasource;

/**
 * @author cdfive
 */
public abstract class AbstractDataSourceBuilder implements DataSourceBuilder {

    protected String buildRuleKey(String appName, String ip, Integer port, String ruleType) {
        return String.join("-", appName, ip, String.valueOf(port), ruleType, "rules");
    }
}

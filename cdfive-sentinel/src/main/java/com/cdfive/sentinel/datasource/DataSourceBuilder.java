package com.cdfive.sentinel.datasource;

import com.cdfive.sentinel.config.SentinelProperties;

/**
 * @author cdfive
 */
public interface DataSourceBuilder {

    DataSourceType dataSourceType();

    void buildDataSource(SentinelProperties sentinelProperties, String appName, String ip, Integer port);
}

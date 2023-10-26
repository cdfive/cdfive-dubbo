package com.cdfive.sentinel.datasource;

import lombok.Getter;

/**
 * @author cdfive
 */
@Getter
public enum DataSourceType {

    REDIS,

    NACOS,

    APOLLO,

    ZOOKEEPER;

    public static DataSourceType of(String name) {
        DataSourceType[] values = DataSourceType.values();
        for (DataSourceType value : values) {
            if (value.name().equalsIgnoreCase(name)) {
                return value;
            }
        }

        return null;
    }
}

package com.cdfive.sentinel.config;

import com.alibaba.csp.sentinel.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author cdfive
 */
@Configuration
@ConfigurationProperties(prefix = "sentinel")
@PropertySource(value = "classpath:sentinel.properties", ignoreResourceNotFound = true)
public class SentinelProperties {

    private static Logger log = LoggerFactory.getLogger(SentinelProperties.class);

    @Value("${sentinel.enable:false}")
    private boolean enable;

    @Value("${sentinel.appName:#{null}}")
    private String appName;

    @Value("${sentinel.transport.dashboard:#{null}}")
    private String transportDashboard;

    @Value("${sentinel.transport.port:#{null}}")
    private Integer transportPort;

    @Value("${sentinel.datasource.type:#{null}}")
    private String datasourceType;

    @Value("${redis.host:#{null}}")
    private String redisHost;

    @Value("${redis.port:#{null}}")
    private String redisPort;

    @Value("${redis.password:#{null}}")
    private String redisPassword;

    private Transport transport = new Transport();

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getTransportDashboard() {
        if (StringUtil.isNotBlank(transportDashboard)) {
            return transportDashboard;
        }

        return transport.getDashboard();
    }

    public void setTransportDashboard(String transportDashboard) {
        this.transportDashboard = transportDashboard;
    }

    public Integer getTransportPort() {
        if (transportPort != null) {
            return transportPort;
        }

        return transport.getPort();
    }

    public void setTransportPort(Integer transportPort) {
        this.transportPort = transportPort;
    }

    public String getDatasourceType() {
        return datasourceType;
    }

    public void setDatasourceType(String datasourceType) {
        this.datasourceType = datasourceType;
    }

    public String getRedisHost() {
        return redisHost;
    }

    public void setRedisHost(String redisHost) {
        this.redisHost = redisHost;
    }

    public String getRedisPort() {
        return redisPort;
    }

    public void setRedisPort(String redisPort) {
        this.redisPort = redisPort;
    }

    public String getRedisPassword() {
        return redisPassword;
    }

    public void setRedisPassword(String redisPassword) {
        this.redisPassword = redisPassword;
    }

    public Transport getTransport() {
        return transport;
    }

    public void setTransport(Transport transport) {
        this.transport = transport;
    }

    public static class Transport {
        private String dashboard;

        private Integer port;

        public String getDashboard() {
            return dashboard;
        }

        public void setDashboard(String dashboard) {
            this.dashboard = dashboard;
        }

        public Integer getPort() {
            return port;
        }

        public void setPort(Integer port) {
            this.port = port;
        }
    }
}

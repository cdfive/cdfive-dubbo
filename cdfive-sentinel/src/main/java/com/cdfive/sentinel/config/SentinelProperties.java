package com.cdfive.sentinel.config;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author cdfive
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "sentinel")
@PropertySource(value = "classpath:sentinel.properties", ignoreResourceNotFound = true)
public class SentinelProperties {

    private static Logger log = LoggerFactory.getLogger(SentinelProperties.class);

    @Value("${sentinel.enable:false}")
    private boolean enable;

    @Value("${sentinel.appName:#{null}}")
    private String appName;

    @Value("${sentinel.enableSpringWebMvc:true}")
    private boolean enableSpringWebMvc;

    @Value("${sentinel.enableServlet:false}")
    private boolean enableServlet;

//    @Value("${sentinel.transport.dashboard:#{null}}")
//    private String transportDashboard;
//
//    @Value("${sentinel.transport.port:#{null}}")
//    private Integer transportPort;

//    @Value("${sentinel.datasource.type:#{null}}")
//    private String dataSourceType;

//    @Value("${redis.host:#{null}}")
//    private String redisHost;
//
//    @Value("${redis.port:#{null}}")
//    private String redisPort;
//
//    @Value("${redis.password:#{null}}")
//    private String redisPassword;

    private Transport transport;

    private DataSource dataSource;

//    private RedisProperties redis;

    @Data
    public static class Transport {

        private String dashboard;

        private Integer port;
    }

    @Data
    public static class DataSource {

        private String type;

        private RedisDataSourceProperties redis;

        private NacosDataSourceProperties nacos;

        private ZookeeperDataSourceProperties zookeeper;

        private ApolloDataSourceProperties apollo;
    }

    @Data
    public static class RedisDataSourceProperties {

        private String host;

        private String port;

        private String password;

        private String channelSuffix;
    }

    @Data
    public static class NacosDataSourceProperties {

        private static final String DEFAULT_GROUP_ID = "SENTINEL_GROUP";

        private String serverAddr;

        private String namespace;

        private String groupId;

        public String getGroupId() {
            return groupId != null && groupId.length() > 0 ? groupId : DEFAULT_GROUP_ID;
        }
    }

    @Data
    public static class ZookeeperDataSourceProperties {

        private String serverAddr;
    }

    @Data
    public static class ApolloDataSourceProperties {

        private String serverAddress;

        private String namespaceName;
    }
}

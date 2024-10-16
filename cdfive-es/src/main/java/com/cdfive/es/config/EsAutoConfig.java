package com.cdfive.es.config;

import com.cdfive.es.constant.EsConstant;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

/**
 * @author cdfive
 */
@Configuration
@ConditionalOnProperty({"spring.data.elasticsearch.clusterNodes"})
@ConditionalOnClass(RestClientBuilder.class)
@EnableConfigurationProperties(EsProperties.class)
public class EsAutoConfig {

    private final Logger logger = LoggerFactory.getLogger(EsAutoConfig.class);

    @Autowired
    private EsProperties elasticSearchProperties;

    @Bean(destroyMethod = "close")
    public RestHighLevelClient restHighLevelClient() {
        HttpHost[] hosts = null;

        String clusterNodes = elasticSearchProperties.getClusterNodes();
        if (StringUtils.isEmpty(clusterNodes)) {
            throw parseClusterNodeException(clusterNodes);
        }

        if (StringUtils.isEmpty(clusterNodes)) {
            hosts = new HttpHost[1];
            hosts[0] = new HttpHost(EsConstant.ES_HOST_DEFAULT, EsConstant.ES_PORT_DEFAULT);
        } else {
            String schema = HttpHost.DEFAULT_SCHEME_NAME;
            int index = clusterNodes.indexOf("://");
            if (index > -1) {
                schema = clusterNodes.substring(0, index);
                clusterNodes = clusterNodes.substring(index + 3);
            }

            String[] clusterNodeArray = clusterNodes.split(",");
            hosts = new HttpHost[clusterNodeArray.length];

            for (int i = 0; i < clusterNodeArray.length; i++) {
                String clusterNode = clusterNodeArray[i];
                if (!StringUtils.hasText(clusterNode)) {
                    throw parseClusterNodeException(clusterNode);
                }

                String[] hostAndPort = clusterNode.split(":");
                if (hostAndPort.length < 1 || hostAndPort.length > 2) {
                    throw parseClusterNodeException(clusterNode);
                }

                if (hostAndPort.length == 1) {
                    String hostname = hostAndPort[0];
                    if (!StringUtils.hasText(hostname)) {
                        throw parseClusterNodeException(clusterNode);
                    }
                    hosts[i] = new HttpHost(hostname, EsConstant.ES_PORT_DEFAULT, schema);
                    continue;
                }

                String hostname = hostAndPort[0];
                String port = hostAndPort[1];
                boolean portValid = true;
                try {
                    Integer.parseInt(port);
                } catch (NumberFormatException e) {
                    portValid = false;
                    logger.error("port invalid,port={},clusterNodes={}", port, clusterNodes, e);
                }
                if (!StringUtils.hasText(hostname) || !portValid) {
                    throw parseClusterNodeException(clusterNode);
                }

                hosts[i] = new HttpHost(hostname, Integer.valueOf(port), schema);
            }
        }

        RestClientBuilder clientBuilder = RestClient.builder(hosts);

        clientBuilder.setRequestConfigCallback(requestConfigBuilder -> {
            requestConfigBuilder.setConnectionRequestTimeout(elasticSearchProperties.getConnectionRequestTimout());
            requestConfigBuilder.setConnectTimeout(elasticSearchProperties.getConnectTimeout());
            requestConfigBuilder.setSocketTimeout(elasticSearchProperties.getSocketTimeout());
            return requestConfigBuilder;
        });

        String username = elasticSearchProperties.getUsername();
        String password = elasticSearchProperties.getPassword();
        CredentialsProvider credentialsProvider = null;
        if (StringUtils.hasText(username) && StringUtils.hasText(password)) {
            credentialsProvider = new BasicCredentialsProvider();
            credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password));
        }
        CredentialsProvider finalCredentialsProvider = credentialsProvider;
        clientBuilder.setHttpClientConfigCallback(httpAsyncClientBuilder -> {
            if (finalCredentialsProvider != null) {
                httpAsyncClientBuilder.setDefaultCredentialsProvider(finalCredentialsProvider);
            }
            return httpAsyncClientBuilder
                    .setMaxConnPerRoute(elasticSearchProperties.getMaxConnPerRoute())
                    .setMaxConnTotal(elasticSearchProperties.getMaxConnTotal());
        });

        RestHighLevelClient restHighLevelClient = new RestHighLevelClient(clientBuilder);

        logger.debug("EsAutoConfig RestHighLevelClient init success");
        return restHighLevelClient;
    }

    private IllegalArgumentException parseClusterNodeException(String clusterNode) {
        return new IllegalArgumentException(String.format("Illegal clusterNode:'%s', correct clusterNodes is like '192.168.1.1:9200'", clusterNode));
    }
}

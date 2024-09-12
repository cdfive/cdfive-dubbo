package com.cdfive.es.config;

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

    private Logger logger = LoggerFactory.getLogger(EsAutoConfig.class);

    @Autowired
    private EsProperties elasticSearchProperties;

    @Bean(destroyMethod = "close")
    public RestHighLevelClient restHighLevelClient() {
        HttpHost[] hosts = null;

        String clusterNodes = elasticSearchProperties.getClusterNodes();
        if (StringUtils.isEmpty(clusterNodes)) {
            hosts = new HttpHost[1];
            hosts[0] = new HttpHost("localhost", 9200);
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
                String[] hostAndPort = clusterNodeArray[i].split(":");
                if (hostAndPort.length < 1 || hostAndPort.length > 2) {
                    throw new IllegalArgumentException(String.format("Illegal clusterNode:'%s', correct clusterNodes is like '192.168.1.1:9200'", clusterNodeArray[i]));
                }

                if (hostAndPort.length == 1) {
                    String hostname = hostAndPort[0];
                    if (StringUtils.isEmpty(hostname)) {
                        throw new IllegalArgumentException(String.format("Illegal clusterNode:'%s', correct clusterNodes is like '192.168.1.1:9200'", clusterNodeArray[i]));
                    }
                    hosts[i] = new HttpHost(hostname, 9200, schema);
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
                if (StringUtils.isEmpty(hostname) || !portValid) {
                    throw new IllegalArgumentException(String.format("Illegal clusterNode:'%s', correct clusterNodes is like '192.168.1.1:9200'", clusterNodeArray[i]));
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
        if (!StringUtils.isEmpty(username) && !StringUtils.isEmpty(password)) {
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
        logger.info("EsAutoConfig RestHighLevelClient init success");
        return restHighLevelClient;
    }
}

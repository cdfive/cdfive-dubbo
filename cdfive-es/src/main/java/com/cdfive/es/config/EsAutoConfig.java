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
        String clusterNodes = elasticSearchProperties.getClusterNodes();
        if (StringUtils.isEmpty(clusterNodes)) {
            throw new IllegalArgumentException("spring.data.elasticsearch.clusterNodes can't be empty");
        }

        String schema = HttpHost.DEFAULT_SCHEME_NAME;
        int index = clusterNodes.indexOf("://");
        if (index > -1) {
            schema = clusterNodes.substring(0, index);
            clusterNodes = clusterNodes.substring(index + 3);
        }

        String[] clusterNodeArray = clusterNodes.split(",");
        HttpHost[] hosts = new HttpHost[clusterNodeArray.length];

        for (int i = 0; i < clusterNodeArray.length; ++i) {
            String[] hostAndPort = clusterNodeArray[i].split(":");
            if (hostAndPort.length != 2 || StringUtils.isEmpty(hostAndPort[0]) || StringUtils.isEmpty(hostAndPort[1])) {
                throw new IllegalArgumentException(String.format("Illegal clusterNode:'%s', correct clusterNodes is like '192.168.1.1:9200'", clusterNodeArray[i]));
            }

            hosts[i] = new HttpHost(hostAndPort[0], Integer.valueOf(hostAndPort[1]), schema);
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

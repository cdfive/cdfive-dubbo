//package com.cdfive.search.config;
//
//import org.apache.http.HttpHost;
//import org.apache.http.auth.AuthScope;
//import org.apache.http.auth.UsernamePasswordCredentials;
//import org.apache.http.client.CredentialsProvider;
//import org.apache.http.impl.client.BasicCredentialsProvider;
//import org.elasticsearch.client.RestClient;
//import org.elasticsearch.client.RestClientBuilder;
//import org.elasticsearch.client.RestHighLevelClient;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.elasticsearch.client.ClientConfiguration;
//import org.springframework.data.elasticsearch.client.RestClients;
//
///**
// * @author cdfive
// */
//@Configuration
//public class EsConfig {
//
//    @Bean
//    RestHighLevelClient client() {
////        ClientConfiguration clientConfiguration = ClientConfiguration.builder()
////                .connectedTo("localhost:9200", "localhost:9201")
////                .build();
////        return RestClients.create(clientConfiguration).rest();
//        HttpHost[] hosts = new HttpHost[1];
//        hosts[0] = new HttpHost("localhost", 9200, "http");
//        RestClientBuilder clientBuilder = RestClient.builder(hosts);
//        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
//        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("elastic", "elastic"));
//        clientBuilder.setHttpClientConfigCallback(httpAsyncClientBuilder ->
//                httpAsyncClientBuilder
//                        .setDefaultCredentialsProvider(credentialsProvider)
//                        .setMaxConnPerRoute(5)
//                        .setMaxConnTotal(20));
//        RestHighLevelClient restHighLevelClient = new RestHighLevelClient(clientBuilder);
//        return restHighLevelClient;
//    }
//}

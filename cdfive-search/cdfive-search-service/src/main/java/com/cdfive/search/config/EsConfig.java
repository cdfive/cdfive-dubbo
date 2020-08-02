package com.cdfive.search.config;//package com.cdfive.search.config;
//
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
//        ClientConfiguration clientConfiguration = ClientConfiguration.builder()
//                .connectedTo("localhost:9200", "localhost:9201")
//                .build();
//
//        return RestClients.create(clientConfiguration).rest();
//    }
//}

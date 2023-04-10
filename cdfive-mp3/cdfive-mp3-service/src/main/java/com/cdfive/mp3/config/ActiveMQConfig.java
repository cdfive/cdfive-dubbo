package com.cdfive.mp3.config;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jms.Queue;

/**
 * @author cdfive
 */
@Configuration
public class ActiveMQConfig {

    @Bean
    public Queue methodLogQueue() {
        return new ActiveMQQueue("methodLogQueue");
    }

    @Bean
    public Queue appRestApiQueue() {
        return new ActiveMQQueue("appRestApiQueue");
    }
}

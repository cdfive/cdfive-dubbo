//package com.cdfive.search.config;
//
//import org.apache.activemq.ActiveMQConnectionFactory;
//import org.apache.activemq.RedeliveryPolicy;
//import org.apache.activemq.pool.PooledConnectionFactory;
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * @author cdfive
// */
//@Configuration
//public class ActiveMQConfig implements InitializingBean {
//
//    @Autowired
//    private PooledConnectionFactory pooledConnectionFactory;
//
//    @Autowired
//    private RedeliveryPolicy redeliveryPolicy;
//
//    @Bean
//    public RedeliveryPolicy redeliveryPolicy() {
//        RedeliveryPolicy redeliveryPolicy = new RedeliveryPolicy();
//        redeliveryPolicy.setUseExponentialBackOff(true);
//        redeliveryPolicy.setMaximumRedeliveryDelay(-1);
//        redeliveryPolicy.setMaximumRedeliveries(0);
//        redeliveryPolicy.setInitialRedeliveryDelay(1);
//        redeliveryPolicy.setBackOffMultiplier(2);
//        redeliveryPolicy.setUseCollisionAvoidance(false);
//        return redeliveryPolicy;
//    }
//
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        ActiveMQConnectionFactory activeMQConnectionFactory = (ActiveMQConnectionFactory) pooledConnectionFactory.getConnectionFactory();
//        activeMQConnectionFactory.setRedeliveryPolicy(redeliveryPolicy);
//    }
//}

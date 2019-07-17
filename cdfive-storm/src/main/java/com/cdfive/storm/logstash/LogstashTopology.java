package com.cdfive.storm.logstash;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.kafka.spout.KafkaSpout;
import org.apache.storm.kafka.spout.KafkaSpoutConfig;
import org.apache.storm.topology.TopologyBuilder;

/**
 * @author cdfive
 */
public class LogstashTopology {

    public static void main(String[] args) {
        String boostrapServer = "localhost:9092";
        String topic = "cdfive-log";

        KafkaSpoutConfig<String, String> kafkaSpoutConfig = KafkaSpoutConfig.builder(boostrapServer, topic)
                .setProp(ConsumerConfig.GROUP_ID_CONFIG, "logstash-storm-group")
                .build();
        KafkaSpout kafkaSpout = new KafkaSpout(kafkaSpoutConfig);
        LogstashBolt logstashBolt = new LogstashBolt();

        TopologyBuilder topologyBuilder = new TopologyBuilder();
        topologyBuilder.setSpout("kafka_spout", kafkaSpout, 1);
        topologyBuilder.setBolt("logstash_bolt", logstashBolt).localOrShuffleGrouping("kafka_spout");

        try {
//            System.setProperty("storm.jar", "H:\\java_env\\apache-storm-1.1.1\\lib\\storm-core-1.1.1.jar");
            StormSubmitter.submitTopology("logstash_topology", new Config(), topologyBuilder.createTopology());
        } catch (AlreadyAliveException e) {
            e.printStackTrace();
        } catch (InvalidTopologyException e) {
            e.printStackTrace();
        } catch (AuthorizationException e) {
            e.printStackTrace();
        }

//        LocalCluster localCluster = new LocalCluster();
//        localCluster.submitTopology("logstash_topology", new Config(), topologyBuilder.createTopology());
    }
}

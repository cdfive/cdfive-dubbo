//package com.cdfive.storm.spout;
//
//import org.apache.storm.spout.SpoutOutputCollector;
//import org.apache.storm.task.TopologyContext;
//import org.apache.storm.topology.OutputFieldsDeclarer;
//import org.apache.storm.topology.base.BaseRichSpout;
//import org.apache.storm.tuple.Fields;
//
//import java.util.Map;
//
///**
// * @author cdfive
// */
//public class KafkaSpout extends BaseRichSpout {
//
//    protected SpoutOutputCollector spoutOutputCollector;
//    protected String declareOutputField;
//
//    @Override
//    public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {
//        this.spoutOutputCollector = spoutOutputCollector;
//    }
//
//    @Override
//    public void nextTuple() {
//
//    }
//
//    @Override
//    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
//        outputFieldsDeclarer.declare(new Fields(declareOutputField));
//    }
//}

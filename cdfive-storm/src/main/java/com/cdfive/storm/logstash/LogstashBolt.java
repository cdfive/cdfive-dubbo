package com.cdfive.storm.logstash;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.http.HttpHost;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

/**
 * @author cdfive
 */
public class LogstashBolt extends BaseRichBolt {

    private String DATE_FORMAT = "yyyy.MM.dd";

    private OutputCollector outputCollector;

    private RestHighLevelClient client;

    @Override
    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
        this.outputCollector = outputCollector;
        this.client = new RestHighLevelClient(RestClient.builder(new HttpHost("localhost", 9200, "http")));
    }

    @Override
    public void execute(Tuple tuple) {
        String value = tuple.getStringByField("value");
        System.out.println("From kafka: " + value);

        JSONObject jsonObject = JSONObject.parseObject(value);
        String appName = jsonObject.getString("appName");
        String index = appName + "-" + DateFormatUtils.format(new Date(), DATE_FORMAT);

        // org.elasticsearch.action.ActionRequestValidationException: Validation Failed: 1: type is missing;
//        IndexRequest request = new IndexRequest(index);
        IndexRequest request = new IndexRequest(index, "doc");
        request.source(value, XContentType.JSON);
        try {
            IndexResponse response = client.index(request);
            System.out.println("Add success=>" + response.getResult());
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.outputCollector.ack(tuple);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {

    }
}

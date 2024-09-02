package com.cdfive.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @author cdfive
 */
@Slf4j
public class DingTalkUtil {

    private static final RestTemplate REST_TEMPLATE = new RestTemplate();

    public static void sendMarkdownMessage(String api, String secret, String title, String markdownContent) {
        Map<String, String> contentMap = new HashMap<>(2);
        contentMap.put("title", title);
        contentMap.put("text", markdownContent);
        String contentJson = JacksonUtil.objToJson(contentMap);

        Map<String, String> requestData = new HashMap<>(2);
        requestData.put("msgtype", "markdown");
        requestData.put("markdown", contentJson);
        sendMessage(api, secret, requestData);
    }

    public static void sendMessage(String api, String secret, Map<String, String> requestData) {
        long timestamp = System.currentTimeMillis();
        String url = String.format("%s&timestamp=%s&sign=%s", api, timestamp, SHA256Util.signBySHA256(secret, timestamp));
        String resp = REST_TEMPLATE.postForObject(url, requestData, String.class);
        log.info("DingTalkUtil sendMessage,resp={}", resp);
    }
}

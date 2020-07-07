package com.cdfive.learn.http;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

/**
 * @author cdfive
 */
public class RestTemplateTest {

    private static String dashboardUrl = "http://sentinel.cdfive.com";

    private static String appName = "cdfive-mp3";

    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();

        // 登录控制台
        String loginUrl = dashboardUrl + "/auth/login";
        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add("username", "sentinel");
        map.add("password", "sentinel");
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(loginUrl, request, String.class);
        System.out.println("response: " + response.getBody());
        String cookie = response.getHeaders().get(HttpHeaders.SET_COOKIE).get(0);
        System.out.println("cookie: " + cookie);
        System.out.println("------------------------------------");

        // 查询应用名为appName的机器列表
        String queryMachinesUrl = dashboardUrl + "/app/" + appName + "/machines.json";
        List<String> cookies = Arrays.asList(cookie);
        headers = new HttpHeaders();
        headers.put(HttpHeaders.COOKIE, cookies);
        map = new LinkedMultiValueMap<String, String>();
        request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
        response = restTemplate.exchange(queryMachinesUrl, HttpMethod.GET, request, String.class);
        System.out.println("response: " + response.getBody());
    }
}

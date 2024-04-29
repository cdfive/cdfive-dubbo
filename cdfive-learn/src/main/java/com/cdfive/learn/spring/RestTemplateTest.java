package com.cdfive.learn.spring;

import com.google.common.base.Throwables;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author cdfive
 */
public class RestTemplateTest {

    public static void main(String[] args) throws Exception {
        String url = "http://127.0.0.1:10003/api/v1/search/test";

        RestTemplate restTemplate = new RestTemplate();

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
//                    long time = System.currentTimeMillis();
                    long start = System.currentTimeMillis();
                    String time = UUID.randomUUID().toString().replaceAll("-", "");
                    System.out.println(time + " start");
                    ResponseEntity<String> responseEntity = null;
                    try {
                        responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
                        System.out.println(time + " success,cost=" + (System.currentTimeMillis() - start) + "ms=>" + responseEntity.getBody());
                    } catch (Exception e) {
                        System.out.println(time + " error,cost=" + (System.currentTimeMillis() - start) + "ms=>" + e.getMessage() + "\n,cause=" + Throwables.getRootCause(e));
                    }
                }
            });
        }

        System.out.println("waiting...");

        executorService.shutdown();
        if (!executorService.awaitTermination(5, TimeUnit.MINUTES)) {
            executorService.shutdownNow();
        }

        System.out.println("done");
    }
}

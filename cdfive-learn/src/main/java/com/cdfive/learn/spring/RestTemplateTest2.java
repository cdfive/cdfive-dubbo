package com.cdfive.learn.spring;

import lombok.Data;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author cdfive
 */
public class RestTemplateTest2 {

    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://www.httpbin.org/json";
        HttpEntity<String> request = new HttpEntity<>(null);
        ResponseEntity<Result> responseEntity = restTemplate.exchange(url, HttpMethod.GET, request, Result.class);
        Result result = responseEntity.getBody();
        System.out.println(result);
    }

    @Data
    private static class Result {
        private Slideshow slideshow;
    }

    @Data
    private static class Slideshow {
        private String author;
        private String date;
        private List<Slides> slides;
        private String title;
    }

    @Data
    private static class Slides {
        private String title;
        private String type;
    }
//    {
//        "slideshow": {
//                "author": "Yours Truly",
//                "date": "date of publication",
//                "slides": [
//                    {
//                        "title": "Wake up to WonderWidgets!",
//                            "type": "all"
//                    },
//                    {
//                        "items": [
//                        "Why <em>WonderWidgets</em> are great",
//                                "Who <em>buys</em> WonderWidgets"
//                        ],
//                        "title": "Overview",
//                        "type": "all"
//                    }
//                ],
//            "title": "Sample Slide Show"
//        }
//    }
}

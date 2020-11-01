package com.cdfive.learn.spring;

import lombok.Data;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
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

        ResponseEntity<Result> responseEntity2 = restTemplate.exchange(url, HttpMethod.GET, request, new ParameterizedTypeReference<Result>(){});
        Result result2 = responseEntity2.getBody();
        System.out.println(result2);

        ResponseEntity<Result<Slideshow>> responseEntity3 = restTemplate.exchange(url, HttpMethod.GET, request, new ParameterizedTypeReference<Result<Slideshow>>(){});
        Result<Slideshow> result3 = responseEntity3.getBody();
        System.out.println(result3);

        Result<Slideshow> result4 = sendGet(url);
        System.out.println(result4);

        Result<Slideshow> result5 = sendGet2(url, new ParameterizedTypeReference<Result<Slideshow>>() {});
        System.out.println(result5);
    }

    public static <T> Result<T> sendGet(String url) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> request = new HttpEntity<>(null);
        ResponseEntity<Result<T>> responseEntity = restTemplate.exchange(url, HttpMethod.GET, request, new ParameterizedTypeReference<Result<T>>() {});
        return responseEntity.getBody();
    }

    public static <T> Result<T> sendGet2(String url, ParameterizedTypeReference<Result<T>> responseType) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> request = new HttpEntity<>(null);
        ResponseEntity<Result<T>> responseEntity = restTemplate.exchange(url, HttpMethod.GET, request, responseType);
        return responseEntity.getBody();
    }

//    public static <T> Result<T> sendGet3(String url, Class<T> clazz) {
//        RestTemplate restTemplate = new RestTemplate();
//        HttpEntity<String> request = new HttpEntity<>(null);
//        Type type = clazz.getGenericSuperclass();
//        ResponseEntity<Result<T>> responseEntity = restTemplate.exchange(url, HttpMethod.GET, request, new ParameterizedTypeReference<Result<T>>());
//        return responseEntity.getBody();
//    }

    @Data
    private static class Result<T> {
        private T slideshow;
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

package com.cdfive.learn.springcloud;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.*;
import feign.codec.DecodeException;
import feign.codec.Decoder;
import lombok.Data;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * @author cdfive
 */
public class FeignTest {

    public static void main(String[] args) {
//        String url = "https://res-static.hc-cdn.cn/cloudbu-site/china/zh-cn/global/interceptor.json";

        Class<TestApi> type = TestApi.class;
        String name = "huaweiCloud";
        String url = "https://res-static.hc-cdn.cn";
        Target.HardCodedTarget<TestApi> hardCodedTarget = new Target.HardCodedTarget<>(type, name, url);

//        TestApi testApi = Feign.builder().target(hardCodedTarget);
//        String result = testApi.huaweiCloudInterceptor1();
//        System.out.println(result);

        //  feign.codec.StringDecoder.decode: feign.codec.DecodeException
//        TestApi testApi = Feign.builder().target(hardCodedTarget);
        TestApi testApi = Feign.builder().decoder(new TestDecoder()).target(hardCodedTarget);
        RespVo respVo = testApi.huaweiCloudInterceptor2();
        System.out.println(respVo);
    }

    private interface TestApi {

        @RequestLine(value = "GET /cloudbu-site/china/zh-cn/global/interceptor.json")
        String huaweiCloudInterceptor1();

        @RequestLine(value = "GET /cloudbu-site/china/zh-cn/global/interceptor.json")
        RespVo huaweiCloudInterceptor2();
    }

    private static class TestDecoder implements Decoder {

        @Override
        public Object decode(Response response, Type type) throws IOException, DecodeException, FeignException {
            Response.Body body = response.body();
            if (body == null) {
                return null;
            }

            String bodyStr = Util.toString(body.asReader(Util.UTF_8));

            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(bodyStr, objectMapper.constructType(type));
        }
    }

    @Data
    private static class RespVo {

        private List<String> whiteList;

        private Map<String, Map<String, ItemVo>> prompt;

        @Override
        public String toString() {
//            return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);

            ObjectMapper mapper = new ObjectMapper();
            String json = null;
            try {
                json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(this);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            return json;
        }

        @Data
        private static class ItemVo {

            private String title;

            private String desc;

            private String confirm;

            private String cancel;

            private List<String> whiteList;
        }
    }
}

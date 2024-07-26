package com.cdfive.learn.json.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.DeserializationProblemHandler;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.io.Serializable;

/**
 * @author cdfive
 */
public class JacksonDemo3 {

    public static void main(String[] args) throws Exception {
        String str = "{\n" +
                "    \"id\":\"12345678901234567891\",\n" +
                "    \"name\":111,\n" +
                "    \"clientType\":\"WX1\"\n" +
                "}";

//        str = "{\n" +
//                "    \"id\":\"1234567890123456789\",\n" +
//                "    \"name\":111,\n" +
//                "    \"clientType\":\"WX1\"\n" +
//                "}";

        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.addHandler(new CustomDeserializationProblemHandler());

        DemoReqVo demoReqVo = objectMapper.readValue(str, DemoReqVo.class);

        // CustomJsonMappingException: 入参转换失败,字段id(类型Long)格式不合法
        System.out.println(demoReqVo);
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    private static class DemoReqVo implements Serializable {

        private static final long serialVersionUID = 7177288567592077959L;

        private Long id;

        private String name;

        private ClientType clientType;

        @AllArgsConstructor
        @Getter
        public static enum ClientType {

            WX("微信"),
            ZFB("支付宝"),
            ;

            private String desc;
        }
    }

    private static class CustomDeserializationProblemHandler extends DeserializationProblemHandler {
        @Override
        public Object handleWeirdStringValue(DeserializationContext ctxt, Class<?> targetType, String valueToConvert, String failureMsg) throws IOException {
            JsonParser parser = ctxt.getParser();
            JsonStreamContext context = parser.getParsingContext();
            String currentName = context.getCurrentName();
            throw new CustomJsonMappingException("入参转换失败,字段:" + currentName + "(类型:" + targetType.getSimpleName() + ")格式不合法");
        }
    }

    private static class CustomJsonMappingException extends JsonMappingException {

        public CustomJsonMappingException(String msg) {
            super(msg);
        }

        @Override
        protected String _buildMessage() {
            _path = null;
            return super._buildMessage();
        }
    }
}

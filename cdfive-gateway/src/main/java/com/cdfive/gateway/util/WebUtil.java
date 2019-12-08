package com.cdfive.gateway.util;

import io.netty.buffer.ByteBufAllocator;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.NettyDataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Web工具类
 *
 * @author cdfive
 */
public class WebUtil {

    /**
     * 按参数名排序,返回排序后列表
     */
    public static List<Map.Entry<String, List<String>>> sortParameterName(MultiValueMap<String, String> queryParamMap) {
        List<Map.Entry<String, List<String>>> sortList = new ArrayList<>(queryParamMap.entrySet());
        Collections.sort(sortList, Comparator.comparing(Map.Entry::getKey));
        return sortList;
    }

    /**
     * 获取参数内容,格式为key=value&...,忽略签名key
     */
    public static String getParameterContents(List<Map.Entry<String, List<String>>> queryParamList, String skipKey) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, List<String>> item : queryParamList) {
            String key = item.getKey();
            if (key.equals(skipKey)) {
                continue;
            }
            List<String> valueList = item.getValue();
            String value = valueList != null && valueList.size() > 0 ? valueList.get(0) : "";
            sb.append(key).append("=").append(value).append("&");
        }
        return sb.toString();
    }

    /**
     * 验证失败返回
     */
    public static Mono<Void> writeAuthFailResponse(ServerWebExchange exchange, Integer code, String msg) {
        ServerHttpResponse originalResponse = exchange.getResponse();
        originalResponse.setStatusCode(HttpStatus.UNAUTHORIZED);
        originalResponse.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        byte[] response = ("{\"code\": " + code + ",\"msg\": \"" + msg + "\"}")
                .getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = originalResponse.bufferFactory().wrap(response);
        return originalResponse.writeWith(Flux.just(buffer));
    }

    /**
     * 获取body参数内容
     */
    public static String resolveBodyFromRequest(ServerWebExchange exchange) {
        Object requestBody = exchange.getAttribute("cachedRequestBodyObject");
        if (requestBody != null && requestBody instanceof String) {
            return (String) requestBody;
        }

        return "";
    }

    /**
     * 获取body参数内容
     */
    @Deprecated
    public static String resolveBodyFromRequest(ServerHttpRequest serverHttpRequest) {
        Flux<DataBuffer> body = serverHttpRequest.getBody();

        AtomicReference<String> bodyRef = new AtomicReference<>();
        body.subscribe(buffer -> {
            CharBuffer charBuffer = StandardCharsets.UTF_8.decode(buffer.asByteBuffer());
            DataBufferUtils.release(buffer);
            bodyRef.set(charBuffer.toString());
        });

        return bodyRef.get();
    }

    /**
     * 获取body参数内容
     */
    @Deprecated
    public static String resolveBodyFromRequest2(ServerHttpRequest serverHttpRequest) {
        Flux<DataBuffer> body = serverHttpRequest.getBody();
        StringBuilder sb = new StringBuilder();

        body.subscribe(buffer -> {
            byte[] bytes = new byte[buffer.readableByteCount()];
            buffer.read(bytes);
            DataBufferUtils.release(buffer);
            String bodyString = new String(bytes, StandardCharsets.UTF_8);
            sb.append(bodyString);
        });
        return sb.toString();
    }

    /**
     * String转DataBuffer
     */
    public static DataBuffer convertToDataBuffer(String value) {
        if (value == null) {
            value = "";
        }

        byte[] bytes = value.getBytes(StandardCharsets.UTF_8);

        NettyDataBufferFactory nettyDataBufferFactory = new NettyDataBufferFactory(ByteBufAllocator.DEFAULT);
        DataBuffer buffer = nettyDataBufferFactory.allocateBuffer(bytes.length);
        buffer.write(bytes);
        return buffer;
    }

    /**
     * 包装body请求
     */
    @Deprecated
    public static Mono<Void> wrapRequestBody(ServerWebExchange exchange, GatewayFilterChain chain, ServerHttpRequest serverHttpRequest, String bodyStr) {
        URI uri = serverHttpRequest.getURI();
        ServerHttpRequest request = serverHttpRequest.mutate().uri(uri).build();
        DataBuffer bodyDataBuffer = WebUtil.convertToDataBuffer(bodyStr);
        Flux<DataBuffer> bodyFlux = Flux.just(bodyDataBuffer);

        request = new ServerHttpRequestDecorator(request) {
            @Override
            public Flux<DataBuffer> getBody() {
                return bodyFlux;
            }
        };

        return chain.filter(exchange.mutate().request(request).build());
    }

    /**
     * 获取客户端ip
     */
    public static String getClientIp(ServerHttpRequest serverHttpRequest) {
        HttpHeaders headers = serverHttpRequest.getHeaders();
        String ip = headers.getFirst("x-forwarded-for");
        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
            if (ip.indexOf(",") != -1) {
                ip = ip.split(",")[0];
            }
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = headers.getFirst("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = headers.getFirst("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = headers.getFirst("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = headers.getFirst("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = headers.getFirst("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = serverHttpRequest.getRemoteAddress().getAddress().getHostAddress();
        }
        return ip;
    }
}

package com.cdfive.gateway.filter.auth;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.cdfive.common.component.jwt.JwtComponent;
import com.cdfive.gateway.util.WebUtil;
import io.netty.buffer.ByteBufAllocator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.NettyDataBufferFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Auth filter with JWT
 *
 * @author cdfive
 */
@Slf4j
public class JwtAuthGatewayFilter implements GatewayFilter, Ordered {

    private static final Integer AUTH_FAIL_CODE = 30002;

    private static final String AUTH_FAIL_MSG = "登录超时，请重新登录";

    private static final String HEADER_KEY_TOKEN = "Authorization";

    private static final String HEADER_KEY_USER_ID = "userId";

    private static final String PARAMETER_KEY_USER_ID = "userId";

    private JwtComponent jwtComponent;

    private List<String> whiteList;

    public JwtAuthGatewayFilter(JwtComponent jwtComponent, List<String> whiteList) {
        this.jwtComponent = jwtComponent;
        this.whiteList = whiteList;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        if (!CollectionUtils.isEmpty(whiteList)) {
            if (whiteList.contains(exchange.getRequest().getPath().value())) {
                return chain.filter(exchange);
            }
        }

        ServerHttpRequest serverHttpRequest = exchange.getRequest();

        String token = serverHttpRequest.getHeaders().getFirst(HEADER_KEY_TOKEN);
        if (StringUtils.isEmpty(token)) {
            log.error("token empty");
            return WebUtil.writeAuthFailResponse(exchange, AUTH_FAIL_CODE, AUTH_FAIL_MSG);
        }

        JwtComponent.JwtClaims jwtClaims = jwtComponent.parseToken(token);
        if (jwtClaims == null) {
            log.error("jwtClaims empty");
            return WebUtil.writeAuthFailResponse(exchange, AUTH_FAIL_CODE, AUTH_FAIL_MSG);
        }

        Long expireTime = jwtClaims.getExpireTime();
        if (expireTime == null) {
            log.error("jwtClaims expireTime empty");
            return WebUtil.writeAuthFailResponse(exchange, AUTH_FAIL_CODE, AUTH_FAIL_MSG);
        }

        long time = System.currentTimeMillis() - expireTime;
        if (time > 0) {
            log.error("token expired");
            return WebUtil.writeAuthFailResponse(exchange, AUTH_FAIL_CODE, AUTH_FAIL_MSG);
        }

        Long tokenUserId = jwtClaims.getUserId();
        if (StringUtils.isEmpty(tokenUserId)) {
            log.error("jwtClaims userId empty");
            return WebUtil.writeAuthFailResponse(exchange, AUTH_FAIL_CODE, AUTH_FAIL_MSG);
        }

        boolean authSucc = true;
//        String bodyStr = WebUtil.resolveBodyFromRequest(exchange);
//        String bodyStr = WebUtil.resolveBodyFromRequest(exchange.getRequest());
//        String bodyStr = resolveBodyFromRequest(exchange.getRequest());
        String bodyStr = null;
        if (!StringUtils.isEmpty(bodyStr)) {
            Map<String, String> map = JSON.parseObject(bodyStr, new TypeReference<Map<String, String>>() {
            }.getType());
            String userId = map.get(PARAMETER_KEY_USER_ID);
            if (!StringUtils.isEmpty(userId)) {
                if (!userId.equals(tokenUserId.toString())) {
                    log.error("userId={}", userId);
                    log.error("tokenUserId={}", tokenUserId);
                    log.error("userId and tokenUserId is not match");
                    return WebUtil.writeAuthFailResponse(exchange, AUTH_FAIL_CODE, AUTH_FAIL_MSG);
                } else {
                    authSucc = true;
                }
            }
        }

        String userId = serverHttpRequest.getQueryParams().getFirst(PARAMETER_KEY_USER_ID);
        if (!StringUtils.isEmpty(userId)) {
            if (!userId.equals(tokenUserId.toString())) {
                log.error("userId={}", userId);
                log.error("tokenUserId={}", tokenUserId);
                log.error("userId and tokenUserId is not match");
                return WebUtil.writeAuthFailResponse(exchange, AUTH_FAIL_CODE, AUTH_FAIL_MSG);
            } else {
                authSucc = true;
            }
        }

        if (!authSucc) {
            log.error("auth failed,can not find userId");
            return WebUtil.writeAuthFailResponse(exchange, AUTH_FAIL_CODE, AUTH_FAIL_MSG);
        }

        ServerHttpRequest request = exchange.getRequest().mutate().headers((headers) -> {
            headers.remove(HEADER_KEY_TOKEN);
        }).header(HEADER_KEY_USER_ID, tokenUserId.toString()).build();

        if (bodyStr != null) {
            ServerHttpRequest httpRequest = this.resRequest(bodyStr, request);
            return chain.filter(exchange.mutate().request(httpRequest).build());
        }

        return chain.filter(exchange.mutate().request(request).build());
    }

    @Override
    public int getOrder() {
        return -10000;
    }

    private String resolveBodyFromRequest(ServerHttpRequest serverHttpRequest) {
        AtomicReference<String> bodyRef = new AtomicReference<>();
        Flux<DataBuffer> body = serverHttpRequest.getBody();
        body.subscribe(buffer -> {
            CharBuffer charBuffer = StandardCharsets.UTF_8.decode(buffer.asByteBuffer());
            DataBufferUtils.release(buffer);
            bodyRef.set(charBuffer.toString());
        });
        return bodyRef.get();
    }

    private ServerHttpRequest resRequest(String body, ServerHttpRequest request) {
        DataBuffer bodyDataBuffer = stringBuffer(body);
        Flux<DataBuffer> bodyFlux = Flux.just(bodyDataBuffer);

        request = new ServerHttpRequestDecorator(request) {
            @Override
            public Flux<DataBuffer> getBody() {
                return bodyFlux;
            }
        };
        return request;

    }

    private DataBuffer stringBuffer(String value) {
        if (value == null) {
            return null;
        }

        byte[] bytes = value.getBytes(StandardCharsets.UTF_8);
        NettyDataBufferFactory nettyDataBufferFactory = new NettyDataBufferFactory(ByteBufAllocator.DEFAULT);
        DataBuffer buffer = nettyDataBufferFactory.allocateBuffer(bytes.length);
        buffer.write(bytes);
        return buffer;
    }
}

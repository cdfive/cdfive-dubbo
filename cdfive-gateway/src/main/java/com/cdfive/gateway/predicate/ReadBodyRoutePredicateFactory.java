//package com.cdfive.gateway.predicate;
//
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.springframework.cloud.gateway.handler.AsyncPredicate;
//import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
//import org.springframework.cloud.gateway.support.BodyInserterContext;
//import org.springframework.cloud.gateway.support.CachedBodyOutputMessage;
//import org.springframework.cloud.gateway.support.DefaultServerRequest;
//import org.springframework.web.reactive.function.BodyInserter;
//import org.springframework.web.reactive.function.BodyInserters;
//import org.springframework.web.reactive.function.server.ServerRequest;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
//import java.util.function.Predicate;
//
//import static org.springframework.cloud.gateway.filter.AdaptCachedBodyGlobalFilter.CACHED_REQUEST_BODY_KEY;
//
///**
// * 参考Spring Cloud Gateway官方提供的ReadBodyPredicateFactory,解决请求参数body读取不全的问题
// * @see {@link org.springframework.cloud.gateway.handler.predicate.ReadBodyPredicateFactory}
// *
// * @author cdfive
// */
//public class ReadBodyRoutePredicateFactory extends AbstractRoutePredicateFactory<ReadBodyRoutePredicateFactory.Config> {
//
//    protected static final Log LOGGER = LogFactory.getLog(ReadBodyRoutePredicateFactory.class);
//
//    private static final String CACHE_REQUEST_BODY_OBJECT_KEY = "cachedRequestBodyObject";
//
//    public ReadBodyRoutePredicateFactory() {
//        super(ReadBodyRoutePredicateFactory.Config.class);
//    }
//
//    @Override
//    @SuppressWarnings("unchecked")
//    public AsyncPredicate<ServerWebExchange> applyAsync(ReadBodyRoutePredicateFactory.Config config) {
//        return exchange -> {
//            Object cachedBody = exchange.getAttribute(CACHE_REQUEST_BODY_OBJECT_KEY);
//            Mono<String> modifiedBody;
//            // We can only read the body from the request once, once that happens if we try to read the body again an
//            // exception will be thrown.  The below if/else caches the body object as a request attribute in the ServerWebExchange
//            // so if this filter is run more than once (due to more than one route using it) we do not try to read the
//            // request body multiple times
//            if(cachedBody != null) {
//                modifiedBody = Mono.just(cachedBody != null && cachedBody instanceof String ? (String) cachedBody : "");
//            } else {
//                ServerRequest serverRequest = new DefaultServerRequest(exchange);
//                // TODO: flux or mono
//                modifiedBody = serverRequest.bodyToMono(String.class)
//                        .flatMap(body -> {
//                            // TODO: migrate to async
//                            exchange.getAttributes().put(CACHE_REQUEST_BODY_OBJECT_KEY, body);
//                            return Mono.just(body);
//                        });
//            }
//            BodyInserter bodyInserter = BodyInserters.fromPublisher(modifiedBody, String.class);
//            CachedBodyOutputMessage outputMessage = new CachedBodyOutputMessage(exchange,
//                    exchange.getRequest().getHeaders());
//            return bodyInserter.insert(outputMessage, new BodyInserterContext())
//                    .then(Mono.defer(() -> {
//                        exchange.getAttributes().put(CACHED_REQUEST_BODY_KEY,
//                                outputMessage.getBody());
//                        return Mono.just(true);
//                    }));
//        };
//    }
//
//    @Override
//    @SuppressWarnings("unchecked")
//    public Predicate<ServerWebExchange> apply(ReadBodyRoutePredicateFactory.Config config) {
//        throw new UnsupportedOperationException(
//                "ReadBodyPredicateFactory is only async.");
//    }
//
//    public static class Config {
//
//    }
//}
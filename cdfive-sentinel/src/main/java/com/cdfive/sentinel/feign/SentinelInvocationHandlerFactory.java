package com.cdfive.sentinel.feign;

import feign.InvocationHandlerFactory;
import feign.Target;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author cdfive
 */
public class SentinelInvocationHandlerFactory implements InvocationHandlerFactory {

    /**
     * Note: this delegate will not be used, just be reserved.
     */
    private final InvocationHandlerFactory delegate;

    public SentinelInvocationHandlerFactory(InvocationHandlerFactory delegate) {
        this.delegate = delegate;
    }

    @Override
    public InvocationHandler create(Target target, Map<Method, MethodHandler> dispatch) {
        SentinelInvocationHandler sentinelInvocationHandler = new SentinelInvocationHandler(target, dispatch);
        return sentinelInvocationHandler;
    }
}

package com.cdfive.sentinel.feign;

import feign.Capability;
import feign.InvocationHandlerFactory;

/**
 * @author cdfive
 */
public class SentinelCapability implements Capability {

    @Override
    public InvocationHandlerFactory enrich(InvocationHandlerFactory invocationHandlerFactory) {
        return new SentinelInvocationHandlerFactory(invocationHandlerFactory);
    }
}

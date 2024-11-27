package com.cdfive.sentinel.feign;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.EntryType;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.Tracer;
import com.alibaba.csp.sentinel.context.ContextUtil;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import feign.InvocationHandlerFactory;
import feign.MethodMetadata;
import feign.Target;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author cdfive
 */
public class SentinelInvocationHandler implements InvocationHandler {

    private final Target target;

    private final Map<Method, InvocationHandlerFactory.MethodHandler> dispatch;

    public SentinelInvocationHandler(Target target, Map<Method, InvocationHandlerFactory.MethodHandler> dispatch) {
        this.target = target;
        this.dispatch = dispatch;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        InvocationHandlerFactory.MethodHandler methodHandler = dispatch.get(method);

        String name = method.getName();
        if ("toString".equals(name)) {
            return target.toString();
        }

        if ("hashCode".equals(name)) {
            return target.hashCode();
        }

        if ("equals".equals(name)) {
            return target.equals(args);
        }

        Field fieldMetadata = ReflectionUtils.findField(methodHandler.getClass(), "metadata");
        ReflectionUtils.makeAccessible(fieldMetadata);
        Object objMetadata = ReflectionUtils.getField(fieldMetadata, methodHandler);
        MethodMetadata methodMetadata = (MethodMetadata) objMetadata;

        String httpMethod = methodMetadata.template().method();
        String url = target.url();
        String path = methodMetadata.template().path();

        String resourceName = httpMethod + ":" + url + path;

        Entry entry = null;
        Object result = null;
        try {
            ContextUtil.enter(resourceName);
            entry = SphU.entry(resourceName, EntryType.OUT, 1, args);
            result = methodHandler.invoke(args);
        } catch (Throwable ex) {
            if (!BlockException.isBlockException(ex)) {
                Tracer.trace(ex);
            }

            throw ex;
        } finally {
            if (entry != null) {
                entry.exit(1, args);
            }
            ContextUtil.exit();
        }

        return result;
    }
}

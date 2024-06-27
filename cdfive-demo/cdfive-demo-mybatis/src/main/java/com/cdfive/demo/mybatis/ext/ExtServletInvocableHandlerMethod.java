package com.cdfive.demo.mybatis.ext;

import com.cdfive.demo.mybatis.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod;

import java.lang.reflect.Method;

/**
 * @author cdfive
 */
@Slf4j
public class ExtServletInvocableHandlerMethod extends ServletInvocableHandlerMethod {

    public ExtServletInvocableHandlerMethod(Object handler, Method method) {
        super(handler, method);
    }

    public ExtServletInvocableHandlerMethod(HandlerMethod handlerMethod) {
        super(handlerMethod);
    }

    @Override
    protected Object doInvoke(Object... args) throws Exception {
        long start = System.currentTimeMillis();
        log.info("logController start,method={},reqVo={}", this.toString(), getReqVoJson(args));
        Object result = null;
        try {
            result = super.doInvoke(args);
            log.info("logController success,method={},cost={}ms,reqVo={},respVo={}"
                    , this.toString(), (System.currentTimeMillis() - start), getReqVoJson(args), JsonUtil.objToStr(result));
        } catch (Exception e) {
            log.info("logController error,method={},cost={}ms,reqVo={}"
                    , this.toString(), (System.currentTimeMillis() - start), getReqVoJson(args), e);
            throw e;
        }
        return result;
    }

    private String getReqVoJson(Object... args) {
        if (args == null || args.length == 0) {
            return null;
        }

        if (args.length == 1) {
            return JsonUtil.objToStr(args[0]);
        }

        return JsonUtil.objToStr(args);
    }
}

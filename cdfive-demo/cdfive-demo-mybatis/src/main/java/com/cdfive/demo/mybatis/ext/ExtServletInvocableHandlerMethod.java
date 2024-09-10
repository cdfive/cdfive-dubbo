package com.cdfive.demo.mybatis.ext;

import com.cdfive.demo.mybatis.util.JsonUtil;
import com.cdfive.demo.mybatis.util.RequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.support.ModelAndViewContainer;
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
        log.info("logController start,method={},reqVo={}", this.toString(), JsonUtil.controllerArgsToStr(args));
        Object result = null;
        try {
            RequestUtil.setRequestAttrReq(args);
            result = super.doInvoke(args);
            RequestUtil.setRequestAttrResp(result);
            log.info("logController success,method={},cost={}ms,reqVo={},respVo={}"
                    , this.toString(), (System.currentTimeMillis() - start), JsonUtil.controllerArgsToStr(args), JsonUtil.controllerArgsToStr(result));
        } catch (Exception e) {
            log.error("logController error,method={},cost={}ms,reqVo={}"
                    , this.toString(), (System.currentTimeMillis() - start), JsonUtil.controllerArgsToStr(args), e);
            throw e;
        }
        return result;
    }

    @Override
    protected Object[] getMethodArgumentValues(NativeWebRequest request, ModelAndViewContainer mavContainer, Object... providedArgs) throws Exception {
        return super.getMethodArgumentValues(request, mavContainer, providedArgs);
    }
}

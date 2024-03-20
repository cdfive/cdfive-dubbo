package com.cdfive.framework.spring.webmvc;

import com.cdfive.framework.util.JacksonUtil;
import com.cdfive.framework.util.ServletUtil;
import com.cdfive.framework.vo.api.ApiResponse;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author cdfive
 */
@ConditionalOnClass(HandlerMethodReturnValueHandler.class)
@ConditionalOnProperty(name = "cdfive.framework.wrapApiResponse", havingValue = "true", matchIfMissing = false)
@RefreshScope
@Component
public class WrapApiResponseHandlerMethodReturnValueHandler implements HandlerMethodReturnValueHandler, InitializingBean {

    @Autowired
    private RequestMappingHandlerAdapter requestMappingHandlerAdapter;

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return true;
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {
        ApiResponse<?> apiResponse = null;
        if (returnValue instanceof ApiResponse) {
            apiResponse = (ApiResponse<?>) returnValue;
        } else {
            apiResponse = ApiResponse.succ(returnValue);
        }

        HttpServletRequest httpServletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
        apiResponse.setTraceId(ServletUtil.getRequestTraceId(httpServletRequest));

        HttpServletResponse nativeResponse = webRequest.getNativeResponse(HttpServletResponse.class);
        nativeResponse.setContentType("application/json;charset=UTF-8");
        PrintWriter writer = nativeResponse.getWriter();
        writer.write(JacksonUtil.objToJson(apiResponse));
        writer.flush();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        List<HandlerMethodReturnValueHandler> handlers = requestMappingHandlerAdapter.getReturnValueHandlers();
        List<HandlerMethodReturnValueHandler> list = new ArrayList<>();
        list.add(this);
        if (handlers != null) {
            list.addAll(handlers);
        }
        requestMappingHandlerAdapter.setReturnValueHandlers(list);
    }
}

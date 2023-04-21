package com.cdfive.common.spring.webmvc;

import com.cdfive.common.api.ApiResponse;
import com.cdfive.common.util.CommonUtil;
import com.cdfive.common.util.JacksonUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xiejihan
 * @date 2023-04-19
 */
@ConditionalOnProperty(name = "cdfive.framework.wrapApiResponse", havingValue = "true")
@Component
public class WrapApiResponseHandlerMethodReturnValueHandler implements HandlerMethodReturnValueHandler, InitializingBean {

    private static final String TRACE_ID = "_traceId";

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
        apiResponse.setTraceId(getTraceId(httpServletRequest));

        HttpServletResponse nativeResponse = webRequest.getNativeResponse(HttpServletResponse.class);
        nativeResponse.setContentType("application/json;charset=UTF-8");
        PrintWriter writer = nativeResponse.getWriter();
        writer.write(JacksonUtil.objToJson(apiResponse));
        writer.flush();

//        byte[] bytes = JacksonUtil.objToJson(apiResponse).getBytes(Charset.defaultCharset());
//        nativeResponse.resetBuffer();
//        nativeResponse.getOutputStream().write(bytes);
//        nativeResponse.getOutputStream().flush();
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

    private String getTraceId(HttpServletRequest request) {
        Object objTraceId = request.getAttribute(TRACE_ID);
        if (objTraceId != null) {
            return objTraceId.toString();
        }

        return CommonUtil.getTraceId();
    }
}

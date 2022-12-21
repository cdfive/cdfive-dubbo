package com.cdfive.mp3.config.springwebmvc;

import com.cdfive.common.util.JacksonUtil;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

/**
 * @author cdfive
 */
public class RequestResponseBodyMethodProcessorWrapper implements HandlerMethodArgumentResolver {

    public final static String resolveArgumentKey = "resolveArgumentObject";

    private HandlerMethodArgumentResolver handlerMethodReturnValueHandler;

    public RequestResponseBodyMethodProcessorWrapper(HandlerMethodArgumentResolver handlerMethodReturnValueHandler) {
        this.handlerMethodReturnValueHandler = handlerMethodReturnValueHandler;
    }


    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return handlerMethodReturnValueHandler.supportsParameter(methodParameter);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        Object argumentObject = handlerMethodReturnValueHandler.resolveArgument(methodParameter, modelAndViewContainer, nativeWebRequest, webDataBinderFactory);
        if (argumentObject != null) {
            HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
            request.setAttribute(resolveArgumentKey, argumentObject);
        }
        return argumentObject;
    }

    public static String getRequestBody() {
        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
        Object data = request.getAttribute(RequestResponseBodyMethodProcessorWrapper.resolveArgumentKey);
        return data != null ? JacksonUtil.objToJson(data) : null;
    }
}

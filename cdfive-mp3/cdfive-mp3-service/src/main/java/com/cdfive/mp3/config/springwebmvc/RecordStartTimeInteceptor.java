package com.cdfive.mp3.config.springwebmvc;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author cdfive
 */
public class RecordStartTimeInteceptor implements HandlerInterceptor {

    private final static String REQUEST_KEY_START_TIME = "_start_time";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpServletRequest httpServletRequest = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
        httpServletRequest.setAttribute(REQUEST_KEY_START_TIME, System.currentTimeMillis());
        return true;
    }

    public static Long getRequestCostMs() {
        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
        Object data = request.getAttribute(REQUEST_KEY_START_TIME);
        return data != null ? (System.currentTimeMillis() - (long) data) : null;
    }
}

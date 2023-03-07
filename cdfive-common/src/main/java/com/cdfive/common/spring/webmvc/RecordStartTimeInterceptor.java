package com.cdfive.common.spring.webmvc;

import com.cdfive.common.util.WebUtil;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author cdfive
 */
public class RecordStartTimeInterceptor implements HandlerInterceptor {

    private static final String REQUEST_KEY_START_TIME = "_start_time";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        request.setAttribute(REQUEST_KEY_START_TIME, System.currentTimeMillis());
        return true;
    }

    public static Long getRequestCostMs() {
        HttpServletRequest httpServletRequest = WebUtil.getCurrentHttpServletRequest();
        Object data = httpServletRequest.getAttribute(REQUEST_KEY_START_TIME);
        return data != null ? (System.currentTimeMillis() - (long) data) : null;
    }
}

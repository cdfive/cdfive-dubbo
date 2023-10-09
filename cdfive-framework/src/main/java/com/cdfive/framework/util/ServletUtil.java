package com.cdfive.framework.util;

import lombok.SneakyThrows;
import org.springframework.util.StreamUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.Charset;

/**
 * @author cdfive
 */
public class ServletUtil {

    public static final String TRACE_ID = "_trace_id";

    public static final String REQUEST_START_TIME = "_request_start_time";

    public static final String REQUEST_TIME_COST_MS = "_request_time_cost_ms";

    public static HttpServletRequest getCurrentHttpServletRequest() {
        if (RequestContextHolder.getRequestAttributes() == null) {
            return null;
        }
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();
        return request;
    }

    // TODO improve
    public static String getTraceId(HttpServletRequest request) {
        String requestTraceId = getRequestTraceId(request);
        if (StringUtil.isNotEmpty(requestTraceId)) {
            return requestTraceId;
        }

        String headerTraceId = request.getHeader(TRACE_ID);
        if (StringUtil.isNotEmpty(headerTraceId)) {
            return headerTraceId;
        }

        return CommonUtil.generateTraceId();
    }

    public static String getRequestTraceId(HttpServletRequest request) {
        Object attr = request.getAttribute(TRACE_ID);
        if (attr != null && attr instanceof String) {
            return (String) attr;
        }

        return null;
    }

    public static void setRequestTraceId(HttpServletRequest request, String traceId) {
        request.setAttribute(TRACE_ID, traceId);
    }

    public static Long getRequestStartTime(HttpServletRequest request) {
        Object attr = request.getAttribute(REQUEST_START_TIME);
        if (attr != null && attr instanceof Long) {
            return (Long) attr;
        }

        return null;
    }

    public static void setRequestStartTime(HttpServletRequest request, Long value) {
        request.setAttribute(REQUEST_START_TIME, value);
    }

    public static Long getRequestCostTimeMs(HttpServletRequest request) {
        Object attr = request.getAttribute(REQUEST_TIME_COST_MS);
        if (attr != null && attr instanceof Long) {
            return (Long) attr;
        }

        return null;
    }

    public static void setRequestCostTimeMs(HttpServletRequest request, Long value) {
        request.setAttribute(REQUEST_TIME_COST_MS, value);
    }

    @SneakyThrows
    public static String getRequestBody(HttpServletRequest request) {
        return StreamUtils.copyToString(request.getInputStream(), Charset.forName("UTF-8"));
    }
}

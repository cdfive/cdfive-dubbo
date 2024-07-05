package com.cdfive.framework.servlet.filter;

import com.cdfive.framework.util.ServletUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author cdfive
 */
@Slf4j
@ConditionalOnClass(Filter.class)
@ConditionalOnProperty(name = "cdfive.framework.slfMdcTraceId", havingValue = "true", matchIfMissing = true)
@Component
public class SlfMdcTraceIdFilter implements Filter {

    private static final String TRACE_ID = "_trace_id";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (!(request instanceof HttpServletRequest)) {
            chain.doFilter(request, response);
            return;
        }

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        String traceId = ServletUtil.getRequestTraceId(httpServletRequest);

        try {
            MDC.put(TRACE_ID, traceId);
            chain.doFilter(request, response);
        } finally {
            MDC.clear();
        }
    }

    @Override
    public void destroy() {

    }
}

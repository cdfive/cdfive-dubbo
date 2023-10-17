package com.cdfive.framework.servlet.filter;

import com.cdfive.framework.listener.event.LogRequestEvent;
import com.cdfive.framework.servlet.ReadBodyHttpServletRequestWrapper;
import com.cdfive.framework.util.ServletUtil;
import com.cdfive.framework.util.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Set;

/**
 * @author cdfive
 */
@Slf4j
@ConditionalOnProperty(name = "cdfive.framework.logRequest", havingValue = "true", matchIfMissing = true)
@Component
public class LogRequestFilter implements Filter {

    @Autowired
    private ErrorAttributes errorAttributes;

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
        httpServletRequest = wrapServletRequest(httpServletRequest);

        long startTime = System.currentTimeMillis();
        ServletUtil.setRequestStartTime(httpServletRequest, startTime);

        String traceId = ServletUtil.getTraceId(httpServletRequest);
        ServletUtil.setRequestTraceId(httpServletRequest, traceId);

        Throwable ex = null;
        try {
            chain.doFilter(httpServletRequest, response);
        } catch (Throwable e) {
            log.error(traceId + ",LogRequestFilter internal error", e);
            ex = e;
            throw e;
        } finally {
            long costTimeMs = System.currentTimeMillis() - startTime;
            ServletUtil.setRequestCostTimeMs(httpServletRequest, costTimeMs);

            if (ex == null) {
                ex = errorAttributes.getError(new ServletWebRequest(httpServletRequest));
            }

            if (ex != null) {
                log.error(traceId + ",requestUri={},\nremoteAddr={},\ncost={}ms,\nbody={}"
                        , httpServletRequest.getRequestURI()
                        , httpServletRequest.getRemoteAddr()
                        , costTimeMs
                        , ServletUtil.getRequestBody(httpServletRequest)
                        , ex);
            }

            SpringContextUtil.publishEvent(new LogRequestEvent(this, httpServletRequest, ex));
        }
    }

    @Override
    public void destroy() {

    }

    private HttpServletRequest wrapServletRequest(HttpServletRequest httpServletRequest) throws IOException {
        if (httpServletRequest.getContentType() != null && httpServletRequest.getContentType().toLowerCase().startsWith("multipart/form-data")) {
            return httpServletRequest;
        }

//        String body1 = StreamUtils.copyToString(httpServletRequest.getInputStream(), Charset.defaultCharset());
//        log.info("body1={}", body1);

        // !!! important
        // at least read once
        Map<String, String[]> parameterMap = httpServletRequest.getParameterMap();

//        Set<Map.Entry<String, String[]>> entries = parameterMap.entrySet();
//        log.info("parameterMap=>");
//        for (Map.Entry<String, String[]> entry : entries) {
//            log.info(entry.getKey() + "=>" + String.join(",", entry.getValue()));
//        }
//
//        String body2 = StreamUtils.copyToString(httpServletRequest.getInputStream(), Charset.defaultCharset());
//        log.info("body2={}", body2);

        return new ReadBodyHttpServletRequestWrapper(httpServletRequest);
    }
}

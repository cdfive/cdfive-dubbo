package com.cdfive.common.servlet.filter;

import com.cdfive.common.message.producer.AppRestApiProducer;
import com.cdfive.common.properties.AppProperties;
import com.cdfive.common.util.CommonUtil;
import com.cdfive.common.vo.AppRestApiLogContextVo;
import com.google.common.base.Charsets;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;

/**
 * @author cdfive
 */
@Slf4j
@Component
public class AppRestApiLogFilter implements Filter, InitializingBean {

    private static final String ATTRIBUTE_TRACE_ID = "_traceId";

    @Autowired
    private AppProperties appProperties;

    @Autowired
    private AppRestApiProducer appRestApiProducer;

    @Autowired
    private ErrorAttributes errorAttributes;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (!(request instanceof HttpServletRequest)) {
            chain.doFilter(request, response);
            return;
        }
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        long startTime = System.currentTimeMillis();

        String traceId = CommonUtil.getTraceId();
        request.setAttribute(ATTRIBUTE_TRACE_ID, traceId);

        Throwable ex = null;
        try {
            httpServletRequest = wrapServletRequest(httpServletRequest);
            chain.doFilter(httpServletRequest, response);
        } catch (Exception e) {
            ex = e;
            throw e;
        } finally {
            long costTimeMs = System.currentTimeMillis() - startTime;

            if (ex == null) {
                ex = errorAttributes.getError(new ServletWebRequest(httpServletRequest));
            }

            log(traceId, httpServletRequest, ex, costTimeMs);

            AppRestApiLogContextVo logContextVo = buildAppRestApiContextVo(traceId, httpServletRequest, ex, costTimeMs);
            appRestApiProducer.send(logContextVo);
        }
    }

    private AppRestApiLogContextVo buildAppRestApiContextVo(String traceId, HttpServletRequest request, Throwable ex, long costTimeMs) throws IOException {
        AppRestApiLogContextVo logContextVo = new AppRestApiLogContextVo();
        logContextVo.setTraceId(traceId);

        logContextVo.setAppName(appProperties.getAppName());
        // TODO
//        apiContextVo.setAppIp();
        logContextVo.setAppPort(appProperties.getServerPort());

        logContextVo.setRequestUri(request.getRequestURI());
        logContextVo.setRemoteAddr(request.getRemoteAddr());

        logContextVo.setCostMs(costTimeMs);

        logContextVo.setRequestBody(getRequestBody(request));

        if (ex != null) {
            logContextVo.setExClassName(ex.getClass().getName());
            logContextVo.setExStackTrace(Throwables.getStackTraceAsString(ex));
        }

        logContextVo.setCreateTime(new Date());
        return logContextVo;
    }

    private void log(String traceId, HttpServletRequest request, Throwable ex, long costTimeMs) throws IOException {
        if (ex == null) {
            log.info("\ntraceId={},\nrequestUri={},\nremoteAddr={},\ncost={}ms,\nbody={}"
                    , traceId
                    , request.getRequestURI()
                    , request.getRemoteAddr()
                    , costTimeMs
                    , getRequestBody(request));
        } else {
            log.error("\ntraceId={},\nrequestUri={},\nremoteAddr={},\ncost={}ms,\nbody={}"
                    , traceId
                    , request.getRequestURI()
                    , request.getRemoteAddr()
                    , costTimeMs
                    , getRequestBody(request)
                    , ex);
        }
    }

    private String getRequestBody(HttpServletRequest httpServletRequest) throws IOException {
        return StreamUtils.copyToString(httpServletRequest.getInputStream(), Charsets.UTF_8);
    }

    private HttpServletRequest wrapServletRequest(HttpServletRequest httpServletRequest) throws IOException {
//        if (!"POST".equals(httpServletRequest.getMethod())) {
//            return httpServletRequest;
//        }

//        if (httpServletRequest.getContentType() == null) {
//            return httpServletRequest;
//        }

        if (httpServletRequest.getContentType() != null && httpServletRequest.getContentType().toLowerCase().startsWith("multipart/form-data")) {
            return httpServletRequest;
        }

        return new HttpServletRequestReplacedFilter.BodyReaderHttpServletRequestWrapper((HttpServletRequest) httpServletRequest);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("AppRestApiLogFilter initialized");
    }
}

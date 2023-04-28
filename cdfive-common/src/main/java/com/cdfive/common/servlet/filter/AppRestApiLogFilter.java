//package com.cdfive.common.servlet.filter;
//
//import com.cdfive.common.message.producer.AppRestApiProducer;
//import com.cdfive.common.properties.AppProperties;
//import com.cdfive.common.util.CommonUtil;
//import com.cdfive.common.vo.AppRestApiLogContextVo;
//import com.google.common.base.Charsets;
//import com.google.common.base.Throwables;
//import lombok.extern.slf4j.Slf4j;
//import org.slf4j.MDC;
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
//import org.springframework.boot.web.servlet.error.ErrorAttributes;
//import org.springframework.stereotype.Component;
//import org.springframework.util.StreamUtils;
//import org.springframework.util.StringUtils;
//import org.springframework.web.context.request.ServletWebRequest;
//
//import javax.servlet.*;
//import javax.servlet.http.HttpServletRequest;
//import java.io.IOException;
//import java.sql.Timestamp;
//import java.util.Date;
//
///**
// * @author cdfive
// */
//@Slf4j
//@ConditionalOnBean(AppRestApiProducer.class)
//@Component
//public class AppRestApiLogFilter implements Filter, InitializingBean {
//
//    private static final String TRACE_ID = "_traceId";
//
//    @Value("${appRestApiLog.logSuccessRequest:false}")
//    private boolean logSuccessRequest;
//
//    @Value("${appRestApiLog.logSuccessRequest:true}")
//    private boolean logErrorRequest;
//
//    @Autowired
//    private AppProperties appProperties;
//
//    @Autowired
//    private AppRestApiProducer appRestApiProducer;
//
//    @Autowired
//    private ErrorAttributes errorAttributes;
//
//
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//
//    }
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        if (!(request instanceof HttpServletRequest)) {
//            chain.doFilter(request, response);
//            return;
//        }
//        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
//
//        long startTime = System.currentTimeMillis();
//
//        String traceId = getTraceId(httpServletRequest);
//        request.setAttribute(TRACE_ID, traceId);
//
//        try {
//            MDC.put(TRACE_ID, traceId);
//            Throwable ex = null;
//            try {
//                // TODO log start of this inovke
//                httpServletRequest = wrapServletRequest(httpServletRequest);
//                chain.doFilter(httpServletRequest, response);
//            } catch (Exception e) {
//                log.error(traceId + ",AppRestApiLogFilter internal error", e);
//                ex = e;
//                throw e;
//            } finally {
//                long costTimeMs = System.currentTimeMillis() - startTime;
//
//                if (ex == null) {
//                    ex = errorAttributes.getError(new ServletWebRequest(httpServletRequest));
//                }
//
//                log(traceId, httpServletRequest, ex, costTimeMs);
//
//                AppRestApiLogContextVo logContextVo = buildAppRestApiContextVo(traceId, startTime, httpServletRequest, ex, costTimeMs);
//                appRestApiProducer.send(logContextVo);
//            }
//        } finally {
//            MDC.clear();
//        }
//    }
//
//    @Override
//    public void destroy() {
//
//    }
//
//    private String getTraceId(HttpServletRequest httpServletRequest) {
//        String traceId = httpServletRequest.getHeader(TRACE_ID);
//        if (!StringUtils.isEmpty(traceId)) {
//            return traceId;
//        }
//
//        return CommonUtil.getTraceId();
//    }
//
//    private AppRestApiLogContextVo buildAppRestApiContextVo(String traceId, long startTime, HttpServletRequest httpServletRequest, Throwable ex, long costTimeMs) throws IOException {
//        AppRestApiLogContextVo logContextVo = new AppRestApiLogContextVo();
//        logContextVo.setTraceId(traceId);
//
//        logContextVo.setAppName(appProperties.getAppName());
//        // TODO store app ip?
////        apiContextVo.setAppIp();
//        logContextVo.setAppPort(appProperties.getServerPort());
//
//        logContextVo.setRequestUri(httpServletRequest.getRequestURI());
//        logContextVo.setRemoteAddr(httpServletRequest.getRemoteAddr());
//
//        logContextVo.setCostMs(costTimeMs);
//
//        logContextVo.setRequestBody(getRequestBody(httpServletRequest));
//
//        if (ex != null) {
//            logContextVo.setExClassName(ex.getClass().getName());
//            logContextVo.setExStackTrace(Throwables.getStackTraceAsString(ex));
//        }
//
//        logContextVo.setStartTime(new Date(startTime));
//        logContextVo.setCreateTime(new Date());
//        return logContextVo;
//    }
//
//    private void log(String traceId, HttpServletRequest httpServletRequest, Throwable ex, long costTimeMs) throws IOException {
//        if (ex == null) {
//            log.info("\nrequestUri={},\nremoteAddr={},\ncost={}ms,\nbody={}"
//                    , httpServletRequest.getRequestURI()
//                    , httpServletRequest.getRemoteAddr()
//                    , costTimeMs
//                    , getRequestBody(httpServletRequest));
//        } else {
//            log.error("\nrequestUri={},\nremoteAddr={},\ncost={}ms,\nbody={}"
//                    , httpServletRequest.getRequestURI()
//                    , httpServletRequest.getRemoteAddr()
//                    , costTimeMs
//                    , getRequestBody(httpServletRequest)
//                    , ex);
//        }
//    }
//
//    private String getRequestBody(HttpServletRequest httpServletRequest) throws IOException {
//        // TODO instanceof BodyReaderHttpServletRequestWrapper
//        return StreamUtils.copyToString(httpServletRequest.getInputStream(), Charsets.UTF_8);
//    }
//
//    private HttpServletRequest wrapServletRequest(HttpServletRequest httpServletRequest) throws IOException {
////        if (!"POST".equals(httpServletRequest.getMethod())) {
////            return httpServletRequest;
////        }
//
////        if (httpServletRequest.getContentType() == null) {
////            return httpServletRequest;
////        }
//
//        if (httpServletRequest.getContentType() != null && httpServletRequest.getContentType().toLowerCase().startsWith("multipart/form-data")) {
//            return httpServletRequest;
//        }
//
//        // !!! important
//        httpServletRequest.getParameterMap();
//
//        return new HttpServletRequestReplacedFilter.BodyReaderHttpServletRequestWrapper(httpServletRequest);
//    }
//
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        log.info("AppRestApiLogFilter initialized");
//    }
//}

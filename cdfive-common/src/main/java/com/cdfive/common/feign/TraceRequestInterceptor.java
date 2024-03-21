//package com.cdfive.common.feign;
//
//import com.cdfive.common.util.WebUtil;
//import feign.RequestInterceptor;
//import feign.RequestTemplate;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
//import org.springframework.stereotype.Component;
//
///**
// * @author cdfive
// */
//@ConditionalOnClass(RequestInterceptor.class)
//@Component
//public class TraceRequestInterceptor implements RequestInterceptor {
//
//    private static final String TRACE_ID = "_traceId";
//
//    @Override
//    public void apply(RequestTemplate template) {
//        if (WebUtil.getCurrentHttpServletRequest().getAttribute(TRACE_ID) == null) {
//            return;
//        }
//
//        String traceId = WebUtil.getCurrentHttpServletRequest().getAttribute(TRACE_ID).toString();
//        template.header(TRACE_ID, traceId);
//    }
//}

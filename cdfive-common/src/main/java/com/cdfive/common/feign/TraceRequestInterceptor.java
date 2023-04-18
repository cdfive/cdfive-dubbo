package com.cdfive.common.feign;

import com.cdfive.common.util.WebUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Component;

/**
 * @author xiejihan
 * @date 2023-04-14
 */
@ConditionalOnClass(RequestInterceptor.class)
@Component
public class TraceRequestInterceptor implements RequestInterceptor {

    private static final String TRACE_ID = "_traceId";

    @Override
    public void apply(RequestTemplate template) {
        String traceId = WebUtil.getCurrentHttpServletRequest().getAttribute(TRACE_ID).toString();
        template.header(TRACE_ID, traceId);
    }
}

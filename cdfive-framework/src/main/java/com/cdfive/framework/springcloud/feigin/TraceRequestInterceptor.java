package com.cdfive.framework.springcloud.feigin;

import com.cdfive.framework.util.ServletUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Component;

/**
 * @author cdfive
 */
@Slf4j
@ConditionalOnClass(RequestInterceptor.class)
@Component
public class TraceRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        String traceId = ServletUtil.getRequestTraceId(ServletUtil.getCurrentHttpServletRequest());
        if (traceId == null) {
            return;
        }

        template.header(ServletUtil.TRACE_ID, traceId);
    }
}

package com.cdfive.common.spring.webmvc.exception;

import com.cdfive.common.exception.ServiceException;
import com.cdfive.common.properties.AppProperties;
import com.cdfive.common.spring.webmvc.RecordStartTimeInterceptor;
import com.cdfive.common.spring.webmvc.RequestResponseBodyMethodProcessorWrapper;
import com.cdfive.common.util.CommonUtil;
import com.cdfive.common.vo.AppRestApiContextVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.internal.Throwables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author cdfive
 */
@Slf4j
@RefreshScope
@Component
public class ServiceHandlerExceptionResolver implements HandlerExceptionResolver {

    private static final String LOG_PREFIX = "[ServiceError]";

    @Value("${biz.error.code:500}")
    private Integer errorCode;

    @Value("${biz.error.msg:服务繁忙，请稍候再试}")
    private String errorMsg;

    @Autowired
    private AppProperties appProperties;

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
//    public ResponseEntity<Map<String, Object>> resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        String traceId = CommonUtil.getTraceId();
        log.error(LOG_PREFIX + "{},requestUri={},remoteAddr={},cost={}ms,body={},exceptionClass={}"
                , traceId
                , request.getRequestURI()
                , request.getRemoteAddr()
                , RecordStartTimeInterceptor.getRequestCostMs()
                , RequestResponseBodyMethodProcessorWrapper.getRequestBody()
                , ex.getClass().getName()
                , ex);

        AppRestApiContextVo apiContextVo = buildAppRestApiContextVo(traceId, request, ex);
        // TODO

        ModelAndView mav = new ModelAndView(new MappingJackson2JsonView());
        mav.addObject("ts", System.currentTimeMillis());
        mav.addObject("traceId", traceId);
        if (ex instanceof ServiceException) {
            ServiceException se = (ServiceException) ex;
            mav.addObject("code", se.getCode());
            mav.addObject("msg", se.getDescription());
        } else {
            mav.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            mav.addObject("code", errorCode);
            mav.addObject("msg", errorMsg);
        }
        return mav;
    }

    private AppRestApiContextVo buildAppRestApiContextVo(String traceId, HttpServletRequest request, Exception ex) {
        AppRestApiContextVo apiContextVo = new AppRestApiContextVo();
        apiContextVo.setTraceId(traceId);
        apiContextVo.setAppName(appProperties.getAppName());
        apiContextVo.setServerPort(appProperties.getServerPort());
        apiContextVo.setRequestUri(request.getRequestURI());
        apiContextVo.setRemoteAddr(request.getRemoteAddr());
        apiContextVo.setCostMs(RecordStartTimeInterceptor.getRequestCostMs());
        apiContextVo.setRequestBody(RequestResponseBodyMethodProcessorWrapper.getRequestBody());
        apiContextVo.setExClassName(ex.getClass().getName());
        apiContextVo.setExStackTrace(Throwables.getStacktrace(ex));
        return apiContextVo;
    }
}

package com.cdfive.common.spring.webmvc.exception;

import com.cdfive.common.exception.ServiceException;
import com.cdfive.common.message.producer.AppRestApiProducer;
import com.cdfive.common.properties.AppProperties;
import com.cdfive.common.spring.webmvc.RecordStartTimeInterceptor;
import com.cdfive.common.spring.webmvc.RequestResponseBodyMethodProcessorWrapper;
import com.cdfive.common.util.CommonUtil;
import com.cdfive.common.vo.AppRestApiLogContextVo;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @author cdfive
 */
@Slf4j
@RefreshScope
@ConditionalOnBean(AppRestApiProducer.class)
@Component
public class ServiceHandlerExceptionResolver implements HandlerExceptionResolver {

    private static final String TRACE_ID = "_traceId";

    private static final String LOG_PREFIX = "[ServiceError]";

    @Value("${biz.error.code:500}")
    private Integer errorCode;

    @Value("${biz.error.msg:服务繁忙，请稍候再试}")
    private String errorMsg;

    @Autowired
    private AppProperties appProperties;

    @Autowired
    private AppRestApiProducer appRestApiProducer;

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
//    public ResponseEntity<Map<String, Object>> resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
//        String traceId = CommonUtil.getTraceId();
//        String traceId = request.getAttribute(TRACE_ID) != null ? request.getAttribute(TRACE_ID).toString() : CommonUtil.getTraceId();
        String traceId = getTraceId(request);
//        log.error(LOG_PREFIX + "{},requestUri={},remoteAddr={},cost={}ms,body={},exceptionClass={}"
//                , traceId
//                , request.getRequestURI()
//                , request.getRemoteAddr()
//                , RecordStartTimeInterceptor.getRequestCostMs()
//                , RequestResponseBodyMethodProcessorWrapper.getRequestBody()
//                , ex.getClass().getName()
//                , ex);

//        AppRestApiLogContextVo contextVo = buildAppRestApiContextVo(traceId, request, ex);
//        appRestApiProducer.send(contextVo);

        ModelAndView mav = new ModelAndView(new MappingJackson2JsonView());
        mav.addObject("ts", System.currentTimeMillis());
        mav.addObject("traceId", traceId);
        if (ex instanceof ServiceException) {
            ServiceException se = (ServiceException) ex;
            mav.addObject("code", se.getCode());
            mav.addObject("msg", se.getMessage());
        } else {
            mav.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            mav.addObject("code", errorCode);
            mav.addObject("msg", errorMsg);
        }
        return mav;
    }

    @Deprecated
    private AppRestApiLogContextVo buildAppRestApiContextVo(String traceId, HttpServletRequest request, Exception ex) {
        AppRestApiLogContextVo apiContextVo = new AppRestApiLogContextVo();
        apiContextVo.setTraceId(traceId);
        apiContextVo.setAppName(appProperties.getAppName());
        // TODO
//        apiContextVo.setAppIp();
        apiContextVo.setAppPort(appProperties.getServerPort());
        apiContextVo.setRequestUri(request.getRequestURI());
        apiContextVo.setRemoteAddr(request.getRemoteAddr());
        apiContextVo.setCostMs(RecordStartTimeInterceptor.getRequestCostMs());
        apiContextVo.setRequestBody(RequestResponseBodyMethodProcessorWrapper.getRequestBody());
        apiContextVo.setExClassName(ex.getClass().getName());
        apiContextVo.setExStackTrace(Throwables.getStackTraceAsString(ex));
        apiContextVo.setCreateTime(new Date());
        return apiContextVo;
    }

    private String getTraceId(HttpServletRequest request) {
        Object objTraceId = request.getAttribute(TRACE_ID);
        if (objTraceId != null) {
            return objTraceId.toString();
        }

        return CommonUtil.getTraceId();
    }
}

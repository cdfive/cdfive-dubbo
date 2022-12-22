package com.cdfive.common.spring.webmvc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

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

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
//    public ResponseEntity<Map<String, Object>> resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        log.error(LOG_PREFIX + "requestUrl={},remoteAddr={},cost={}ms,body={}"
                , request.getRequestURL()
                , request.getRemoteAddr()
                , RecordStartTimeInteceptor.getRequestCostMs()
                , RequestResponseBodyMethodProcessorWrapper.getRequestBody(), ex);

        ModelAndView mav = new ModelAndView(new MappingJackson2JsonView());
        mav.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        mav.addObject("code", errorCode);
        mav.addObject("msg", errorMsg);
        mav.addObject("ts", System.currentTimeMillis());
//        mav.addObject("timestamp", System.currentTimeMillis());
//        mav.addObject("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
//        mav.addObject("error", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
//        mav.addObject("path", request.getRequestURI());
        return mav;
    }
}

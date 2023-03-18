package com.cdfive.common.spring.webmvc.exception;

import com.cdfive.common.spring.webmvc.RecordStartTimeInterceptor;
import com.cdfive.common.spring.webmvc.RequestResponseBodyMethodProcessorWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

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
    private List<ExceptionHandler> exceptionHandlers;

    @Autowired
    private ExceptionHandler defaultExceptionHandler;

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
//    public ResponseEntity<Map<String, Object>> resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        log.error(LOG_PREFIX + "requestUrl={},remoteAddr={},cost={}ms,body={}"
                , request.getRequestURL()
                , request.getRemoteAddr()
                , RecordStartTimeInterceptor.getRequestCostMs()
                , RequestResponseBodyMethodProcessorWrapper.getRequestBody(), ex);

        /**
        ModelAndView mav = new ModelAndView(new MappingJackson2JsonView());
//        mav.addObject("ts", System.currentTimeMillis());

        if (ex instanceof ServiceException) {
            mav.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);

            ServiceException se = (ServiceException) ex;
            mav.addObject("msg", se.getMessage());
        } else if (ex instanceof FeignException) {
            mav.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);

            FeignException fe = (FeignException) ex;
//            mav.addObject("msg", fe.contentUTF8());
            mav.addAllObjects(FastJsonUtil.json2Map(fe.contentUTF8()));
//            throw fe;
        } else {
            mav.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);

            mav.addObject("code", errorCode);
            mav.addObject("msg", errorMsg);
//        mav.addObject("timestamp", System.currentTimeMillis());
//        mav.addObject("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
//        mav.addObject("error", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
//        mav.addObject("path", request.getRequestURI());
        }

        mav.addObject("ts", System.currentTimeMillis());
        return mav;*/

        ExceptionHandler exceptionHandler = this.findExceptionHandler(ex);

        ModelAndView mav = exceptionHandler.handleException(request, response, handler, ex);
        return mav;
    }

    private ExceptionHandler findExceptionHandler(Exception ex) {
        Optional<ExceptionHandler> optExceptionHandler = exceptionHandlers.stream().filter(h -> h != defaultExceptionHandler && h.supportException(ex)).findFirst();
        if (optExceptionHandler.isPresent()) {
            return optExceptionHandler.get();
        }

//        throw new RuntimeException("can't findExceptionHandler,ex=" + ex.getClass().getSimpleName());
        return defaultExceptionHandler;
    }
}

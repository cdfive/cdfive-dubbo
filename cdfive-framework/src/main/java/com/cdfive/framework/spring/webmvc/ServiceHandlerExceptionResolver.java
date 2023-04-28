package com.cdfive.framework.spring.webmvc;

import com.cdfive.framework.exception.ServiceException;
import com.cdfive.framework.properties.AppProperties;
import com.cdfive.framework.util.ServletUtil;
import lombok.extern.slf4j.Slf4j;
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

    @Value("${cdfive.framework.error.code:500}")
    private Integer errorCode;

    @Value("${cdfive.framework.error.msg:服务繁忙，请稍候再试}")
    private String errorMsg;

    @Autowired
    private AppProperties appProperties;

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        String traceId = ServletUtil.getRequestTraceId(request);

        ModelAndView mav = new ModelAndView(new MappingJackson2JsonView());
        mav.addObject("traceId", traceId);
        mav.addObject("ts", System.currentTimeMillis());
        if (ex instanceof ServiceException) {
            ServiceException se = (ServiceException) ex;
            mav.addObject("code", se.getCode());
            mav.addObject("msg", se.getMessage());
        } else {
            // TODO need status 500?
            mav.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            mav.addObject("code", errorCode);
            mav.addObject("msg", errorMsg);
        }
        return mav;
    }
}

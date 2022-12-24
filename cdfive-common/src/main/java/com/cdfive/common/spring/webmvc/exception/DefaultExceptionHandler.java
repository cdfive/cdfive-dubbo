package com.cdfive.common.spring.webmvc.exception;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author cdfive
 */
@RefreshScope
@Primary
//@Order(value = Ordered.LOWEST_PRECEDENCE)
@Component
public class DefaultExceptionHandler extends AbstractExceptionHandler<Exception> {

    @Value("${biz.error.code:500}")
    private Integer errorCode;

    @Value("${biz.error.msg:服务繁忙，请稍候再试}")
    private String errorMsg;

    @Override
    public ModelAndView handleException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        ModelAndView mav = new ModelAndView(new MappingJackson2JsonView());

        mav.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);

        mav.addObject("code", errorCode);
        mav.addObject("msg", errorMsg);

        mav.addObject("ts", System.currentTimeMillis());
        return mav;
    }
}

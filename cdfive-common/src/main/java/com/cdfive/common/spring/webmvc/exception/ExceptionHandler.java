package com.cdfive.common.spring.webmvc.exception;

import com.cdfive.common.util.GenericClassUtil;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author cdfive
 */
public interface ExceptionHandler<E extends Exception> {

    boolean supportException(Exception ex);

    ModelAndView handleException(HttpServletRequest request, HttpServletResponse response, Object handler, E ex);
}

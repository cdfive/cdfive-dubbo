package com.cdfive.common.spring.webmvc.exception;

import com.cdfive.common.exception.ServiceException;
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
//@Order(value = 1)
@Component
public class ServiceExceptionHandler extends AbstractExceptionHandler<ServiceException> {

    @Override
    public ModelAndView handleException(HttpServletRequest request, HttpServletResponse response, Object handler, ServiceException ex) {
        ModelAndView mav = new ModelAndView(new MappingJackson2JsonView());

        mav.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);

        mav.addObject("msg", ex.getMessage());

        mav.addObject("ts", System.currentTimeMillis());
        return mav;
    }
}

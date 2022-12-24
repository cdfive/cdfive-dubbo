package com.cdfive.common.spring.webmvc.exception;

import com.cdfive.common.util.FastJsonUtil;
import feign.FeignException;
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
public class FeignExceptionHandler extends AbstractExceptionHandler<FeignException> {

    @Override
    public ModelAndView handleException(HttpServletRequest request, HttpServletResponse response, Object handler, FeignException ex) {
        ModelAndView mav = new ModelAndView(new MappingJackson2JsonView());

        mav.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);

        mav.addAllObjects(FastJsonUtil.json2Map(ex.contentUTF8()));

        mav.addObject("ts", System.currentTimeMillis());
        return mav;
    }
}

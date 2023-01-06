//package com.cdfive.common.spring.webmvc.exception;
//
//import com.cdfive.es.exception.EsException;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.ModelAndView;
//import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
///**
// * @author cdfive
// */
//@Component
//public class EsExceptionHandler extends AbstractExceptionHandler<EsException> {
//    @Override
//    public ModelAndView handleException(HttpServletRequest request, HttpServletResponse response, Object handler, EsException ex) {
//        ModelAndView mav = new ModelAndView(new MappingJackson2JsonView());
//
//        mav.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
//
//        mav.addObject("msg", ex.getMessage());
//
//        mav.addObject("ts", System.currentTimeMillis());
//        return mav;
//    }
//}

//package com.cdfive.mp3.advice;
//
//import com.cdfive.common.exception.ServiceException;
//import com.cdfive.log.exception.LogServiceException;
//import lombok.extern.slf4j.Slf4j;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.springframework.core.Ordered;
//import org.springframework.core.annotation.Order;
//import org.springframework.stereotype.Component;
//
///**
// * @author cdfive
// */
//@Slf4j
//@Order(Ordered.HIGHEST_PRECEDENCE)
//@Aspect
//@Component
//public class Mp3ExceptionAdvice {
//
//    @Pointcut("execution(* com.cdfive.mp3.service.impl.*.*(..))")
//    public void pointCut(){}
//
//    @Around("pointCut()")
//    public Object handlerPointCut(ProceedingJoinPoint pjp) throws Throwable {
//        Object result;
//        try {
//            result = pjp.proceed();
//        } catch (Throwable t) {
//            handleException(t);
//            throw t;
//        }
//
//        return result;
//    }
//
//    private void handleException(Throwable t) {
//        if (t instanceof LogServiceException) {
//            log.error("[LogServiceException]", t);
//        } else if (t instanceof ServiceException) {
//            log.error("[ServiceException]", t);
//        } else {
//            log.error("[UnknownException]", t);
//        }
//    }
//}

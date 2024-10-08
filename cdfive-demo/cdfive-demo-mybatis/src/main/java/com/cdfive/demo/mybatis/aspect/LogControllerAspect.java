package com.cdfive.demo.mybatis.aspect;

import com.cdfive.demo.mybatis.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author cdfive
 */
@Slf4j
@Aspect
@Component
public class LogControllerAspect {

    @Pointcut("@within(org.springframework.web.bind.annotation.RestController) || @within(org.springframework.stereotype.Controller)")
    public void controller() {

    }

    @Around("controller()")
    public Object logController(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object[] args = joinPoint.getArgs();
        log.info("logController start,invoke={},reqVo={}", getInokeInfo(joinPoint), JsonUtil.controllerArgsToStr(args));
        try {
            Object result = joinPoint.proceed();
            log.info("logController success,cost={}ms,invoke={},reqVo={},respVo={}"
                    , (System.currentTimeMillis() - start), getInokeInfo(joinPoint), JsonUtil.controllerArgsToStr(args), JsonUtil.objToStr(result));
            return result;
        } catch (Throwable e) {
            log.error("logController error,cost={}ms,invoke={},reqVo={}"
                    , (System.currentTimeMillis() - start), getInokeInfo(joinPoint), JsonUtil.controllerArgsToStr(args), e);
            throw e;
        }
    }

    private String getInokeInfo(ProceedingJoinPoint joinPoint) {
        return joinPoint.getSignature().toShortString();
    }
}

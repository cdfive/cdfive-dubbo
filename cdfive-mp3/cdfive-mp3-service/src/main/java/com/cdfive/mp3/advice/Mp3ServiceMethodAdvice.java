package com.cdfive.mp3.advice;

import com.cdfive.common.exception.ServiceException;
import com.cdfive.common.util.HostNameUtil;
import com.cdfive.common.util.JsonUtil;
import com.cdfive.log.vo.AddMethodLogVo;
import com.cdfive.mp3.exception.Mp3ServiceException;
import com.cdfive.mp3.message.producer.MethodLogProducer;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Date;

/**
 * @author cdfive
 */
@Slf4j
@Aspect
@Component
public class Mp3ServiceMethodAdvice {

    @Autowired
    private MethodLogProducer methodLogProducer;

    @Pointcut("execution(* com.cdfive.mp3.service.impl.*.*(..))")
    public void pointCut(){

    }

    @Around("pointCut()")
    public Object handlerPointCut(ProceedingJoinPoint pjp) throws Throwable {
        long startTime = System.currentTimeMillis();

        AddMethodLogVo addMethodLogVo = new AddMethodLogVo();
        addMethodLogVo.setStartTime(new Date(startTime));

        Object result;
        try {
            addMethodLogVo.setIp(HostNameUtil.getIp());

            Signature signature = pjp.getSignature();
            MethodSignature methodSignature = (MethodSignature) signature;
            addMethodLogVo.setMethodName(getMethodName(methodSignature));

            Object[] args = pjp.getArgs();
            addMethodLogVo.setRequestJson(JsonUtil.toJson(args));

            result = pjp.proceed();

            addMethodLogVo.setResponseJson(JsonUtil.toJson(result));
            addMethodLogVo.setSuccess(true);
            long endTime = System.currentTimeMillis();
            addMethodLogVo.setEndTime(new Date(endTime));
            addMethodLogVo.setTimeCostMs(endTime - startTime);
            methodLogProducer.send(addMethodLogVo);
        } catch (Throwable t) {
            addMethodLogVo.setSuccess(false);
            addMethodLogVo.setExceptionMessage(t == null ? null : t.getMessage());
            addMethodLogVo.setExceptionStackTrace(Throwables.getStackTraceAsString(t));
            long endTime = System.currentTimeMillis();
            addMethodLogVo.setEndTime(new Date(endTime));
            addMethodLogVo.setTimeCostMs(endTime - startTime);
            methodLogProducer.send(addMethodLogVo);
            handleException(t);
            throw t;
        }

        return result;
    }

    private String getMethodName(MethodSignature methodSignature) {
        StringBuilder sb = new StringBuilder();
        sb.append(methodSignature.getReturnType().getSimpleName());
        sb.append(" ").append(methodSignature.getName());
        sb.append("(");
        Class[] parameterTypes = methodSignature.getParameterTypes();
        for (int i = 0; i < parameterTypes.length; i++) {
            if (i > 0) {
                sb.append(" ");
            }
            sb.append(parameterTypes[i].getSimpleName());
        }
        sb.append(")");
        return sb.toString();
    }

    private void handleException(Throwable t) {
        if (t instanceof Mp3ServiceException) {
            log.error("[Mp3ServiceException]", t);
        } else if (t instanceof ServiceException) {
            log.error("[ServiceException]", t);
        } else {
            log.error("[UnknownException]", t);
        }
    }
}

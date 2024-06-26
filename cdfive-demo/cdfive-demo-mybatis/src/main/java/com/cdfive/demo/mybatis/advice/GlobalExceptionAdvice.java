package com.cdfive.demo.mybatis.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author cdfive
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionAdvice {

    @ExceptionHandler(value = Exception.class)
    public Map<String, Object> exceptionHandler(HttpServletRequest request, Exception ex) {
        log.error("GlobalError,uri={}", request.getRequestURI(), ex);

        HashMap<String, Object> map = new HashMap<>(4);
        map.put("ts", System.currentTimeMillis());
        map.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
        map.put("msg", ex != null ? ex.getMessage() : null);
        return map;
    }
}

package com.suntao.controller.advice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.suntao.http.RestResult;

@RestControllerAdvice(basePackages = "com.suntao.controller")
public class GlobalExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler
    public RestResult<?> handleException(Exception ex) throws Exception {
        logger.error("", ex);
        return new RestResult<>(-1, ex.getMessage());
    }
}

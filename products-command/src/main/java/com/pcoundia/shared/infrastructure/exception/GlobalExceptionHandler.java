package com.pcoundia.shared.infrastructure.exception;

import org.aspectj.lang.annotation.Aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @AfterThrowing(pointcut = "execution(* com.pcoundia..*(..))", throwing = "ex")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable ex) {
        LOGGER.error("Exception in {}.{}() with cause = {}",
            joinPoint.getSignature().getDeclaringTypeName(),
            joinPoint.getSignature().getName(),
            ex.getCause() != null ? ex.getCause() : "NULL", ex);
    }
}

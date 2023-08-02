package com.teamsix.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;

//test AOP
@Aspect
@Component
public class ControllerLoggingAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Before("execution(* com.teamsix.controller..*(..))")
    public void logBeforeControllerCall(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String userIp = getUserIp();
        logger.info("Calling controller method: " + methodName + ", User IP: " + userIp);
    }

    @After("execution(* com.teamsix.controller..*(..))")
    public void logAfterControllerCall(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        logger.info("Controller method " + methodName + " called successfully.");
    }

    private String getUserIp() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        return request.getRemoteAddr();
    }
}

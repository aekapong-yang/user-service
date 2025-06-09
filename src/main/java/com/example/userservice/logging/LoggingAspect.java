package com.example.userservice.logging;

import com.example.userservice.context.MethodExecutionContext;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LoggingAspect {
    @Pointcut("execution(* com.example.userservice.controller..*(..))")
    public void controllerMethod() {
    }

    @Pointcut("execution(* com.example.userservice.service..*(..))")
    public void serviceMethod() {
    }

    @Before("controllerMethod() || serviceMethod()")
    public void logBeforeMethod(JoinPoint joinPoint) {
        MethodExecutionContext.setStartTime(System.currentTimeMillis());
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getSignature().getDeclaringType().getName();

        log.info("Enter method: {} in class: {}", methodName, className);
    }

    @AfterReturning(pointcut = "controllerMethod() || serviceMethod()", returning = "result")
    public void logAfterControllerMethod(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getSignature().getDeclaringType().getName();
        long startTime = MethodExecutionContext.getStartTime();
        long totalTime = System.currentTimeMillis() - MethodExecutionContext.getStartTime();

        log.info("Exit method: {} in class: {}. Duration: {} ms", methodName, className, totalTime);
    }

}


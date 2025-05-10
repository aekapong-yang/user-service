package com.example.userservice.logging;

import com.example.userservice.context.MethodExecutionContext;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
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
    public void logAfterMethod(JoinPoint joinPoint, Object result) throws JsonProcessingException {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getSignature().getDeclaringType().getName();
        long startTime = MethodExecutionContext.getStartTime();
        long totalTime = System.currentTimeMillis() - startTime;

        log.info("Exit method: {} in class: {}. Total execution time: {} ms", methodName, className, totalTime);
    }

    @AfterThrowing(pointcut = "controllerMethod()", throwing = "ex")
    public void logException(JoinPoint joinPoint, Throwable ex) {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        log.error("Exception in {}.{} with args = {}, message = {}", className, methodName, args, ex.getMessage(), ex);
    }
}


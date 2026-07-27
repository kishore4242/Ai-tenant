package com.aitenant.web_service.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Around("execution(* com.aitenant.web_service.controller.*.*(..))")
    public Object controllerLogger(ProceedingJoinPoint joinPoint) throws Throwable{
        String traceId = MDC.get("traceId");
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        Object obj = null;
        try{
            obj = joinPoint.proceed();
            log.info("TraceId: {} | ClassName: {} | MethodName: {} ",traceId,className,methodName);
        }catch (Exception e){
            log.info("TraceId: {} | ClassName: {} | MethodName: {} | Error: {}",traceId,className,methodName,e.getMessage());
            throw e;
        }
        return obj;
    }
}

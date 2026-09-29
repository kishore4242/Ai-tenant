package com.aitenant.gateway.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.slf4j.MDC;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

@Component
@EnableAspectJAutoProxy
@Slf4j
public class LoggerAspect {
    @Around("execution(* com.aitenant.gateway.service.*.*(..))")
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

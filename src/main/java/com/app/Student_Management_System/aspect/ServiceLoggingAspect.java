package com.app.Student_Management_System.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ServiceLoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(ServiceLoggingAspect.class);

    @Around("execution(* com.app.Student_Management_System.service..*(..))")
    public Object logServiceExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();

        long startTime = System.currentTimeMillis();

        logger.debug("Entering {}.{}", className, methodName);
        try{
            return joinPoint.proceed();

        }catch (Throwable ex){
            throw ex;
        }finally {
            long executionTime = System.currentTimeMillis() - startTime;
            logger.debug("Completed {}.{} in {} ms", className, methodName, executionTime);
        }
    }
}

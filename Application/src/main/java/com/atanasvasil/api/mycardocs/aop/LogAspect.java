package com.atanasvasil.api.mycardocs.aop;

import java.math.BigDecimal;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 *
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0
 */
@Aspect
@Component
public class LogAspect {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Before("execution(* com.atanasvasil.api.mycardocs.controllers.*.*(..))")
    public void methodBegin(JoinPoint joinPoint) {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        log.debug(className + " -> " + methodName + " begin --");
    }

    @Around("execution(* com.atanasvasil.api.mycardocs.controllers.*.*(..))")
    public Object logExecTime(ProceedingJoinPoint pjp) throws Throwable {
        String className = pjp.getTarget().getClass().getSimpleName();
        String methodName = pjp.getSignature().getName();

        long startTime = System.nanoTime();
        Object output = pjp.proceed();
        BigDecimal elapsedTimeMillis = new BigDecimal(System.nanoTime() - startTime).divide(new BigDecimal(1000000));

        String result = className + " -> " + methodName + " ended in ";

        if (elapsedTimeMillis.doubleValue() < 1000) {
            result += elapsedTimeMillis + " millis";
        } else {
            result += elapsedTimeMillis.divide(new BigDecimal(1000)) + " seconds";
        }

        result += " --";
        log.debug(result);

        return output;
    }
}

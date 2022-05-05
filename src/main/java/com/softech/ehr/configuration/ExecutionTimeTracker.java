package com.softech.ehr.configuration;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class ExecutionTimeTracker {

    @Around("@annotation(com.softech.ehr.configuration.LogExecutionTime)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint)
        throws Throwable {
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Object proceed = joinPoint.proceed();
        stopWatch.stop();
        log.info("\"{}\" executed in {} ms", joinPoint.getSignature(),
            stopWatch.getTotalTimeMillis());
        return proceed;
    }


}

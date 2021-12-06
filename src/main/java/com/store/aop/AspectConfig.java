package com.store.aop;

import com.store.exceptions.RequestException;
import com.store.exceptions.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.server.ResponseStatusException;

@Aspect
@Configuration
@Slf4j
public class AspectConfig {

    @Before(value = "execution(* com.store.web.controllers.*.*(..))")
    public void logStatementBefore(JoinPoint joinPoint) {
        log.info("Execute => {}", joinPoint);
    }

    @After(value = "execution(* com.store.web.controllers.*.*(..))")
    public void logStatementAfter(JoinPoint joinPoint) {
        log.info("Complete execution => {}", joinPoint);
    }

    @Around(value = "execution(* com.store.services.impl.*.*(..))")
    public Object requestHandler(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            return joinPoint.proceed();
        } catch (RequestException e) {
            log.info("RequestException StatusCode {}", e.getStatus().value());
            log.info("RequestException Message {}", e.getMessage());
            throw new ResponseStatusException(e.getStatus(), e.getMessage());
        } catch (ResourceNotFoundException e){
            log.info("ResourceNotFoundException StatusCode {}", e.getStatus().value());
            log.info("ResourceNotFoundException Message {}", e.getMessage());
            throw new ResponseStatusException(e.getStatus(), e.getMessage());
        }
    }

}

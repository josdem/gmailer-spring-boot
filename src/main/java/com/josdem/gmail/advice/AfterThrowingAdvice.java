package com.josdem.gmail.advice;

import com.josdem.gmail.exception.BusinessException;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AfterThrowingAdvice {
    @AfterThrowing(pointcut = "execution(* com.josdem.gmail.service.impl.*(..))", throwing = "ex")
    public void afterThrowing(RuntimeException ex) {
        throw new BusinessException(ex.getMessage(), ex);
    }
}

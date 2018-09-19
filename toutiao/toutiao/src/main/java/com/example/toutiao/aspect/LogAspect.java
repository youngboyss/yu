package com.example.toutiao.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {
    private final static Logger log= LoggerFactory.getLogger(LogAspect.class);

    @Before("execution(* com.example.toutiao.controller.IndexController.*(..))")
    public void beforeMethod(JoinPoint joinPoint) {
          log.info("before method");
          StringBuilder sb=new StringBuilder();
          for(Object arg:joinPoint.getArgs())
          {
              sb.append("arg:"+arg.toString()+"|");
          }
          log.info("before method"+sb.toString());
    }

    @After("execution(* com.example.toutiao.controller.IndexController.*(..))")
    public void afterMethod()
    {
        log.info("after method");
    }
}

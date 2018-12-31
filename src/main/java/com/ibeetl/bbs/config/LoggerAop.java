package com.ibeetl.bbs.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @author ljy
 * @date 2018/12/29
 */
@Aspect
@Component
public class LoggerAop {

    private static final Logger logger = LoggerFactory.getLogger(LoggerAop.class);

    @Pointcut("execution(* com.ibeetl.bbs.service.impl.*.*(..))")
    private void anyMethod(){}

    @Around("anyMethod()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        
        Object result = null;
        Signature methodName = point.getSignature();
        try {
            //前置通知
            logger.debug("Start: The method [" + methodName + "] begins with :" + Arrays.asList(point.getArgs()));
            //执行目标方法
            result = point.proceed();

            //返回通知
            logger.debug("End: The method [" + methodName + "] ends with :" + result);
        } catch (Throwable e) {
            //异常通知
            logger.debug("Exception: The method [" + methodName + "] occurs exception:" + e);
            throw new RuntimeException(e);
        }
        //后置通知
        logger.debug("End: The method [" + methodName + "] ends");

        return result;
    }

}

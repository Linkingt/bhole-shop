package com.bhole.shop.common.aop.aspect;

import com.bhole.shop.common.base.bean.ResultBean;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.time.LocalDateTime;

/**
 * @program: bhole-shop-common-aop
 * @description:
 * @author: joke
 * @date: 2023/5/30 15:08
 * @version: 1.0
 */
@Aspect
@Component
public class ApiAspect {

    private final Logger log = LoggerFactory.getLogger(ApiAspect.class);


    @Around("@annotation(com.bhole.shop.common.aop.annotation.BhApi)")
    public Object logServiceAccess(ProceedingJoinPoint point) throws Throwable {
        long start = System.currentTimeMillis();

        String className = point.getTarget().getClass().getName();
        String methodName = point.getSignature().getName();
        String fullMethodName = className + "." + methodName;
        Method method = ((MethodSignature) point.getSignature()).getMethod();

        StringBuilder sb = new StringBuilder();
        sb.append("\n<===================================START===================================>\n");
        sb.append("call time:>").append(LocalDateTime.now()).append("\n");
        sb.append("method name:> ").append(methodName).append("\n");
        Parameter[] parameters = method.getParameters();
        Object[] args = point.getArgs();
        sb.append("request:>");
        ObjectMapper mapper = new ObjectMapper();
        for (int i = 0; i < args.length; i++) {
            String paramName = parameters[i].getName();
            String argStr = mapper.writeValueAsString(args[i]);
            sb.append(paramName).append(":").append(argStr);
        }
        log.info(" Request Info>: {}", sb);
        Object result = point.proceed();

        long end = System.currentTimeMillis();
        long elapsedMilliseconds = end - start;

        if (result instanceof ResultBean) {
            ((ResultBean<?>) result).setElapsedMilliseconds(elapsedMilliseconds);
        }
        log.info("> {} Execution time : {} millisecond", fullMethodName, elapsedMilliseconds);
        log.info("> Results of execution: {}", mapper.writeValueAsString(result));
        return result;
    }
}

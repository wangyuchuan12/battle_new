
package com.wyc.common.interceptor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Component;
@Aspect
@Component
public class ManagerInterceptConfig {

    final static Logger logger = LoggerFactory.getLogger(ManagerInterceptConfig.class);
    @Autowired
    private AutowireCapableBeanFactory factory;



    @Around(value="execution (* com.wyc.api.*.*(..))")
    public Object common(ProceedingJoinPoint proceedingJoinPoint)throws Throwable{
        Object value = proceedingJoinPoint.proceed();

        return value;
    }
}
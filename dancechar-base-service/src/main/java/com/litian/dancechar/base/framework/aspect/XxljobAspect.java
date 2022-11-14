package com.litian.dancechar.base.framework.aspect;

import com.litian.dancechar.framework.common.trace.TraceHelper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 拦截Xxljob处理traceId
 *
 * @author tojson
 * @date 2022/7/9 06:26
 */
@Slf4j
@Aspect
@Component
public class XxljobAspect {

    @Pointcut("@annotation(com.xxl.job.core.handler.annotation.XxlJob)")
    public  void xxlJobPointcut(){
    }

    @Around("xxlJobPointcut()")
    public Object around(ProceedingJoinPoint point) throws Throwable{
        try{
            TraceHelper.getCurrentTrace();
            return point.proceed();
        } catch (Throwable throwable){
            throw throwable;
        } finally {
            TraceHelper.clearCurrentTrace();
        }
    }

}

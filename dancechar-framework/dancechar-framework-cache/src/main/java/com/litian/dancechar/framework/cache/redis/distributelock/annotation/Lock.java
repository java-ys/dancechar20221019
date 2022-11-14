package com.litian.dancechar.framework.cache.redis.distributelock.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 *
 * 分布式锁注解
 *
 * @author tojson
 * @date 2022/01/19 20:53
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Lock {

    /**
     * 生成锁的key，支持spring EL表达式
     */
    @AliasFor("value")
    String value() default "default";

    /**
     * 生成锁key的前缀
     */
    @AliasFor("keyPrefix")
    String keyPrefix() default "prefix";

    /**
     * 没获取到锁是否直接返回，默认直接返回
     */
    boolean isBlock() default false;

    /**
     * 等待加锁的时间，单位毫秒
     */
    long waitTime() default 3000;

    /**
     * 锁过期时间，不指定，默认等待方法执行结束自动释放，单位毫秒
     */
    long expireTime() default -1L;

    /**
     * 获取锁失败，抛出此定义的异常
     */
    String lockFailMsg() default "系统正在处理，请稍后";
}

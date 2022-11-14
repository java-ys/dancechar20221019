package com.litian.dancechar.framework.cache.redis.distributelock.core;

import org.redisson.api.RLock;

import java.util.concurrent.TimeUnit;

/**
 *
 * 分布式锁接口
 *
 * 1）基本用法：lock.lock();
 * 2）支持过期解锁功能,10秒钟以后自动解锁, 无需调用unlock方法手动解锁：lock.lock(10, TimeUnit.SECONDS);
 * 3）尝试加锁，最多等待3秒，上锁以后10秒自动解锁 boolean res = lock.tryLock(3, 10, TimeUnit.SECONDS);
 *
 * @author tojson
 * @date 2022/01/19 20:53
 */
public interface IDistributedLock {

    /**
     * 获取锁对象
     */
    RLock getLock(String lockKey);

    /**
     * 加锁
     */
    void lock(String lockKey);

    /**
     * 加锁+过期时间
     */
    void lock(String lockKey, long timeOut);

    /**
     * 加锁+过期时间
     */
    void lock(String lockKey, TimeUnit timeUnit, long timeOut);

    /**
     * 尝试加锁，最多等待waitTime毫秒，上锁以后leaseTime自动解锁
     */
    boolean tryLock(String lockKey, TimeUnit timeUnit, long waitTime, long leaseTime);

    /**
     * 尝试加锁，最多等待waitTime, 毫秒
     */
    boolean tryLock(String lockKey, TimeUnit timeUnit, long waitTime);

    /**
     * 解锁
     */
    void unlock(String lockKey);

    /**
     * 是否锁定状态
     */
    boolean isLocked(String lockKey);
}

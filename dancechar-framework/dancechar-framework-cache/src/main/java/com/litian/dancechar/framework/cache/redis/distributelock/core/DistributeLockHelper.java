package com.litian.dancechar.framework.cache.redis.distributelock.core;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 *
 * 分布式锁实现类
 *
 * 1）基本用法：lock.lock();
 * 2）支持过期解锁功能,10秒钟以后自动解锁, 无需调用unlock方法手动解锁：lock.lock(10, TimeUnit.SECONDS);
 * 3）尝试加锁，最多等待3秒，上锁以后10秒自动解锁 boolean res = lock.tryLock(3, 10, TimeUnit.SECONDS);
 *
 * @author tojson
 * @date 2022/01/19 20:53
 */
@Component
@Slf4j
public class DistributeLockHelper implements IDistributedLock{

    @Resource
    private RedissonClient redissonClient;

    @Override
    public RLock getLock(String lockKey) {
        return redissonClient.getLock(lockKey);
    }

    @Override
    public void lock(String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock();
    }

    @Override
    public void lock(String lockKey, long timeOut) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock(timeOut, TimeUnit.SECONDS);
    }

    @Override
    public void lock(String lockKey, TimeUnit timeUnit, long timeOut) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock(timeOut,timeUnit);
    }

    @Override
    public boolean tryLock(String lockKey, TimeUnit timeUnit, long waitTime, long leaseTime){
        RLock lock = redissonClient.getLock(lockKey);
        try{
            return lock.tryLock(waitTime, leaseTime, timeUnit);
        }catch (Exception e){
            log.error("tryLock 系统异常！lockKey：{}, waitTime:{}, leaseTime:{}", lockKey, waitTime ,leaseTime);
            return false;
        }
    }

    @Override
    public boolean tryLock(String lockKey, TimeUnit timeUnit, long waitTime) {
        RLock lock = redissonClient.getLock(lockKey);
        try{
            return lock.tryLock(waitTime, timeUnit);
        }catch (Exception e){
            log.error("tryLock 系统异常！lockKey：{}, waitTime:{}", lockKey, waitTime);
            return false;
        }
    }

    @Override
    public void unlock(String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
        // 只释放当前线程自己持有的锁
        if(lock.isHeldByCurrentThread()){
            lock.forceUnlock();
        }
    }

    @Override
    public boolean isLocked(String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
        return lock.isLocked();
    }
}

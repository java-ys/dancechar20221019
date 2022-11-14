package com.litian.dancechar.framework.cache.redis.distributelock.aspect;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.litian.dancechar.framework.cache.redis.distributelock.core.IDistributedLock;
import com.litian.dancechar.framework.cache.redis.constants.LockConstant;
import com.litian.dancechar.framework.cache.redis.distributelock.annotation.Lock;
import com.litian.dancechar.framework.common.exception.DistributeLockException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 *
 * 分布式锁切面
 *
 * @author tojson
 * @date 2022/01/23 23:10
 */
@Slf4j
@Aspect
@Component
@Order(1)
public class DistributeLockAspect {
    @Resource
    private IDistributedLock distributedLock;

    @Pointcut("@annotation(com.litian.dancechar.framework.cache.redis.distributelock.annotation.Lock)")
    private void pointcut(){
    }

    @Around("pointcut() && @annotation(lock)")
    public Object around(ProceedingJoinPoint point, Lock lock) throws Throwable{
        String key = DistributeLockAspectUtil.createLockKey(lock.value(), lock.keyPrefix(), point);
        // 非阻塞式，判断对当前key加锁是否成功，不成功直接抛出异常，比较适合管理后台保存数据重复提交的场景
        if(!lock.isBlock() && !distributedLock.tryLock(key, TimeUnit.MILLISECONDS, 0)){
            throw new DistributeLockException(StrUtil.isNotEmpty(lock.lockFailMsg())
                    ? lock.lockFailMsg() : LockConstant.OPERATE_QUICK);
        }
        // 阻塞式，没设置过期时间，判断在waitTime时间内是否加锁成功，加锁成功后，会根据业务执行的时间锁自动续期
        if (ObjectUtil.equal(lock.expireTime(), -1L)){
            if(!distributedLock.tryLock(key, TimeUnit.MILLISECONDS, lock.waitTime())){
                throw new DistributeLockException(StrUtil.isNotEmpty(lock.lockFailMsg())
                        ? lock.lockFailMsg() : LockConstant.OPERATE_QUICK);
            }
        } else{
            // 阻塞式，设置了过期时间，判断在waitTime时间内是否加锁成功，加锁成功后，锁在expireTime时间自动过期，不在自动续期
            if(!distributedLock.tryLock(key, TimeUnit.MILLISECONDS, lock.waitTime(),lock.expireTime())){
                throw new DistributeLockException(StrUtil.isNotEmpty(lock.lockFailMsg())
                        ? lock.lockFailMsg() : LockConstant.OPERATE_QUICK);
            }
        }
        log.info("加锁成功！key:{}", key);
        try{
            return point.proceed();
        }finally {
            distributedLock.unlock(key);
        }
    }


}

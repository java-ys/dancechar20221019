package com.litian.dancechar.framework.delaymsg.redis.redisson.test;

import com.litian.dancechar.framework.delaymsg.redis.redisson.RedisRedissonDelayedQueueListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 *  redisson实现延时队列案例
 *
 * @author tojson
 * @date 2022/7/9 6:30
 */
@Slf4j
// @Component
public class RedissonTestListener implements RedisRedissonDelayedQueueListener<RedissonTestVO> {
    @Override
    public void execute(RedissonTestVO redissonTestVO) {
        try{
            // todo
        }catch (Exception e){
            log.error(e.getMessage(), e);
        }
    }
}

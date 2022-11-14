package com.litian.dancechar.framework.delaymsg.redis.redisson.test;

import com.litian.dancechar.framework.delaymsg.redis.redisson.RedisRedissonDelayQueue;
import lombok.extern.slf4j.Slf4j;
import javax.annotation.Resource;


/**
 * redisson实现延时队列案例-入口
 *
 * @author tojson
 * @date 2022/7/9 6:30
 */
@Slf4j
public class RedissonTestMain {
    @Resource
    private RedisRedissonDelayQueue redisRedissonDelayQueue;

    public void createOrder() {
        RedissonTestVO redissonTestVO = new RedissonTestVO();
        redissonTestVO.setNo("123");
        redisRedissonDelayQueue.addDelayTaskWithMinutes(redissonTestVO, 2, RedissonTestListener.class);
    }
}
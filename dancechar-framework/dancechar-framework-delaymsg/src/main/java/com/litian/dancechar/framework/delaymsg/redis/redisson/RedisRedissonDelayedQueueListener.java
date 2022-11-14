package com.litian.dancechar.framework.delaymsg.redis.redisson;

/**
 * redis redisson延迟队列监听
 *
 * @author tojson
 * @date 2022/09/28 20:53
 */
public interface RedisRedissonDelayedQueueListener<T> {

    /**
     * 具体的业务执行逻辑
     */
    void execute(T t);
}

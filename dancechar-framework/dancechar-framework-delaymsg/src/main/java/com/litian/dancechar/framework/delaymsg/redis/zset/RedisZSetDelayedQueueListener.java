package com.litian.dancechar.framework.delaymsg.redis.zset;

/**
 * redis zset延迟队列监听
 *
 * @author tojson
 * @date 2022/09/28 20:53
 */
public interface RedisZSetDelayedQueueListener {

    /**
     * 具体的业务执行逻辑
     */
    void execute(String data);
}

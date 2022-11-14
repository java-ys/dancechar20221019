package com.litian.dancechar.framework.delaymsg.redis.zset;

import cn.hutool.core.convert.Convert;
import com.litian.dancechar.framework.cache.redis.util.RedisHelper;
import com.litian.dancechar.framework.delaymsg.redis.redisson.RedisRedissonDelayedQueueListener;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.concurrent.TimeUnit;


/**
 * 基于redis zset实现分布式延时队列
 *
 * @author tojson
 * @date 2022/09/28 23:53
 */
@Component
@Slf4j
public class RedisZSetDelayQueue {
    @Resource
    private RedisHelper redisHelper;

    /**
     * 添加延时任务-秒
     *
     * @param jsonData     处理的数据
     * @param delay        延时的时间戳
     * @param clazz        延时队列监听类
     */
    public <T> void addDelayTaskWithSeconds(String jsonData, long delay, Class<? extends RedisZSetDelayedQueueListener> clazz) {
        addDelayTaskWithFixDelayTime(jsonData, delay * 1000L, clazz.getName());
    }

    /**
     * 添加延时任务-分
     *
     * @param jsonData     处理的数据
     * @param delay        延时的时间戳
     * @param clazz        延时队列监听类
     */
    public <T> void addDelayTaskWithMinutes(String jsonData, long delay, Class<? extends RedisZSetDelayedQueueListener> clazz) {
        addDelayTaskWithFixDelayTime(jsonData, delay * 1000 * 60L, clazz.getName());
    }

    /**
     * 添加延时任务-时
     *
     * @param jsonData    处理的数据
     * @param delay       延时的时间戳
     * @param clazz       延时队列监听类
     */
    public <T> void addDelayTaskWithHours(String jsonData, long delay, Class<? extends RedisZSetDelayedQueueListener> clazz) {
        addDelayTaskWithFixDelayTime(jsonData, delay * 1000 * 60 * 60L, clazz.getName());
    }

    /**
     * 添加延时任务-天
     * @param jsonData    处理的数据
     * @param delay       延时的时间戳
     * @param clazz       延时队列监听类
     */
    public <T> void addDelayTaskWithDays(String jsonData, long delay, Class<? extends RedisZSetDelayedQueueListener> clazz) {
        addDelayTaskWithFixDelayTime(jsonData, delay * 1000 * 60 * 60 * 24L, clazz.getName());
    }

    /**
     * 添加延时任务-未来的某个时间执行
     *
     * @param jsonData     处理的数据
     * @param futureTime   未来的某个时间执行(例如：明天的下午十点执行)
     * @param clazz        延时队列监听类
     */
    public <T> void addDelayTaskWithFutureTime(String jsonData, Date futureTime, Class<? extends RedisZSetDelayedQueueListener> clazz) {
        addDelayTaskWithFutureTime(jsonData, futureTime , clazz.getName());
    }

    /**
     * 添加延时任务-固定的时间以后执行任务(例如5分钟以后执行任务)
     *
     * @param jsonData     json数据
     * @param delay        延时的时间戳(毫秒)
     * @param queueName    队列的名称
     */
    public <T> void addDelayTaskWithFixDelayTime(String jsonData, long delay, String queueName) {
        log.info("添加延迟队列,监听名称:{},时间:{},内容:{}", queueName, delay,jsonData);
        long score = System.currentTimeMillis() + delay;
        redisHelper.zSetAdd(queueName, jsonData, score);
    }

    /**
     * 添加延时任务-未来的某个时间执行任务(例如：明天的下午十点执行)
     *
     * @param jsonData     json数据
     * @param futureTime   未来的某个时间执行(例如：明天的下午十点执行)
     * @param queueName    队列的名称
     */
    public <T> void addDelayTaskWithFutureTime(String jsonData, Date futureTime, String queueName) {
        log.info("添加延迟队列,监听名称:{},时间:{},内容:{}" , queueName, futureTime ,jsonData);
        long score = futureTime.getTime();
        redisHelper.zSetAdd(queueName, jsonData, score);
    }

}

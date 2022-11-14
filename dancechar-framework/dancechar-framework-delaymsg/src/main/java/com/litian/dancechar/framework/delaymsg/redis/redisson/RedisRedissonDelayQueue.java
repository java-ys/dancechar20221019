package com.litian.dancechar.framework.delaymsg.redis.redisson;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.util.Date;
import java.util.concurrent.TimeUnit;


/**
 * 基于redis redisson实现分布式延时队列
 *
 * @author tojson
 * @date 2022/09/28 23:53
 */
@Component
@Slf4j
public class RedisRedissonDelayQueue {
    @Resource
    private RedissonClient redissonClient;

    /**
     * 添加延时任务-秒
     *
     * @param t      处理的数据
     * @param delay  延时的时间戳
     * @param clazz  延时队列监听
     */
    public <T> void addDelayTaskWithSeconds(T t, long delay, Class<? extends RedisRedissonDelayedQueueListener> clazz) {
        addDelayTaskWithFixDelayTime(t, delay, TimeUnit.SECONDS, clazz.getSimpleName());
    }

    /**
     * 添加延时任务-分
     *
     * @param t      处理的数据
     * @param delay  延时的时间戳
     * @param clazz  延时队列监听
     */
    public <T> void addDelayTaskWithMinutes(T t, long delay, Class<? extends RedisRedissonDelayedQueueListener> clazz) {
        addDelayTaskWithFixDelayTime(t, delay, TimeUnit.MINUTES, clazz.getSimpleName());
    }

    /**
     * 添加延时任务-时
     *
     * @param t      处理的数据
     * @param delay  延时的时间戳
     * @param clazz  延时队列监听
     */
    public <T> void addDelayTaskWithHours(T t, long delay, Class<? extends RedisRedissonDelayedQueueListener> clazz) {
        addDelayTaskWithFixDelayTime(t, delay, TimeUnit.HOURS, clazz.getSimpleName());
    }

    /**
     * 添加延时任务-天
     *
     * @param t      处理的数据
     * @param delay  延时的时间戳
     * @param clazz  延时队列监听
     */
    public <T> void addDelayTaskWithDays(T t, long delay, Class<? extends RedisRedissonDelayedQueueListener> clazz) {
        addDelayTaskWithFixDelayTime(t, delay, TimeUnit.DAYS, clazz.getSimpleName());
    }

    /**
     * 添加延时任务-未来的某个时间执行
     *
     * @param t             DTO传输类
     * @param futureTime    未来的某个时间执行(例如：明天的下午十点执行)
     * @param clazz         延时队列监听
     */
    public <T> void addDelayTaskWithFutureTime(T t, Date futureTime, Class<? extends RedisRedissonDelayedQueueListener> clazz) {
        addDelayTaskWithFutureTime(t, futureTime , clazz.getSimpleName());
    }

    /**
     * 添加延时任务-固定的时间以后执行任务(例如5分钟以后执行任务)
     *
     * @param t            DTO传输类
     * @param delay        延时的时间戳
     * @param timeUnit     时间单位
     * @param queueName    队列的名称
     */
    private <T> void addDelayTaskWithFixDelayTime(T t, long delay, TimeUnit timeUnit, String queueName) {
        log.info("添加延迟队列,监听名称:{},时间:{},单位:{},内容:{}" , queueName, delay, timeUnit,t);
        RBlockingQueue<T> blockingFairQueue = redissonClient.getBlockingQueue(queueName);
        RDelayedQueue<T> delayedQueue = redissonClient.getDelayedQueue(blockingFairQueue);
        delayedQueue.offer(t, delay, timeUnit);
    }

    /**
     * 添加延时任务-未来的某个时间执行任务(例如：明天的下午十点执行)
     *
     * @param t            DTO传输类
     * @param futureTime   未来的某个时间执行(例如：明天的下午十点执行)
     * @param queueName    队列的名称
     */
    private <T> void addDelayTaskWithFutureTime(T t, Date futureTime, String queueName) {
        log.info("添加延迟队列,监听名称:{},时间:{},内容:{}" , queueName, futureTime ,t);
        RBlockingQueue<T> blockingFairQueue = redissonClient.getBlockingQueue(queueName);
        RDelayedQueue<T> delayedQueue = redissonClient.getDelayedQueue(blockingFairQueue);
        delayedQueue.offer(t, futureTime.getTime()-new Date().getTime(), TimeUnit.MILLISECONDS);
    }

}

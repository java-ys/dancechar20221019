package com.litian.dancechar.framework.delaymsg.jdk;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * 基于jdk DelayQueue 延时任务
 *
 * @author tojson
 * @date 2022/09/28 20:53
 */
public class JDKDelayTask<T> implements Delayed {
    /**
     * 队列名
     */
    private String queue;

    /**
     * 消息内容
     */
    private T body;

    /**
     * 创建的开始时间
     */
    private long start = System.currentTimeMillis();

    /**
     * 延迟的时长
     */
    private long delayTime;

    public JDKDelayTask(String queue, T body, long delayTime) {
        this.queue = queue;
        this.body = body;
        this.delayTime = delayTime;
    }

    /**
     * 延迟任务是否到时就是按照这个方法判断如果返回的是负数则说明到期，否则还没到期
     */
    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert((start + this.delayTime)
                - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    /**
     * 比较时间，以最接近执行时间的任务，排在最前面
     */
    @Override
    public int compareTo(Delayed delayed) {
        JDKDelayTask msg = (JDKDelayTask) delayed;
        return (int)(((start + this.delayTime) - System.currentTimeMillis())
                -((msg.start + msg.delayTime) - System.currentTimeMillis())) ;
    }

    public String getQueue() {
        return this.queue;
    }

    public T getBody() {
        return this.body;
    }
}

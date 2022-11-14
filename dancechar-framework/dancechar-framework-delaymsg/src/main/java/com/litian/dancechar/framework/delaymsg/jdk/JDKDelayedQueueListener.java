package com.litian.dancechar.framework.delaymsg.jdk;

/**
 * 基于jdk DelayQueue延迟队列监听
 *
 * @author tojson
 * @date 2022/09/28 20:53
 */
public interface JDKDelayedQueueListener<T> {

    /**
     * 具体的业务执行逻辑
     */
    void execute(T t);
}

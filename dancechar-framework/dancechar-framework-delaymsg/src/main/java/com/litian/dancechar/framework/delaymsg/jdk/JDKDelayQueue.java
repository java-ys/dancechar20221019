package com.litian.dancechar.framework.delaymsg.jdk;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.concurrent.DelayQueue;


/**
 * 基于jdk DelayQueue实现延时队列
 *
 * @author tojson
 * @date 2022/09/28 20:53
 */
@Component
@Slf4j
public class JDKDelayQueue {
    public static DelayQueue<JDKDelayTask> delayQueue = new DelayQueue<>();

    /**
     * 添加延时任务-秒
     * @param data           处理的数据
     * @param fixDelayTime   延时的时间戳
     * @param clazz          延时队列监听
     */
    public static <T> void addDelayTaskWithSeconds(T data, long fixDelayTime, Class<? extends JDKDelayedQueueListener> clazz) {
        addDelayTask(data, fixDelayTime * 1000L, clazz.getName());
    }

    /**
     * 添加延时任务-分
     * @param data           处理的数据
     * @param fixDelayTime   延时的时间戳
     * @param clazz          延时队列监听
     */
    public static <T> void addDelayTaskWithMinutes(T data, long fixDelayTime, Class<? extends JDKDelayedQueueListener> clazz) {
        addDelayTask(data, fixDelayTime * 1000 * 60L, clazz.getName());
    }

    /**
     * 添加延时任务-时
     * @param data           处理的数据
     * @param fixDelayTime   延时的时间戳
     * @param clazz          延时队列监听
     */
    public static <T> void addDelayTaskWithHours(T data, long fixDelayTime, Class<? extends JDKDelayedQueueListener> clazz) {
        addDelayTask(data, fixDelayTime * 1000 * 60 * 60L, clazz.getName());
    }

    /**
     * 添加延时任务-天
     * @param data           处理的数据
     * @param fixDelayTime   延时的时间戳
     * @param clazz          延时队列监听
     */
    public static <T> void addDelayTaskWithDays(T data, long fixDelayTime, Class<? extends JDKDelayedQueueListener> clazz) {
        addDelayTask(data, fixDelayTime * 1000 * 60 * 60 * 24L, clazz.getName());
    }

    /**
     * 添加延时任务-未来的某个时间执行
     *
     * @param data         处理的数据
     * @param futureTime   未来的某个时间执行(例如：明天的下午十点执行)
     * @param clazz          延时队列监听
     */
    public static <T> void addDelayTaskWithFutureTime(T data, Date futureTime, Class<? extends JDKDelayedQueueListener> clazz) {
        addDelayTask(data, futureTime.getTime()-new Date().getTime(), clazz.getName());
    }

    public static <T> void addDelayTask(T data, long fixDelayTime, String queue){
        if(ObjectUtil.isNull(data)){
            log.error("data不能为空");
            return;
        }
        log.info("添加延迟队列,监听名称:{},时间:{},内容:{}", queue, fixDelayTime, JSONUtil.toJsonStr(data));
        JDKDelayTask<T> jdkDelayTask = new JDKDelayTask<T>(queue, data, fixDelayTime);
        delayQueue.put(jdkDelayTask);
    }
}

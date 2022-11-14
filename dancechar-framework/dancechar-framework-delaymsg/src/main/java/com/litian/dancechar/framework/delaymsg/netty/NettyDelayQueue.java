package com.litian.dancechar.framework.delaymsg.netty;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import io.netty.util.HashedWheelTimer;
import io.netty.util.Timer;
import io.netty.util.concurrent.DefaultThreadFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 基于netty实现时间轮延时队列
 *
 * @author tojson
 * @date 2022/09/28 20:53
 */
@Slf4j
public class NettyDelayQueue {
    /**
     * 默认的HashedWheelTimer
     */
    public final static Timer HASHED_WHEEL_TIMER = new HashedWheelTimer(
                           new DefaultThreadFactory("nettyDelayMsg"),
                100L, TimeUnit.MILLISECONDS, 512, true);


    /**
     * 添加延时任务-秒
     *
     * @param timerTask    待处理的任务
     * @param fixDelayTime 延时的时间戳
     * @param <T>   泛型
     */
    public static <T> void addDelayTaskWithSeconds(AbstractNettyDelayedQueueListener<T> timerTask, long fixDelayTime) {
        addDelayTaskWithFixDelayTime(timerTask, fixDelayTime, TimeUnit.SECONDS);
    }

    /**
     * 添加延时任务-分
     *
     * @param timerTask    待处理的任务
     * @param fixDelayTime 延时的时间戳
     * @param <T>   泛型
     */
    public static <T> void addDelayTaskWithMinutes(AbstractNettyDelayedQueueListener<T> timerTask, long fixDelayTime) {
        addDelayTaskWithFixDelayTime(timerTask, fixDelayTime, TimeUnit.MINUTES);
    }

    /**
     * 添加延时任务-时
     *
     * @param timerTask    待处理的任务
     * @param fixDelayTime 延时的时间戳
     * @param <T>   泛型
     */
    public static <T> void addDelayTaskWithHours(AbstractNettyDelayedQueueListener<T> timerTask, long fixDelayTime) {
        addDelayTaskWithFixDelayTime(timerTask, fixDelayTime, TimeUnit.HOURS);
    }

    /**
     * 添加延时任务-天
     *
     * @param timerTask    待处理的任务
     * @param fixDelayTime 延时的时间戳
     * @param <T>   泛型
     */
    public static <T> void addDelayTaskWithDays(AbstractNettyDelayedQueueListener<T> timerTask, long fixDelayTime) {
        addDelayTaskWithFixDelayTime(timerTask, fixDelayTime, TimeUnit.HOURS);
    }

    /**
     * 添加延时任务-固定的时间以后执行任务(例如5分钟以后执行任务)
     * @param timerTask    待处理的任务
     * @param fixDelayTime 固定的延时时间之后执行(1、5、10、30、60...)
     * @param timeUnit     时间单位
     */
    public static <T> void addDelayTaskWithFixDelayTime(AbstractNettyDelayedQueueListener<T> timerTask,
                                                        long fixDelayTime,
                                                        TimeUnit timeUnit){
        if(ObjectUtil.isNull(timerTask)){
            log.error("timerTask不能为空");
            return;
        }
        if(fixDelayTime < 0){
            log.error("fixDelayTime不能小于0");
            return;
        }
        if(timeUnit == null){
            log.error("timeUnit不能为空");
            return;
        }
        TaskVO<T> taskVO = timerTask.getTaskVO();
        taskVO.setTimestamp(fixDelayTime);
        taskVO.setTimeUnit(timeUnit);
        log.info("添加延迟队列,时间:{},内容:{}" , fixDelayTime , JSONUtil.toJsonStr(taskVO.getData()));
        HASHED_WHEEL_TIMER.newTimeout(timerTask, fixDelayTime, timeUnit);
    }

    /**
     * 添加延时任务-未来的某个时间执行任务(例如：明天的下午十点执行)
     * @param timerTask    待处理的任务
     * @param futureTime   未来的某个时间执行(例如：明天的下午十点执行)
     */
    public static <T> void addDelayTaskWithFutureTime(AbstractNettyDelayedQueueListener<T> timerTask, Date futureTime){
        if(ObjectUtil.isNull(timerTask)){
            log.error("timerTask不能为空");
            return;
        }
        if(futureTime == null || futureTime.getTime() < new Date().getTime()){
            log.error("futureTime不能小于当前时间");
            return;
        }
        TaskVO<T> taskVO = timerTask.getTaskVO();
        taskVO.setFutureTime(futureTime);
        log.info("添加延迟队列,时间:{},内容:{}", futureTime ,JSONUtil.toJsonStr(taskVO.getData()));
        HASHED_WHEEL_TIMER.newTimeout(timerTask, (futureTime.getTime()-new Date().getTime()),
                TimeUnit.MILLISECONDS);
    }
}

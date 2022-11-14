package com.litian.dancechar.core.biz.activity.info.eventbus;

import cn.hutool.json.JSONUtil;
import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;
import com.litian.dancechar.core.biz.activity.info.dao.entity.ActInfoDO;
import com.litian.dancechar.core.biz.activity.info.delaymsg.ActAutoEndWithRedissonListener;
import com.litian.dancechar.core.biz.activity.info.delaymsg.ActAutoStartWithRedissonListener;
import com.litian.dancechar.core.biz.activity.info.service.ActInfoService;
import com.litian.dancechar.framework.cache.redis.bloomfilter.util.BloomFilerHelper;
import com.litian.dancechar.framework.delaymsg.redis.redisson.RedisRedissonDelayQueue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.EventListener;

/**
 * 活动信息发生变化异步时间监听
 *
 * @author tojson
 * @date 2022/9/18 22:13
 */
@Slf4j
@Component
public class ActInfoChangeEventBusListener implements EventListener {
    @Resource
    private ActInfoService actInfoService;
    @Resource
    private BloomFilerHelper bloomFilerHelper;
    @Resource
    private RedisRedissonDelayQueue redisRedissonDelayQueue;

    @Subscribe
    @AllowConcurrentEvents
    public void listener(ActInfoChangeEvent actInfoChangeEvent){
        log.info("开始接收到活动信息变更eventbus事件！eventId:{},eventName:{}, data:{}", actInfoChangeEvent.getEventId(),
                actInfoChangeEvent.getEventName(),
                JSONUtil.toJsonStr(actInfoChangeEvent.getActInfoDO()));
        ActInfoDO actInfoDO = actInfoChangeEvent.getActInfoDO();
        try{
            bloomFilerHelper.add(actInfoDO.getActNo());
            actInfoService.refreshRedisAndLocalCache(actInfoDO);
            // 发布活动自动开始延时任务, 如果开始时间小于当前时间，直接开始活动
            redisRedissonDelayQueue.addDelayTaskWithFutureTime(actInfoDO,
                    actInfoDO.getStartTime().getTime() < new Date().getTime() ?
                            new Date() : actInfoDO.getStartTime(),
                    ActAutoStartWithRedissonListener.class);
            // 发布活动自动结束延时任务
            redisRedissonDelayQueue.addDelayTaskWithFutureTime(actInfoDO, actInfoDO.getEndTime(),
                    ActAutoEndWithRedissonListener.class);
            log.info("活动信息变更eventbus事件监听完成！data:{}", JSONUtil.toJsonStr(actInfoDO));
        }catch (Exception e){
            log.error("活动信息变更eventbus事件监听系统异常！errMsg:{}", e.getMessage(), e);
        }
    }
}

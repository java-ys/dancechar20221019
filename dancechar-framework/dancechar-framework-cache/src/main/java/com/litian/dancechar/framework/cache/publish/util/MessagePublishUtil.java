package com.litian.dancechar.framework.cache.publish.util;

import com.litian.dancechar.framework.cache.redis.util.RedisHelper;
import com.litian.dancechar.framework.common.util.DCJSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 *
 * 广播消息工具类（采用redis发布订阅实现）
 *
 * @author tojson
 * @date 2022/09/18 08:20
 */
@Slf4j
@Component
public class MessagePublishUtil {

    @Resource
    private RedisHelper redisHelper;

    /**
     * 广播消息
     * @param channel 消息频道
     * @param value  消息value
     */
    public <T> void sendMsg(String channel, T value){
        log.info("采用redis发布订阅广播消息,channel:{},value:{}", channel, DCJSONUtil.toJsonStr(value));
        redisHelper.getRedisTemplate().convertAndSend(channel ,value instanceof String ? value
                : DCJSONUtil.toJsonStr(value));
    }

}

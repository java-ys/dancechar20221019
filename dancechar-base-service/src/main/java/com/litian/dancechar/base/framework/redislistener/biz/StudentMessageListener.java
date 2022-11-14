package com.litian.dancechar.base.framework.redislistener.biz;

import com.litian.dancechar.base.framework.redislistener.common.RedisChannelEnum;
import com.litian.dancechar.framework.cache.redis.util.RedisHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Component
public class StudentMessageListener implements MessageListener {
    @Resource
    private RedisHelper redisHelper;

    public StudentMessageListener(RedisMessageListenerContainer listenerContainer) {
        listenerContainer.addMessageListener(this, new ChannelTopic(RedisChannelEnum.EXAMPLE_CHANNEL.getCode()));
    }

    @Override
    public void onMessage(Message message, byte[] bytes) {
        Object channel = redisHelper.getRedisTemplate().getStringSerializer().deserialize(message.getChannel());
        Object data = redisHelper.getRedisTemplate().getValueSerializer().deserialize(message.getBody());
        log.info("订阅频道：{},接收数据：{}", channel.toString(), data.toString());
    }
}

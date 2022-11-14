package com.litian.dancechar.core.biz.activity.info.messagesubscribe;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.litian.dancechar.core.biz.activity.info.cache.ActInfoCacheService;
import com.litian.dancechar.core.biz.activity.info.dao.entity.ActInfoDO;
import com.litian.dancechar.core.common.enums.MessageChannelEnum;
import com.litian.dancechar.framework.cache.redis.util.RedisHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 活动变更广播消息订阅(刷新本地缓存，处理各个节点本地缓存一致性)
 *
 * @author tojson
 * @date 2022/9/18 22:13
 */
@Slf4j
@Component
public class ActChangeInfoMessageSubscribeListener implements MessageListener {
    @Resource
    private ActInfoCacheService actInfoCacheService;
    @Resource
    private RedisHelper redisHelper;

    public ActChangeInfoMessageSubscribeListener(RedisMessageListenerContainer listenerContainer) {
        listenerContainer.addMessageListener(this, new ChannelTopic(MessageChannelEnum.ACT_CHANNEL.getCode()));
    }

    @Override
    public void onMessage(Message message, byte[] bytes) {
        Object data = redisHelper.getRedisTemplate().getValueSerializer().deserialize(message.getBody());
        if(ObjectUtil.isNotNull(data)){
            actInfoCacheService.refreshLocalCache(JSONUtil.toBean(data.toString(), ActInfoDO.class));
        }
        log.info("订阅活动变更信息频道：{},接收数据：{}", new String(message.getChannel()), data);
    }
}

package com.litian.dancechar.framework.delaymsg.redis.zset.test;

import com.litian.dancechar.framework.delaymsg.redis.zset.RedisZSetDelayedQueueListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * zset实现延时队列案例
 *
 * @author tojson
 * @date 2022/7/9 6:30
 */
@Slf4j
// @Component
public class ZSetTestListener implements RedisZSetDelayedQueueListener {
    @Override
    public void execute(String msg) {
        try{
            // todo
        }catch (Exception e){
            log.error(e.getMessage(), e);
        }
    }
}

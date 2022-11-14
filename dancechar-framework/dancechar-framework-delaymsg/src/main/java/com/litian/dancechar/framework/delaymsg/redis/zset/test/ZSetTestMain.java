package com.litian.dancechar.framework.delaymsg.redis.zset.test;

import cn.hutool.json.JSONUtil;
import com.litian.dancechar.framework.delaymsg.redis.zset.RedisZSetDelayQueue;
import lombok.extern.slf4j.Slf4j;
import javax.annotation.Resource;


/**
 * zset实现延时队列案例-入口
 *
 * @author tojson
 * @date 2022/7/9 6:30
 */
@Slf4j
public class ZSetTestMain {
    @Resource
    private RedisZSetDelayQueue redisZSetDelayQueue;

    public void createOrder() {
        ZSetVO zSetVO = new ZSetVO();
        zSetVO.setNo("123");
        redisZSetDelayQueue.addDelayTaskWithMinutes(JSONUtil.toJsonStr(zSetVO), 2,
                ZSetTestListener.class);

    }
}
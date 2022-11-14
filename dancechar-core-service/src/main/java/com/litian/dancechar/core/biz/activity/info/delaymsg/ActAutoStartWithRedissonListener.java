package com.litian.dancechar.core.biz.activity.info.delaymsg;

import cn.hutool.json.JSONUtil;
import com.litian.dancechar.core.biz.activity.info.dao.entity.ActInfoDO;
import com.litian.dancechar.core.biz.activity.info.enums.ActStatusEnum;
import com.litian.dancechar.core.biz.activity.info.service.ActInfoService;
import com.litian.dancechar.framework.delaymsg.redis.redisson.RedisRedissonDelayedQueueListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 监听活动自动开始
 *
 * @author tojson
 * @date 2022/10/06 6:30
 */
@Slf4j
@Component
public class ActAutoStartWithRedissonListener implements RedisRedissonDelayedQueueListener<ActInfoDO> {
    @Resource
    private ActInfoService actInfoService;

    @Override
    public void execute(ActInfoDO actInfoDO) {
        try {
            actInfoDO.setStatus(ActStatusEnum.STARTING.getCode());
            actInfoService.updateById(actInfoDO);
            actInfoService.refreshRedisAndLocalCache(actInfoDO);
            log.info("活动自动进行成功!data:{}", JSONUtil.toJsonStr(actInfoDO));
        }catch (Exception e){
            // 这里打印错误日志，用于监控告警
            log.error("活动自动进行失败！errMsg:{}", e.getMessage(), e);
        }
    }
}

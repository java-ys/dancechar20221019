package com.litian.dancechar.core.biz.activity.info.job;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONUtil;
import com.google.common.collect.Lists;
import com.litian.dancechar.core.biz.activity.info.dao.entity.ActInfoDO;
import com.litian.dancechar.core.biz.activity.info.enums.ActStatusEnum;
import com.litian.dancechar.core.biz.activity.info.service.ActInfoService;
import com.litian.dancechar.framework.cache.redis.distributelock.core.DistributeLockHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 活动自动发布或结束job(主要是兜底，防止延时任务未执行)
 *
 * @author tojson
 * @date 2022/8/21 23:25
 */
@Slf4j
@Configuration
@EnableScheduling
public class ActAutoStartOrEndSpringJob {
    private static final String ACT_AUTO_START_OR_END_LOCK = "actAutoStartOrEndLock";

    @Resource
    private DistributeLockHelper distributeLockHelper;
    @Resource
    private ActInfoService actInfoService;

    @Scheduled(cron = "0 0/10 * * * ?")
    public void autoStartOrEndAct() {
        if(!distributeLockHelper.tryLock(ACT_AUTO_START_OR_END_LOCK, TimeUnit.MINUTES, 1)){
            return;
        }
        try{
            List<Integer> statusList = Lists.newArrayList(ActStatusEnum.PUBLISH.getCode(),
                                        ActStatusEnum.STARTING.getCode());
            List<ActInfoDO> actDOList = actInfoService.findActInfoListByStatus(statusList);
            if(CollUtil.isEmpty(actDOList)){
                return;
            }
            for (ActInfoDO actInfoDO : actDOList) {
                // 检查活动开始时间是否大于等于当前时间，如果是，开启活动
                if(actInfoDO.getStartTime().getTime() >= new Date().getTime()){
                    actInfoDO.setStatus(ActStatusEnum.STARTING.getCode());
                    actInfoService.updateById(actInfoDO);
                    actInfoService.refreshRedisAndLocalCache(actInfoDO);
                    log.info("通过定时任务开始活动,data:{}", JSONUtil.toJsonStr(actInfoDO));
                }
                // 检查活动开始时间是否小于等于当前时间，如果是，结束活动
                if(actInfoDO.getEndTime().getTime() <= new Date().getTime()){
                    actInfoDO.setStatus(ActStatusEnum.END.getCode());
                    actInfoService.updateById(actInfoDO);
                    actInfoService.refreshRedisAndLocalCache(actInfoDO);
                    log.info("通过定时任务结束活动,data:{}", JSONUtil.toJsonStr(actInfoDO));
                }
            }
        } catch (Exception e){
            log.error("通过定时任务补偿开始活动或结束活动失败！errMsg：{}", e.getMessage(), e);
        } finally {
            distributeLockHelper.unlock(ACT_AUTO_START_OR_END_LOCK);
        }
    }
}

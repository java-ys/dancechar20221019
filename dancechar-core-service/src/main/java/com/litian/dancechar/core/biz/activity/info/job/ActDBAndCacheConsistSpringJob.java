package com.litian.dancechar.core.biz.activity.info.job;

import com.google.common.collect.Lists;
import com.litian.dancechar.core.biz.activity.info.dao.entity.ActInfoDO;
import com.litian.dancechar.core.biz.activity.info.enums.ActStatusEnum;
import com.litian.dancechar.core.biz.activity.info.service.ActInfoService;
import com.litian.dancechar.framework.cache.redis.bloomfilter.util.BloomFilerHelper;
import com.litian.dancechar.framework.cache.redis.distributelock.core.DistributeLockHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 活动缓存与DB一致性检查
 *
 * @author tojson
 * @date 2022/8/21 23:25
 */
@Slf4j
@Configuration
@EnableScheduling
public class ActDBAndCacheConsistSpringJob {
    private static final String ACT_DB_CACHE_CONSIST_LOCK = "actDBAndCacheConsistLock";

    @Resource
    private DistributeLockHelper distributeLockHelper;
    @Resource
    private ActInfoService actInfoService;
    @Resource
    private BloomFilerHelper bloomFilerHelper;

    @Scheduled(cron = "0 0/10 * * * ?")
    public void actDBAndCacheConsist() {
        if(!distributeLockHelper.tryLock(ACT_DB_CACHE_CONSIST_LOCK, TimeUnit.MINUTES, 1)){
            return;
        }
        try{
            List<ActInfoDO> actDOList = actInfoService.findActInfoListByStatus(
                                        Lists.newArrayList(ActStatusEnum.PUBLISH.getCode(),
                                        ActStatusEnum.STARTING.getCode(),
                                        ActStatusEnum.END.getCode()));
            actDOList.forEach(actInfoDO->{
                bloomFilerHelper.add(actInfoDO.getActNo());
                actInfoService.refreshRedisAndLocalCache(actInfoDO);
            });
            log.info("完成活动DB与缓存一致性处理");
        } catch (Exception e){
            log.error("通过定时任务补偿开始活动或结束活动失败！errMsg：{}", e.getMessage(), e);
        } finally {
            distributeLockHelper.unlock(ACT_DB_CACHE_CONSIST_LOCK);
        }
    }
}

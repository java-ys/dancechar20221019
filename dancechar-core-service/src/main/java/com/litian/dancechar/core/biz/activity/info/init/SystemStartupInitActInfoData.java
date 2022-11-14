package com.litian.dancechar.core.biz.activity.info.init;

import com.google.common.collect.Lists;
import com.litian.dancechar.core.biz.activity.info.dao.entity.ActInfoDO;
import com.litian.dancechar.core.biz.activity.info.enums.ActStatusEnum;
import com.litian.dancechar.core.biz.activity.info.service.ActInfoService;
import com.litian.dancechar.framework.cache.redis.bloomfilter.util.BloomFilerHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

/**
 * 系统启动-预热活动db数据到缓存
 *
 * @author tojson
 * @date 2022/8/21 23:25
 */
@Slf4j
@Component
public class SystemStartupInitActInfoData {
    @Resource
    private ActInfoService actInfoService;
    @Resource
    private BloomFilerHelper bloomFilerHelper;

    @PostConstruct
    public void initActData(){
        try{
            List<ActInfoDO> actDOList = actInfoService.findActInfoListByStatus(Lists.newArrayList(
                                        ActStatusEnum.PUBLISH.getCode(),
                                        ActStatusEnum.STARTING.getCode(),
                                        ActStatusEnum.END.getCode()));
            actDOList.forEach(actInfoDO->{
                bloomFilerHelper.add(actInfoDO.getActNo());
                actInfoService.refreshRedisAndLocalCache(actInfoDO);
            });
            log.info("系统启动-预热活动数据到缓存完成...");
        } catch (Exception e){
            log.error("系统启动-预热活动数据到缓存失败！errMsg：{}", e.getMessage(), e);
        }
    }
}

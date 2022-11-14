package com.litian.dancechar.core.biz.activity.info.cache;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.litian.dancechar.core.biz.activity.info.dao.entity.ActInfoDO;
import com.litian.dancechar.core.biz.activity.info.dto.ActBaseInfoDTO;
import com.litian.dancechar.core.biz.activity.info.enums.ActStatusEnum;
import com.litian.dancechar.core.biz.activity.info.service.ActInfoService;
import com.litian.dancechar.core.common.constants.CacheConstants;
import com.litian.dancechar.core.common.constants.CommConstants;
import com.litian.dancechar.framework.cache.caffeine.util.CaffeineCacheUtil;
import com.litian.dancechar.framework.cache.redis.bloomfilter.util.BloomFilerHelper;
import com.litian.dancechar.framework.cache.redis.util.RedisHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 活动信息缓存服务
 *
 * @author tojson
 * @date 2022/7/9 6:30
 */
@Component
@Slf4j
public class ActInfoCacheService {
    /**
     * JVM本地锁
     */
    private final Lock findActListFromCacheLock = new ReentrantLock();

    @Resource
    private RedisHelper redisHelper;
    @Resource
    private CaffeineCacheUtil caffeineCacheUtil;
    @Resource
    private ActInfoService actInfoService;
    @Resource
    private BloomFilerHelper bloomFilerHelper;

    /**
     * 刷新redis缓存
     */
    public void refreshRedisCache(ActInfoDO actInfoDO){
        long expireTime = actInfoDO.getEndTime().getTime()-new Date().getTime();
        // 设置redis过期时间为活动结束时间+ 2天
        if(expireTime <= 0){
            expireTime = 0;
        }
        redisHelper.set(String.format(CacheConstants.ActInfo.ACT_BASE_INFO, actInfoDO.getActNo()), JSONUtil.toJsonStr(actInfoDO), expireTime + 48 * 3600 * 1000L);
        // 更新活动列表redis缓存信息
        List<Integer> statusList = Lists.newArrayList(ActStatusEnum.PUBLISH.getCode(),
                                   ActStatusEnum.STARTING.getCode(), ActStatusEnum.END.getCode());
        List<ActInfoDO>  actInfoDOList = actInfoService.findActInfoListByStatus(statusList);
        if(CollUtil.isNotEmpty(actInfoDOList)){
            Map<String,String> redisMap = Maps.newConcurrentMap();
            for(ActInfoDO actInfo : actInfoDOList){
                redisMap.put(actInfo.getActNo(), JSONUtil.toJsonStr(actInfo));
            }
            redisHelper.setMap(CacheConstants.ActInfo.ACT_LIST, redisMap,CacheConstants.ActInfo.ACT_LIST_EXPIRE_TIME);
        }
    }

    /**
     * 刷新本地缓存
     */
    public void refreshLocalCache(ActInfoDO actInfoDO){
        caffeineCacheUtil.putCache(CacheConstants.ActInfo.CAFFEINE_ACT_INFO, actInfoDO.getActNo()
                , JSONUtil.toJsonStr(actInfoDO));
    }

    /**
     * 查询活动列表
     */
    public List<ActBaseInfoDTO> findActListFromCache(){
        List<ActInfoDO> redisActInfoDOList = redisHelper.getMapValueList(CacheConstants.ActInfo.ACT_LIST,ActInfoDO.class);
        if(CollUtil.isNotEmpty(redisActInfoDOList)) {
            List<ActBaseInfoDTO> actBaseInfoDTOList = Lists.newArrayList();
            for (ActInfoDO actInfoDO : redisActInfoDOList) {
                actBaseInfoDTOList.add(convertActInfoDOToActBaseInfoDTO(actInfoDO));
            }
            return actBaseInfoDTOList;
        }
        // 缓存查不到，从db中查询，为了防止数据库被打爆，这里加一个锁(为了保证性能，使用jvm本地锁拦截，不采用分布式锁)
        List<ActInfoDO> actInfoDOList = Lists.newArrayList();
        findActListFromCacheLock.lock();
        try{
            if(CollUtil.isNotEmpty(actInfoDOList)){
                List<ActBaseInfoDTO> actBaseInfoDTOList = Lists.newArrayList();
                for (ActInfoDO actInfoDO : actInfoDOList) {
                    actBaseInfoDTOList.add(convertActInfoDOToActBaseInfoDTO(actInfoDO));
                }
                return actBaseInfoDTOList;
            }
            actInfoDOList = actInfoService.findActInfoListByStatus(Lists.newArrayList(ActStatusEnum.PUBLISH.getCode(), ActStatusEnum.STARTING.getCode(),
                            ActStatusEnum.END.getCode()));
            return putDbListToCache(actInfoDOList);
        } finally {
            findActListFromCacheLock.unlock();
        }
    }

    /**
     * 将db列表数据放到缓存中
     */
    private List<ActBaseInfoDTO> putDbListToCache(List<ActInfoDO> actInfoDOList){
        if(CollUtil.isEmpty(actInfoDOList)){
            return Lists.newArrayList();
        }
        Map<String,String> redisMap = Maps.newConcurrentMap();
        for(ActInfoDO actInfo : actInfoDOList){
            redisMap.put(actInfo.getActNo(), JSONUtil.toJsonStr(actInfo));
        }
        redisHelper.setMap(CacheConstants.ActInfo.ACT_LIST, redisMap,CacheConstants.ActInfo.ACT_LIST_EXPIRE_TIME);
        List<ActBaseInfoDTO> actBaseInfoDTOList = Lists.newArrayList();
        for (ActInfoDO actInfoDO : actInfoDOList) {
            actBaseInfoDTOList.add(convertActInfoDOToActBaseInfoDTO(actInfoDO));
        }
        return actBaseInfoDTOList;
    }

    /**
     * 查询单个活动基本信息（布隆+二级缓存防穿透）
     * @param actCode 活动code
     * @return 活动基本信息
     */
    public ActBaseInfoDTO getActInfoFromCache(String actCode){
        if(bloomFilerHelper.notContains(actCode)){
            return null;
        }
        Object localActJson = caffeineCacheUtil.getCache(CacheConstants.ActInfo.CAFFEINE_ACT_INFO, actCode);
        if(ObjectUtil.isNotNull(localActJson)){
            ActInfoDO actInfoDO = JSONUtil.toBean((String) localActJson, ActInfoDO.class);
            return convertActInfoDOToActBaseInfoDTO(actInfoDO);
        }
        String redisKey = String.format(CacheConstants.ActInfo.ACT_BASE_INFO, actCode);
        String redisActJson = redisHelper.get(redisKey);
        if(StrUtil.isNotEmpty(redisActJson)){
            if(StrUtil.equals(redisActJson, CommConstants.REDIS_NULL_DEFAULT_VALUE)){
                return null;
            }
            // 将redis缓存信息设置到本地缓存
            ActInfoDO actInfoDO = JSONUtil.toBean(redisActJson, ActInfoDO.class);
            caffeineCacheUtil.putCache(CacheConstants.ActInfo.CAFFEINE_ACT_INFO, actCode, JSONUtil.toJsonStr(actInfoDO));
            return convertActInfoDOToActBaseInfoDTO(actInfoDO);
        }
        // 这里做穿透处理, 默认缓存一个空值，设置一个短暂的时间2分钟
        redisHelper.set(redisKey, CommConstants.REDIS_NULL_DEFAULT_VALUE, CommConstants.REDIS_NULL_DEFAULT_time);
        return null;
    }

    /**
     * 对象转换
     */
    private ActBaseInfoDTO convertActInfoDOToActBaseInfoDTO(ActInfoDO actInfoDO){
        // 这里不用bean copy的原因，高并发的时候会影响性能
        ActBaseInfoDTO actBaseInfoDTO = new ActBaseInfoDTO();
        actBaseInfoDTO.setId(actInfoDO.getId());
        actBaseInfoDTO.setActNo(actInfoDO.getActNo());
        actBaseInfoDTO.setActName(actInfoDO.getActName());
        actBaseInfoDTO.setStatus(actInfoDO.getStatus());
        actBaseInfoDTO.setStartTime(actInfoDO.getStartTime());
        actBaseInfoDTO.setEndTime(actInfoDO.getEndTime());
        actBaseInfoDTO.setActRule(actInfoDO.getActRule());
        return actBaseInfoDTO;
    }
}
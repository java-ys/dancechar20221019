package com.litian.dancechar.core.biz.activity.info.service;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.litian.dancechar.core.biz.activity.info.cache.ActInfoCacheService;
import com.litian.dancechar.core.biz.activity.info.dao.entity.ActInfoDO;
import com.litian.dancechar.core.biz.activity.info.dao.inf.ActInfoDao;
import com.litian.dancechar.core.biz.activity.info.dto.ActInfoPublishDTO;
import com.litian.dancechar.core.biz.activity.info.dto.ActInfoReqDTO;
import com.litian.dancechar.core.biz.activity.info.dto.ActInfoRespDTO;
import com.litian.dancechar.core.biz.activity.info.dto.ActInfoSaveDTO;
import com.litian.dancechar.core.biz.activity.info.enums.ActStatusEnum;
import com.litian.dancechar.core.biz.activity.info.eventbus.ActInfoChangeEvent;
import com.litian.dancechar.core.biz.activity.info.eventbus.ActInfoChangeEventBusListener;
import com.litian.dancechar.core.common.enums.MessageChannelEnum;
import com.litian.dancechar.core.feign.idgen.client.IdGenClient;
import com.litian.dancechar.core.feign.idgen.dto.IdGenReqDTO;
import com.litian.dancechar.framework.cache.publish.util.MessagePublishUtil;
import com.litian.dancechar.framework.common.base.PageWrapperDTO;
import com.litian.dancechar.framework.common.base.RespResult;
import com.litian.dancechar.framework.common.eventbus.EventBusFactory;
import com.litian.dancechar.framework.common.util.DCBeanUtil;
import com.litian.dancechar.framework.common.util.PageResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 活动信息服务
 *
 * @author tojson
 * @date 2022/7/9 6:30
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class ActInfoService extends ServiceImpl<ActInfoDao, ActInfoDO> {
    @Resource
    private ActInfoDao actInfoDao;
    @Resource
    private IdGenClient idGenClient;
    @Resource
    private MessagePublishUtil messagePublishUtil;
    @Resource
    private ActInfoCacheService actInfoCacheService;

    /**
     * 功能: 分页查询活动领取列表
     */
    public RespResult<PageWrapperDTO<ActInfoRespDTO>> listPaged(ActInfoReqDTO req) {
        PageHelper.startPage(req.getPageNo(), req.getPageSize());
        PageWrapperDTO<ActInfoRespDTO> pageCommon = new PageWrapperDTO<>();
        PageResultUtil.setPageResult(actInfoDao.findList(req), pageCommon);
        return RespResult.success(pageCommon);
    }

    /**
     * 功能：根据Id-查询活动信息
     */
    public ActInfoDO findById(String id) {
        return actInfoDao.selectById(id);
    }

    /**
     * 功能：查询指定状态的活动列表
     */
    public List<ActInfoDO> findActInfoListByStatus(List<Integer> statusList) {
        LambdaQueryWrapper<ActInfoDO> lambdaQueryWrapper = Wrappers.lambdaQuery();
        lambdaQueryWrapper.in(ActInfoDO::getStatus, statusList);
        lambdaQueryWrapper.orderByDesc(ActInfoDO::getId);
        return this.list(lambdaQueryWrapper);
    }

    /**
     * 功能：新增活动
     */
    public RespResult<String> createActInfo(ActInfoSaveDTO actInfoSaveDTO) {
        ActInfoDO actInfoDO = new ActInfoDO();
        DCBeanUtil.copyNotNull(actInfoDO, actInfoSaveDTO);
        actInfoDO.setStatus(ActStatusEnum.NOT_PUBLISH.getCode());
        String actNo;
        RespResult<String> respResult = idGenClient.genId(IdGenReqDTO.builder().module("act").build());
        if(respResult.isOk()){
            actNo = respResult.getData();
        }else{
            // 同时打印错误日志，及时告警，研发介入，另外保证系统可靠，这里会兜底给一个值
            log.error("请检查活动编号生成服务是否正常！");
            actNo = IdUtil.objectId();
        }
        actInfoDO.setActNo(actNo);
        this.save(actInfoDO);
        log.info("创建活动成功！data:{}", JSONUtil.toJsonStr(actInfoDO));
        return RespResult.success(actInfoDO.getId());
    }

    /**
     * 功能：修改活动
     */
    public RespResult<Boolean> updateActInfo(ActInfoSaveDTO actInfoSaveDTO) {
        ActInfoDO actInfoDO = actInfoDao.selectById(actInfoSaveDTO.getId());
        if(ObjectUtil.isNull(actInfoDO)){
            return RespResult.error("活动不存在！actId:{}", actInfoSaveDTO.getId());
        }
        if(!ActStatusEnum.NOT_PUBLISH.getCode().equals(actInfoDO.getStatus())){
            return RespResult.error("只能修改未发布状态的活动");
        }
        DCBeanUtil.copyNotNull(actInfoDO, actInfoSaveDTO);
        this.updateById(actInfoDO);
        log.info("修改活动成功！data:{}", JSONUtil.toJsonStr(actInfoDO));
        return RespResult.success(true);
    }

    /**
     * 功能：发布活动
     */
    public RespResult<Boolean> publishActInfo(ActInfoPublishDTO actInfoPublishDTO) {
        ActInfoDO actInfoDO = actInfoDao.selectById(actInfoPublishDTO.getId());
        if(ObjectUtil.isNull(actInfoDO)){
            return RespResult.error("活动不存在！actId:{}", actInfoPublishDTO.getId());
        }
        if(!ActStatusEnum.NOT_PUBLISH.getCode().equals(actInfoDO.getStatus())){
            return RespResult.error("只能发布未发布状态的活动");
        }
        if(actInfoPublishDTO.getEndTime().getTime() < new Date().getTime()){
            return RespResult.error("活动结束时间不能小于当前时间，请检查");
        }
        DCBeanUtil.copyNotNull(actInfoDO, actInfoPublishDTO);
        actInfoDO.setStatus(ActStatusEnum.PUBLISH.getCode());
        this.updateById(actInfoDO);
        // 异步发布事件，详情逻辑见ActInfoChangeListener
        EventBusFactory.build().registerAsyncEvent(ActInfoChangeEventBusListener.class);
        EventBusFactory.build().postAsyncEvent(ActInfoChangeEvent.builder().actInfoDO(actInfoDO).eventName("发布活动变更活动信息事件").build());
        return RespResult.success(true);
    }

    /**
     * 功能：结束活动
     */
    public RespResult<Boolean> endActInfo(String id) {
        ActInfoDO actInfoDO = actInfoDao.selectById(id);
        if(ObjectUtil.isNull(actInfoDO)){
            return RespResult.error("活动不存在！actId:{}", id);
        }
        if(!ActStatusEnum.STARTING.getCode().equals(actInfoDO.getStatus())){
            return RespResult.error("只能结束进行中的活动");
        }
        actInfoDO.setStatus(ActStatusEnum.END.getCode());
        this.updateById(actInfoDO);
        refreshRedisAndLocalCache(actInfoDO);
        return RespResult.success(true);
    }

    /**
     * 刷新redis缓存和本地缓存
     */
    public void refreshRedisAndLocalCache(ActInfoDO actInfoDO){
        // 刷新redis缓存
        actInfoCacheService.refreshRedisCache(actInfoDO);
        // 采用redis发布订阅实现或广播kafka消息，更新活动本地缓存
        messagePublishUtil.sendMsg(MessageChannelEnum.ACT_CHANNEL.getCode(), actInfoDO);
    }
}
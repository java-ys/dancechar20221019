package com.litian.dancechar.core.biz.activity.act20220910.service;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.litian.dancechar.core.biz.activity.act20220910.dto.Act20220910ReqDTO;
import com.litian.dancechar.core.biz.activity.info.dao.entity.ActInfoDO;
import com.litian.dancechar.core.biz.activity.info.service.ActInfoService;
import com.litian.dancechar.core.biz.activity.taskconfig.service.ActTaskConfigInfoService;
import com.litian.dancechar.framework.common.base.RespResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 中秋活动信息服务
 *
 * @author tojson
 * @date 2022/7/9 6:30
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class Act20220910Service {
    @Resource
    private ActInfoService actInfoService;
    @Resource
    private ActTaskConfigInfoService actTaskConfigInfoService;

    /**
     * 功能：完成任务
     */
    public RespResult<Boolean> finishTask(Act20220910ReqDTO act20220910ReqDTO) {
        return null;
    }

    /**
     * 验证任务参数是否合法
     */
    private RespResult<Void> validParam(Act20220910ReqDTO act20220910ReqDTO){
        String actId = act20220910ReqDTO.getActId();
        if(StrUtil.isEmpty(actId)){
            return RespResult.error("actId不能为空");
        }
        if(StrUtil.isEmpty(act20220910ReqDTO.getCustomerId())){
            return RespResult.error("customerId不能为空");
        }
        if(StrUtil.isEmpty(act20220910ReqDTO.getTaskId())){
            return RespResult.error("taskId不能为空");
        }
        ActInfoDO actInfoDO = actInfoService.findById(actId);
        if(ObjectUtil.isNull(actInfoDO)){
            return RespResult.error("活动不存在");
        }
        long curDate = new Date().getTime();
        if(actInfoDO.getStartTime().getTime() > curDate
                || actInfoDO.getEndTime().getTime() < curDate){
            return RespResult.error("活动未开始或活动已结束");
        }
        return null;
    }
}
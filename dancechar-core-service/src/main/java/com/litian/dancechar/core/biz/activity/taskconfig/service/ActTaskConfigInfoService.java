package com.litian.dancechar.core.biz.activity.taskconfig.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.litian.dancechar.core.biz.activity.taskconfig.dao.entity.ActTaskConfigInfoDO;
import com.litian.dancechar.core.biz.activity.taskconfig.dao.inf.ActTaskConfigInfoDao;
import com.litian.dancechar.core.biz.activity.taskconfig.dto.ActTaskConfigInfoReqDTO;
import com.litian.dancechar.core.biz.activity.taskconfig.dto.ActTaskConfigInfoRespDTO;
import com.litian.dancechar.core.biz.activity.taskconfig.dto.ActTaskConfigInfoSaveDTO;
import com.litian.dancechar.framework.common.base.PageWrapperDTO;
import com.litian.dancechar.framework.common.base.RespResult;
import com.litian.dancechar.framework.common.util.DCBeanUtil;
import com.litian.dancechar.framework.common.util.PageResultUtil;
import com.litian.dancechar.framework.cache.redis.util.RedisHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 活动任务配置信息服务
 *
 * @author tojson
 * @date 2022/7/9 6:30
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class ActTaskConfigInfoService extends ServiceImpl<ActTaskConfigInfoDao, ActTaskConfigInfoDO> {
    @Resource
    private ActTaskConfigInfoDao actTaskConfigInfoDao;

    @Resource
    private RedisHelper redisHelper;

    /**
     * 功能: 分页查询活动任务配置列表
     */
    public RespResult<PageWrapperDTO<ActTaskConfigInfoRespDTO>> listPaged(ActTaskConfigInfoReqDTO req) {
        PageHelper.startPage(req.getPageNo(), req.getPageSize());
        PageWrapperDTO<ActTaskConfigInfoRespDTO> pageCommon = new PageWrapperDTO<>();
        PageResultUtil.setPageResult(actTaskConfigInfoDao.findList(req), pageCommon);
        return RespResult.success(pageCommon);
    }

    /**
     * 功能：根据Id-查询活动任务配置信息
     */
    public ActTaskConfigInfoDO findById(String id) {
        return actTaskConfigInfoDao.selectById(id);
    }

    /**
     * 功能：查询活动任务配置列表
     */
    public RespResult<List<ActTaskConfigInfoRespDTO>> findList(ActTaskConfigInfoReqDTO req) {
        return RespResult.success(actTaskConfigInfoDao.findList(req));
    }

    /**
     * 功能：新增活动任务配置信息
     */
    public RespResult<String> saveWithInsert(ActTaskConfigInfoSaveDTO actInfoSaveDTO) {
        ActTaskConfigInfoDO actTaskConfigInfoDO = new ActTaskConfigInfoDO();
        DCBeanUtil.copyNotNull(actTaskConfigInfoDO, actInfoSaveDTO);
        save(actTaskConfigInfoDO);
        return RespResult.success(actTaskConfigInfoDO.getId());
    }
}
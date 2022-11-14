package com.litian.dancechar.idgenerator.core.idgenconfig.service;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.litian.dancechar.framework.cache.redis.util.RedisHelper;
import com.litian.dancechar.framework.common.base.PageWrapperDTO;
import com.litian.dancechar.framework.common.base.RespResult;
import com.litian.dancechar.framework.common.util.DCBeanUtil;
import com.litian.dancechar.framework.common.util.PageResultUtil;
import com.litian.dancechar.idgenerator.core.idgenconfig.dao.entity.SysIdGenConfigInfoDO;
import com.litian.dancechar.idgenerator.core.idgenconfig.dao.inf.SysIdGenConfigInfoDao;
import com.litian.dancechar.idgenerator.core.idgenconfig.dto.SysIdGenConfigInfoReqDTO;
import com.litian.dancechar.idgenerator.core.idgenconfig.dto.SysIdGenConfigInfoRespDTO;
import com.litian.dancechar.idgenerator.core.idgenconfig.dto.SysIdGenConfigInfoSaveDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * id生成配置信息服务
 *
 * @author tojson
 * @date 2022/7/9 6:30
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class SysIdGenConfigInfoService extends ServiceImpl<SysIdGenConfigInfoDao, SysIdGenConfigInfoDO> {
    @Resource
    private SysIdGenConfigInfoDao sysIdGenConfigInfoDao;

    @Resource
    private RedisHelper redisHelper;

    /**
     * 功能: 分页查询id生成配置信息列表
     */
    public RespResult<PageWrapperDTO<SysIdGenConfigInfoRespDTO>> listPaged(SysIdGenConfigInfoReqDTO req) {
        PageHelper.startPage(req.getPageNo(), req.getPageSize());
        PageWrapperDTO<SysIdGenConfigInfoRespDTO> pageCommon = new PageWrapperDTO<>();
        PageResultUtil.setPageResult(sysIdGenConfigInfoDao.findList(req), pageCommon);
        return RespResult.success(pageCommon);
    }

    /**、* 功能：查询id生成配置信息
     */
    public SysIdGenConfigInfoDO findById(String id) {
        return sysIdGenConfigInfoDao.selectById(id);
    }


    /**
     * 功能：查询id生成配置信息列表
     */
    public List<SysIdGenConfigInfoRespDTO> findList(SysIdGenConfigInfoReqDTO req) {
        return sysIdGenConfigInfoDao.findList(req);
    }

    /**
     * 功能：根据module查询配置信息
     */
    public SysIdGenConfigInfoDO getByModule(String module) {
        LambdaQueryWrapper<SysIdGenConfigInfoDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(SysIdGenConfigInfoDO::getModule, module);
        List<SysIdGenConfigInfoDO> configList = sysIdGenConfigInfoDao.selectList(queryWrapper);
        return CollUtil.isNotEmpty(configList) ? configList.get(0) : null;
    }

    /**
     * 功能：新增id生成配置信息数据
     */
    public RespResult<Boolean> saveWithInsert(SysIdGenConfigInfoSaveDTO sysIdGenConfigInfoSaveDTO) {
        SysIdGenConfigInfoDO sysIdGenConfigInfoDO = new SysIdGenConfigInfoDO();
        DCBeanUtil.copyNotNull(sysIdGenConfigInfoDO, sysIdGenConfigInfoSaveDTO);
        save(sysIdGenConfigInfoDO);
        return RespResult.success(true);
    }
}
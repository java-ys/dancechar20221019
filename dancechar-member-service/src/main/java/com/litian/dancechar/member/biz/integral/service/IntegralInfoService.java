package com.litian.dancechar.member.biz.integral.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.litian.dancechar.framework.common.base.PageWrapperDTO;
import com.litian.dancechar.framework.common.base.RespResult;
import com.litian.dancechar.framework.common.util.DCBeanUtil;
import com.litian.dancechar.framework.common.util.PageResultUtil;
import com.litian.dancechar.framework.cache.redis.util.RedisHelper;
import com.litian.dancechar.member.biz.integral.dao.entity.IntegralInfoDO;
import com.litian.dancechar.member.biz.integral.dao.inf.IntegralDao;
import com.litian.dancechar.member.biz.integral.dto.IntegralInfoReqDTO;
import com.litian.dancechar.member.biz.integral.dto.IntegralInfoRespDTO;
import com.litian.dancechar.member.biz.integral.dto.IntegralInfoSaveDTO;
import com.litian.dancechar.member.common.constants.RedisKeyConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 会员积分服务
 *
 * @author tojson
 * @date 2022/7/9 6:30
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class IntegralInfoService extends ServiceImpl<IntegralDao, IntegralInfoDO> {
    @Resource
    private IntegralDao integralDao;

    @Resource
    private RedisHelper redisHelper;

    /**
     * 功能: 分页查询积分列表
     */
    public RespResult<PageWrapperDTO<IntegralInfoRespDTO>> listPaged(IntegralInfoReqDTO req) {
        PageHelper.startPage(req.getPageNo(), req.getPageSize());
        PageWrapperDTO<IntegralInfoRespDTO> pageCommon = new PageWrapperDTO<>();
        PageResultUtil.setPageResult(integralDao.findList(req), pageCommon);
        return RespResult.success(pageCommon);
    }

    /**
     * 功能：根据Id-查询积分信息
     */
    public IntegralInfoDO findById(String id) {
        String key = String.format(RedisKeyConstants.Student.ID_KEY, id);
        String studentFromRedis = redisHelper.get(key);
        if(StrUtil.isNotEmpty(studentFromRedis)){
            return JSONUtil.toBean(studentFromRedis, IntegralInfoDO.class);
        }
        IntegralInfoDO studentDO = integralDao.selectById(id);
        if(ObjectUtil.isNotNull(studentDO)){
            redisHelper.set(key, JSONUtil.toJsonStr(studentDO));
        }
        return studentDO;
    }

    /**
     * 功能：根据条件-查询积分信息
     */
    public IntegralInfoDO findByCondition(IntegralInfoReqDTO reqDTO) {
        LambdaQueryWrapper<IntegralInfoDO> lambda = new LambdaQueryWrapper<IntegralInfoDO>();
        lambda.eq(IntegralInfoDO::getCustomerId,reqDTO.getCustomerId());
        if(StrUtil.isNotEmpty(reqDTO.getSerialNo())){
            lambda.eq(IntegralInfoDO::getSerialNo,reqDTO.getSerialNo());
        }
        List<IntegralInfoDO> integralInfoDOList = integralDao.selectList(lambda);
        return CollUtil.isNotEmpty(integralInfoDOList) ? integralInfoDOList.get(0) : null;
    }

    /**
     * 功能：批量查询积分信息
     */
    public List<IntegralInfoDO> findByIds(List<String> ids) {
        return integralDao.selectBatchIds(ids);
    }


    /**
     * 功能：查询积分列表
     */
    public RespResult<List<IntegralInfoRespDTO>> findList(IntegralInfoReqDTO req) {
        return RespResult.success(integralDao.findList(req));
    }

    /**
     * 功能：新增积分
     */
    public RespResult<String> saveWithInsert(IntegralInfoSaveDTO integralInfoSaveDTO) {
        IntegralInfoDO integralInfoDO = new IntegralInfoDO();
        DCBeanUtil.copyNotNull(integralInfoDO, integralInfoSaveDTO);
        save(integralInfoDO);
        return RespResult.success(integralInfoDO.getId());
    }
}
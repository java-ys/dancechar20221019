package com.litian.dancechar.member.biz.customer.service;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.litian.dancechar.framework.cache.redis.util.RedisHelper;
import com.litian.dancechar.framework.common.base.PageWrapperDTO;
import com.litian.dancechar.framework.common.base.RespResult;
import com.litian.dancechar.framework.common.util.DCBeanUtil;
import com.litian.dancechar.framework.common.util.PageResultUtil;
import com.litian.dancechar.member.biz.customer.dao.entity.CustomerInfoDO;
import com.litian.dancechar.member.biz.customer.dao.inf.CustomerInfoDao;
import com.litian.dancechar.member.biz.customer.dto.CustomerInfoReqDTO;
import com.litian.dancechar.member.biz.customer.dto.CustomerInfoRespDTO;
import com.litian.dancechar.member.common.constants.RedisKeyConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 会员信息服务
 *
 * @author tojson
 * @date 2022/7/9 6:30
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class CustomerInfoService extends ServiceImpl<CustomerInfoDao, CustomerInfoDO> {
    @Resource
    private CustomerInfoDao customerInfoDao;

    @Resource
    private RedisHelper redisHelper;

    /**
     * 功能: 分页查询会员列表
     */
    public RespResult<PageWrapperDTO<CustomerInfoRespDTO>> listPaged(CustomerInfoReqDTO req) {
        PageHelper.startPage(req.getPageNo(), req.getPageSize());
        PageWrapperDTO<CustomerInfoRespDTO> pageCommon = new PageWrapperDTO<>();
        PageResultUtil.setPageResult(customerInfoDao.findList(req), pageCommon);
        return RespResult.success(pageCommon);
    }

    /**、* 功能：查询会员信息
     */
    public CustomerInfoDO findById(String id) {
        String key = String.format(RedisKeyConstants.Student.ID_KEY, id);
        String studentFromRedis = redisHelper.get(key);
        if(StrUtil.isNotEmpty(studentFromRedis)){
            return JSONUtil.toBean(studentFromRedis, CustomerInfoDO.class);
        }
        CustomerInfoDO studentDO = customerInfoDao.selectById(id);
        if(ObjectUtil.isNotNull(studentDO)){
            redisHelper.set(key, JSONUtil.toJsonStr(studentDO));
        }
        return studentDO;
    }

    /**
     * 功能：批量查询会员信息
     */
    public List<CustomerInfoDO> findByIds(List<String> ids) {
        return customerInfoDao.selectBatchIds(ids);
    }


    /**
     * 功能：查询会员列表
     */
    public RespResult<List<CustomerInfoRespDTO>> findList(CustomerInfoReqDTO req) {
        return RespResult.success(customerInfoDao.findList(req));
    }

    /**
     * 功能：新增会员数据
     */
    public RespResult<Boolean> saveWithInsert(CustomerInfoReqDTO studentReqDTO) {
        CustomerInfoDO customerInfoDO = new CustomerInfoDO();
        DCBeanUtil.copyNotNull(customerInfoDO, studentReqDTO);
        save(customerInfoDO);
        return RespResult.success(true);
    }
}
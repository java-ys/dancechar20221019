package com.litian.dancechar.core.biz.blacklist.service;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.litian.dancechar.core.biz.blacklist.dao.entity.SysBlackListDO;
import com.litian.dancechar.core.biz.blacklist.dao.inf.SysBlackListDao;
import com.litian.dancechar.core.biz.blacklist.dto.SysBlackListAddOrEditDTO;
import com.litian.dancechar.core.biz.blacklist.dto.SysBlackListReqDTO;
import com.litian.dancechar.core.biz.blacklist.dto.SysBlackListRespDTO;
import com.litian.dancechar.core.init.ThreadPoolInit;
import com.litian.dancechar.framework.common.base.PageWrapperDTO;
import com.litian.dancechar.framework.common.base.RespResult;
import com.litian.dancechar.framework.common.util.DCBeanUtil;
import com.litian.dancechar.framework.common.util.PageResultUtil;
import com.litian.dancechar.framework.encrypt.util.EncryptUtil;
import com.litian.dancechar.framework.cache.redis.util.RedisHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 黑名单请求url服務
 *
 * @author tojson
 * @date 2021/6/19 15:13
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class SysBlackListService {
    @Resource
    private SysBlackListDao sysBlackListDao;
    @Resource
    private RedisHelper redisHelper;

    /**
     * 功能: 分页查询黑名单列表
     */
    public RespResult<PageWrapperDTO<SysBlackListRespDTO>> listPaged(SysBlackListReqDTO req) {
        PageHelper.startPage(req.getPageNo(), req.getPageSize());
        PageWrapperDTO<SysBlackListRespDTO> pageCommon = new PageWrapperDTO<>();
        PageResultUtil.setPageResult(EncryptUtil.handleDecryptList(sysBlackListDao.findList(req)), pageCommon);
        redisHelper.set("hello", "sgsg");
        System.out.println(redisHelper.get("hello"));
        for(int i=0; i<5;i++){
            ThreadPoolInit.getThreadPoolTaskExecutor().execute(()->{
                log.info("验证黑名单异步traceId不丢失");
            });
        }
        return RespResult.success(pageCommon);
    }

    /**
     * 功能：查询黑名单列表
     */
    public RespResult<List<SysBlackListRespDTO>> findList(SysBlackListReqDTO req) {
        return RespResult.success(sysBlackListDao.findList(req));
    }

    /**
     * 保存黑名单(新增或修改)
     *
     * @param req 黑名单对象
     * @return 保存结果
     */
    public RespResult<Boolean> save(SysBlackListAddOrEditDTO req) {
        if (StrUtil.isNotEmpty(req.getId())) {
            SysBlackListDO sysBlackListDO = sysBlackListDao.selectById(req.getId());
            if (sysBlackListDO != null) {
                DCBeanUtil.copyNotNull(sysBlackListDO, req);
                sysBlackListDao.updateById(sysBlackListDO);
            }
        } else {
            SysBlackListDO sysBlackListDO = new SysBlackListDO();
            DCBeanUtil.copyNotNull(sysBlackListDO, req);
            sysBlackListDao.insert(sysBlackListDO);
        }
        return RespResult.success(true);
    }
}
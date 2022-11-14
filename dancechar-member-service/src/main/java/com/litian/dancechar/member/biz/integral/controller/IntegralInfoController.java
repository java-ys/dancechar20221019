package com.litian.dancechar.member.biz.integral.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.litian.dancechar.framework.cache.redis.distributelock.annotation.Lock;
import com.litian.dancechar.framework.common.base.PageWrapperDTO;
import com.litian.dancechar.framework.common.base.RespResult;
import com.litian.dancechar.framework.common.util.DCBeanUtil;
import com.litian.dancechar.framework.common.validator.ValidatorUtil;
import com.litian.dancechar.member.biz.integral.dto.IntegralInfoReqDTO;
import com.litian.dancechar.member.biz.integral.dto.IntegralInfoRespDTO;
import com.litian.dancechar.member.biz.integral.dto.IntegralInfoSaveDTO;
import com.litian.dancechar.member.biz.integral.service.IntegralInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 会员积分业务处理
 *
 * @author tojson
 * @date 2022/7/9 06:26
 */
@Api(tags = "会员积分相关api")
@RestController
@Slf4j
@RequestMapping(value = "/member/integral/")
public class IntegralInfoController {
    @Resource
    private IntegralInfoService integralInfoService;

    @ApiOperation(value = "分页查询列表", notes = "分页查询列表")
    @PostMapping("listPaged")
    public RespResult<PageWrapperDTO<IntegralInfoRespDTO>> listPaged(@RequestBody IntegralInfoReqDTO req) {
        return integralInfoService.listPaged(req);
    }

    @ApiOperation(value = "根据Id查询积分信息", notes = "根据Id查询积分信息")
    @PostMapping("findById")
    public RespResult<IntegralInfoRespDTO> findById(@RequestBody IntegralInfoReqDTO req) {
        return RespResult.success(DCBeanUtil.copyNotNull(new IntegralInfoRespDTO(), integralInfoService.findById(req.getId())));
    }

    @ApiOperation(value = "根据积分流水号-查询积分信息", notes = "根据积分流水号-查询积分信息")
    @PostMapping("findBySerialNo")
    public RespResult<IntegralInfoRespDTO> findBySerialNo(@RequestBody IntegralInfoReqDTO req) {
        if(StrUtil.hasBlank(req.getCustomerId(), req.getSerialNo())){
            return RespResult.error("customerId或serialNo不能为空！");
        }
        return RespResult.success(DCBeanUtil.copyNotNull(new IntegralInfoRespDTO(), integralInfoService
                .findByCondition(req)));
    }

    @ApiOperation(value = "批量根据Id查询积分信息", notes = "批量根据Id查询积分信息")
    @PostMapping("findByIds")
    public RespResult<List<IntegralInfoRespDTO>> findByIds(@RequestBody IntegralInfoReqDTO req) {
        if(CollUtil.isEmpty(req.getIds())){
            return RespResult.error("ids不能为空！");
        }
        return RespResult.success(DCBeanUtil.copyList(integralInfoService.findByIds(req.getIds()), IntegralInfoRespDTO.class));
    }

    @ApiOperation(value = "新增保存积分", notes = "新增积分")
    @PostMapping("saveWithInsert")
    @Lock(value = "#integralInfoSaveDTO.serialNo", lockFailMsg = "请勿重复提交", expireTime = 3000)
    public RespResult<String> saveWithInsert(@RequestBody IntegralInfoSaveDTO integralInfoSaveDTO) {
        log.info("新增保存积分....");
        ValidatorUtil.validate(integralInfoSaveDTO);
        return integralInfoService.saveWithInsert(integralInfoSaveDTO);
    }
}
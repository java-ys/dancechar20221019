package com.litian.dancechar.core.biz.activity.info.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.litian.dancechar.core.biz.activity.info.cache.ActInfoCacheService;
import com.litian.dancechar.core.biz.activity.info.dto.*;
import com.litian.dancechar.core.biz.activity.info.exception.ActSentinelBlockExceptionHandler;
import com.litian.dancechar.core.biz.activity.info.service.ActInfoService;
import com.litian.dancechar.framework.cache.redis.distributelock.annotation.Lock;
import com.litian.dancechar.framework.common.base.PageWrapperDTO;
import com.litian.dancechar.framework.common.base.RespResult;
import com.litian.dancechar.framework.common.util.DCBeanUtil;
import com.litian.dancechar.framework.common.validator.ValidatorUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 活动业务处理
 *
 * @author tojson
 * @date 2022/7/9 11:26
 */
@Api(tags = "活动相关api")
@RestController
@Slf4j
@RequestMapping(value = "/act/info")
public class ActInfoController {
    @Resource
    private ActInfoService actInfoService;
    @Resource
    private ActInfoCacheService actInfoCacheService;

    @ApiOperation(value = "分页查询活动列表", notes = "分页查询活动列表")
    @PostMapping("listPaged")
    public RespResult<PageWrapperDTO<ActInfoRespDTO>> listPaged(@RequestBody ActInfoReqDTO req) {
        return actInfoService.listPaged(req);
    }

    @ApiOperation(value = "根据Id查询活动信息", notes = "根据Id查询活动信息")
    @PostMapping("findById")
    public RespResult<ActInfoRespDTO> findById(@RequestBody ActInfoReqDTO req) {
        return RespResult.success(DCBeanUtil.copyNotNull(new ActInfoRespDTO(), actInfoService.findById(req.getId())));
    }

    @ApiOperation(value = "新增活动", notes = "新增活动")
    @PostMapping("insert")
    @Lock(value = "#actInfoSaveDTO.actName", lockFailMsg = "请勿重复提交", expireTime = 3000)
    public RespResult<String> insert(@RequestBody ActInfoSaveDTO actInfoSaveDTO) {
        log.info("新增保存活动....");
        ValidatorUtil.validate(actInfoSaveDTO);
        return actInfoService.createActInfo(actInfoSaveDTO);
    }

    @ApiOperation(value = "修改活动", notes = "修改活动")
    @PostMapping("update")
    public RespResult<Boolean> update(@RequestBody ActInfoSaveDTO actInfoSaveDTO) {
        log.info("修改保存活动....");
        if(StrUtil.isNotEmpty(actInfoSaveDTO.getId())){
            return RespResult.error("id不能为空");
        }
        ValidatorUtil.validate(actInfoSaveDTO);
        return actInfoService.updateActInfo(actInfoSaveDTO);
    }

    @ApiOperation(value = "发布活动", notes = "发布活动")
    @PostMapping("publish")
    public RespResult<Boolean> publish(@RequestBody ActInfoPublishDTO actInfoPublishDTO) {
        log.info("发布活动....");
        ValidatorUtil.validate(actInfoPublishDTO);
        return actInfoService.publishActInfo(actInfoPublishDTO);
    }

    @ApiOperation(value = "结束活动", notes = "结束活动")
    @PostMapping("end")
    public RespResult<Boolean> endActInfo(@RequestBody ActInfoEndDTO actInfoEndDTO) {
        log.info("结束活动....");
        if(StrUtil.isNotEmpty(actInfoEndDTO.getId())){
            return RespResult.error("id不能为空");
        }
        return actInfoService.endActInfo(actInfoEndDTO.getId());
    }

    @ApiOperation(value = "查询活动列表", notes = "查询活动列表")
    @PostMapping("findActList")
    public RespResult<List<ActBaseInfoDTO>> findActList(@RequestBody ActInfoQueryDTO actInfoQueryDTO) {
        List<ActBaseInfoDTO> actList = actInfoCacheService.findActListFromCache();
        if(ObjectUtil.isNotNull(actInfoQueryDTO.getStatus())){
            return RespResult.success(actList.stream().filter(vo-> ObjectUtil.equal(vo.getStatus(),
                    actInfoQueryDTO.getStatus())).collect(Collectors.toList()));
        }
        return RespResult.success(actList);
    }

    @ApiOperation(value = "根据活动code-查询活动信息", notes = "根据活动code-查询活动信息")
    @PostMapping("getActInfoByCode")
    @SentinelResource(value = "getActInfoByCode", blockHandler = "getActInfoByCodeBlockHandler", blockHandlerClass = ActSentinelBlockExceptionHandler.class)
    public RespResult<ActBaseInfoDTO> getActInfoByCode(@RequestBody ActInfoQueryDTO actInfoQueryDTO) {
        if(StrUtil.isEmpty(actInfoQueryDTO.getActNo())){
            return RespResult.error("actNo不能为空");
        }
        return RespResult.success(actInfoCacheService.getActInfoFromCache(actInfoQueryDTO.getActNo()));
    }
}
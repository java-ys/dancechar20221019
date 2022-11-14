package com.litian.dancechar.core.biz.blacklist.controller;

import com.litian.dancechar.core.biz.blacklist.conf.EnvConfig;
import com.litian.dancechar.core.biz.blacklist.dto.SysBlackListAddOrEditDTO;
import com.litian.dancechar.core.biz.blacklist.dto.SysBlackListReqDTO;
import com.litian.dancechar.core.biz.blacklist.dto.SysBlackListRespDTO;
import com.litian.dancechar.core.biz.blacklist.service.SysBlackListService;
import com.litian.dancechar.framework.common.base.PageWrapperDTO;
import com.litian.dancechar.framework.common.base.RespResult;
import com.litian.dancechar.framework.common.validator.ValidatorUtil;
import com.litian.dancechar.framework.common.validator.groups.Update;
import com.litian.dancechar.framework.cache.redis.constants.LockConstant;
import com.litian.dancechar.framework.cache.redis.distributelock.annotation.Lock;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 黑名单地址业务处理
 *
 * @author tojson
 * @date 2021/6/19 11:17
 */
@Api(tags = "黑名单相关api")
@RestController
@Slf4j
@RequestMapping(value = "/sys/blacklist/")
public class SysBlackListController {
    @Resource
    private SysBlackListService sysBlackListService;
    @Resource
    private EnvConfig envConfig;

    @ApiOperation(value = "分页查询黑名单列表", notes = "分页查询黑名单列表")
    @PostMapping("listPaged")
    public RespResult<PageWrapperDTO<SysBlackListRespDTO>> listPaged(@RequestBody SysBlackListReqDTO req) {
        return sysBlackListService.listPaged(req);
    }

    @ApiOperation(value = "查询黑名单列表", notes = "查询黑名单列表")
    @PostMapping("findList")
    public RespResult<List<SysBlackListRespDTO>> findList(@RequestBody SysBlackListReqDTO req) {
        return sysBlackListService.findList(req);
    }

    @ApiOperation(value = "新增黑名单保存", notes = "新增黑名单保存")
    @PostMapping("add")
    @Lock(keyPrefix = "mybatis/actinfo", lockFailMsg = LockConstant.REPEATED_SUBMIT, value = "#req.reqUrl")
    public RespResult<Boolean> add(@Validated @RequestBody SysBlackListAddOrEditDTO req) {
        log.info("进入新增黑名单....");
        return sysBlackListService.save(req);
    }

    @ApiOperation(value = "修改黑名单保存", notes = "修改黑名单保存")
    @PostMapping("update")
    public RespResult<Boolean> update(@RequestBody SysBlackListAddOrEditDTO req) {
        log.info("进入修改黑名单....");
        ValidatorUtil.validate(req, Update.class);
        return sysBlackListService.save(req);
    }
}
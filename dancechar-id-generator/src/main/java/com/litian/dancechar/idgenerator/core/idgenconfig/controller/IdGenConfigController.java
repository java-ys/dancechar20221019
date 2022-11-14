package com.litian.dancechar.idgenerator.core.idgenconfig.controller;

import com.litian.dancechar.framework.common.base.PageWrapperDTO;
import com.litian.dancechar.framework.common.base.RespResult;
import com.litian.dancechar.framework.common.util.DCBeanUtil;
import com.litian.dancechar.idgenerator.core.idgenconfig.dto.SysIdGenConfigInfoReqDTO;
import com.litian.dancechar.idgenerator.core.idgenconfig.dto.SysIdGenConfigInfoRespDTO;
import com.litian.dancechar.idgenerator.core.idgenconfig.dto.SysIdGenConfigInfoSaveDTO;
import com.litian.dancechar.idgenerator.core.idgenconfig.service.SysIdGenConfigInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * id生成配置信息业务处理
 *
 * @author tojson
 * @date 2022/7/9 06:26
 */
@Api(tags = "id生成配置信息相关api")
@RestController
@Slf4j
@RequestMapping(value = "/sys/idGenConfigInfo/")
public class IdGenConfigController {
    @Resource
    private SysIdGenConfigInfoService sysIdGenConfigInfoService;

    @ApiOperation(value = "分页查询列表", notes = "分页查询列表")
    @PostMapping("listPaged")
    public RespResult<PageWrapperDTO<SysIdGenConfigInfoRespDTO>> listPaged(@RequestBody SysIdGenConfigInfoReqDTO req) {
        return sysIdGenConfigInfoService.listPaged(req);
    }

    @ApiOperation(value = "根据Id查询信息", notes = "根据Id查询信息")
    @PostMapping("findById")
    public RespResult<SysIdGenConfigInfoRespDTO> findById(@RequestBody SysIdGenConfigInfoReqDTO req) {
        return RespResult.success(DCBeanUtil.copyNotNull(new SysIdGenConfigInfoRespDTO(),
                sysIdGenConfigInfoService.findById(req.getId())));
    }

    @ApiOperation(value = "新增保存", notes = "新增保存")
    @PostMapping("saveWithInsert")
    public RespResult<Boolean> saveWithInsert(@RequestBody SysIdGenConfigInfoSaveDTO req) {
        log.info("新增保存数据....");
        return sysIdGenConfigInfoService.saveWithInsert(req);
    }
}
package com.litian.dancechar.member.biz.customer.controller;

import com.litian.dancechar.framework.common.base.PageWrapperDTO;
import com.litian.dancechar.framework.common.base.RespResult;
import com.litian.dancechar.framework.common.util.DCBeanUtil;
import com.litian.dancechar.member.biz.customer.dto.CustomerInfoReqDTO;
import com.litian.dancechar.member.biz.customer.dto.CustomerInfoRespDTO;
import com.litian.dancechar.member.biz.customer.service.CustomerInfoService;
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
 * 会员信息业务处理
 *
 * @author tojson
 * @date 2022/7/9 06:26
 */
@Api(tags = "会员信息相关api")
@RestController
@Slf4j
@RequestMapping(value = "/member/base/")
public class CustomerInfoController {
    @Resource
    private CustomerInfoService customerInfoService;

    @ApiOperation(value = "分页查询列表", notes = "分页查询列表")
    @PostMapping("listPaged")
    public RespResult<PageWrapperDTO<CustomerInfoRespDTO>> listPaged(@RequestBody CustomerInfoReqDTO req) {
        return customerInfoService.listPaged(req);
    }

    @ApiOperation(value = "根据Id查询信息", notes = "根据Id查询信息")
    @PostMapping("findById")
    public RespResult<CustomerInfoRespDTO> findById(@RequestBody CustomerInfoReqDTO req) {
        return RespResult.success(DCBeanUtil.copyNotNull(new CustomerInfoRespDTO(), customerInfoService.findById(req.getId())));
    }
    @ApiOperation(value = "批量根据Id查询信息", notes = "批量根据Id查询信息")
    @PostMapping("findByIds")
    public RespResult<List<CustomerInfoRespDTO>> findByIds(@RequestBody CustomerInfoReqDTO req) {
        return RespResult.success(DCBeanUtil.copyList(customerInfoService.findByIds(req.getIds()), CustomerInfoRespDTO.class));
    }

    @ApiOperation(value = "新增保存", notes = "新增保存")
    @PostMapping("saveWithInsert")
    public RespResult<Boolean> saveWithInsert(@RequestBody CustomerInfoReqDTO req) {
        log.info("新增保存数据....");
        return customerInfoService.saveWithInsert(req);
    }
}
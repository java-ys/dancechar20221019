package com.litian.dancechar.core.biz.example.controller;

import cn.hutool.core.util.StrUtil;
import com.litian.dancechar.core.biz.blacklist.dao.entity.SysBlackListDO;
import com.litian.dancechar.core.biz.example.dto.WxChannelWhiteListReqDTO;
import com.litian.dancechar.core.feign.base.commonconfig.client.BaseServiceClient;
import com.litian.dancechar.core.feign.base.commonconfig.dto.SysWhiteListReqDTO;
import com.litian.dancechar.core.feign.base.commonconfig.dto.SysWhiteListRespDTO;
import com.litian.dancechar.core.feign.base.encrypt.client.EncryptClient;
import com.litian.dancechar.core.feign.base.encrypt.dto.EncryptDTO;
import com.litian.dancechar.framework.common.base.RespResult;
import com.litian.dancechar.framework.encrypt.util.EncryptUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * example业务处理
 *
 * @author tojson
 * @date 2022/6/5 14:17
 */
@Api(tags = "example相关controller")
@RestController
@Slf4j
@RequestMapping(value = "/example/")
public class ExampleController {
    @Resource
    private BaseServiceClient baseServiceClient;
    @Resource
    private EncryptClient encryptClient;
    @Resource
    private JdbcTemplate jdbcTemplate;

    @ApiOperation(value = "jdbc测试", notes = "jdbc测试")
    @PostMapping("jdbc")
    public RespResult<List<SysBlackListDO>> jdbc(@RequestBody WxChannelWhiteListReqDTO req) {
        String sql = "select * from sys_black_list where source='%s'";
        sql = String.format(sql, req.getSource());
        // Map<String, Object> map = jdbcTemplate.queryForMap(sql);
        // System.out.println(map);
        List<SysBlackListDO> studentList = jdbcTemplate.query(sql, (resultSet, i) -> {
            SysBlackListDO order = new SysBlackListDO();
            order.setId(resultSet.getString("id"));
            order.setSource(resultSet.getString("source"));
            order.setReqUrl(resultSet.getString("req_url"));
            order.setBlackName(resultSet.getString("black_name"));
            return order;
        });
        System.out.println(studentList);
        return RespResult.success(studentList);
    }

    @ApiOperation(value = "查询微信渠道白名单列表", notes = "查询微信渠道白名单列表")
    @PostMapping("findList")
    public RespResult<List<SysWhiteListRespDTO>> findList(@RequestBody WxChannelWhiteListReqDTO req) {
        SysWhiteListReqDTO sysWhiteListReqDTO = new SysWhiteListReqDTO();
        sysWhiteListReqDTO.setSource(req.getSource());
        return baseServiceClient.findWhiteList(sysWhiteListReqDTO);
    }

    @ApiOperation(value = "测试手机号加密", notes = "测试手机号加密")
    @PostMapping("encryptMobile")
    public RespResult<String> encryptMobile(@RequestBody EncryptDTO req) {
        if(StrUtil.hasBlank(req.getSystem(), req.getField())){
            return RespResult.error("system或field字段不能为空！");
        }
        return encryptClient.encryptMobile(req);
    }

    @ApiOperation(value = "测试手机号加密使用sdk", notes = "测试手机号加密使用sdk")
    @PostMapping("encryptMobileWithSDK")
    public RespResult<String> encryptMobileWithSDK(@RequestBody EncryptDTO req) {
        if(StrUtil.hasBlank(req.getSystem(), req.getField())){
            return RespResult.error("system或field字段不能为空！");
        }
        return RespResult.success(EncryptUtil.encryptMobile(req.getField()));
    }

    @ApiOperation(value = "测试手机号解密", notes = "测试手机号解密")
    @PostMapping("decryptMobile")
    public RespResult<String> decryptMobile(@RequestBody EncryptDTO req) {
        if(StrUtil.hasBlank(req.getSystem(), req.getField())){
            return RespResult.error("system或field字段不能为空！");
        }
        return encryptClient.decryptMobile(req);
    }

    @ApiOperation(value = "测试手机号解密使用sdk", notes = "测试手机号解密使用sdk")
    @PostMapping("decryptMobileWithSDK")
    public RespResult<String> decryptMobileWithSDK(@RequestBody EncryptDTO req) {
        if(StrUtil.hasBlank(req.getSystem(), req.getField())){
            return RespResult.error("system或field字段不能为空！");
        }
        return RespResult.success(EncryptUtil.decryptMobile(req.getField()));
    }
}
package com.litian.dancechar.base.biz.encrypt.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.litian.dancechar.base.biz.encrypt.dto.EncryptDTO;
import com.litian.dancechar.framework.common.base.RespResult;
import com.litian.dancechar.framework.encrypt.util.EncryptUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 加解密业务处理
 *
 * @author tojson
 * @date 2022/7/9 06:26
 */
@Api(tags = "加解密相关api")
@RestController
@Slf4j
@RequestMapping(value = "/sys/encrypt/")
public class EncryptController {

    @ApiOperation(value = "加密手机号", notes = "加密手机号")
    @PostMapping("encryptMobile")
    public RespResult<String> encryptMobile(@RequestBody EncryptDTO req) {
        if(StrUtil.hasBlank(req.getSystem(), req.getField())){
            return RespResult.error("system或field字段不能为空！");
        }
        return RespResult.success(EncryptUtil.encryptMobile(req.getField()));
    }

    @ApiOperation(value = "解密手机号", notes = "解密手机号")
    @PostMapping("decryptMobile")
    public RespResult<String> decryptMobile(@RequestBody EncryptDTO req) {
        if(StrUtil.hasBlank(req.getSystem(), req.getField())){
            return RespResult.error("system或field字段不能为空！");
        }
        return RespResult.success(EncryptUtil.decryptMobile(req.getField()));
    }

    @ApiOperation(value = "批量加密手机号", notes = "批量加密手机号")
    @PostMapping("batchEncryptMobiles")
    public RespResult<Map<String,String>> batchEncryptMobiles(@RequestBody EncryptDTO req) {
        if(StrUtil.hasBlank(req.getSystem()) || CollUtil.isEmpty(req.getFieldList())){
            return RespResult.error("system或fieldList字段不能为空！");
        }
        return RespResult.success(EncryptUtil.batchEncryptMobiles(req.getFieldList()));
    }

    @ApiOperation(value = "批量解密手机号", notes = "批量解密手机号")
    @PostMapping("batchDecryptMobiles")
    public RespResult<Map<String,String>> batchDecryptMobiles(@RequestBody EncryptDTO req) {
        if(StrUtil.hasBlank(req.getSystem()) || CollUtil.isEmpty(req.getFieldList())){
            return RespResult.error("system或fieldList字段不能为空！");
        }
        return RespResult.success(EncryptUtil.batchDecryptMobiles(req.getFieldList()));
    }

    @ApiOperation(value = "加密邮件", notes = "加密邮件")
    @PostMapping("encryptEmail")
    public RespResult<String> encryptEmail(@RequestBody EncryptDTO req) {
        if(StrUtil.hasBlank(req.getSystem(), req.getField())){
            return RespResult.error("system或field字段不能为空！");
        }
        return RespResult.success(EncryptUtil.encryptEmail(req.getField()));
    }

    @ApiOperation(value = "解密邮件", notes = "解密邮件")
    @PostMapping("decryptEmail")
    public RespResult<String> decryptEmail(@RequestBody EncryptDTO req) {
        if(StrUtil.hasBlank(req.getSystem(), req.getField())){
            return RespResult.error("system或field字段不能为空！");
        }
        return RespResult.success(EncryptUtil.decryptEmail(req.getField()));
    }

    @ApiOperation(value = "批量加密邮件", notes = "批量加密邮件")
    @PostMapping("batchEncryptEmails")
    public RespResult<Map<String,String>> batchEncryptEmails(@RequestBody EncryptDTO req) {
        if(StrUtil.hasBlank(req.getSystem()) || CollUtil.isEmpty(req.getFieldList())){
            return RespResult.error("system或fieldList字段不能为空！");
        }
        return RespResult.success(EncryptUtil.batchEncryptEmails(req.getFieldList()));
    }

    @ApiOperation(value = "批量解密邮件", notes = "批量解密邮件")
    @PostMapping("batchDecryptEmails")
    public RespResult<Map<String,String>> batchDecryptEmails(@RequestBody EncryptDTO req) {
        if(StrUtil.hasBlank(req.getSystem()) || CollUtil.isEmpty(req.getFieldList())){
            return RespResult.error("system或fieldList字段不能为空！");
        }
        return RespResult.success(EncryptUtil.batchDecryptEmails(req.getFieldList()));
    }

    @ApiOperation(value = "加密地址", notes = "加密地址")
    @PostMapping("encryptAddress")
    public RespResult<String> encryptAddress(@RequestBody EncryptDTO req) {
        if(StrUtil.hasBlank(req.getSystem(), req.getField())){
            return RespResult.error("system或field字段不能为空！");
        }
        return RespResult.success(EncryptUtil.encryptAddress(req.getField()));
    }

    @ApiOperation(value = "解密地址", notes = "解密地址")
    @PostMapping("decryptAddress")
    public RespResult<String> decryptAddress(@RequestBody EncryptDTO req) {
        if(StrUtil.hasBlank(req.getSystem(), req.getField())){
            return RespResult.error("system或field字段不能为空！");
        }
        return RespResult.success(EncryptUtil.decryptAddress(req.getField()));
    }

    @ApiOperation(value = "批量加密地址", notes = "批量加密地址")
    @PostMapping("batchEncryptAddresses")
    public RespResult<Map<String,String>> batchEncryptAddresses(@RequestBody EncryptDTO req) {
        if(StrUtil.hasBlank(req.getSystem()) || CollUtil.isEmpty(req.getFieldList())){
            return RespResult.error("system或fieldList字段不能为空！");
        }
        return RespResult.success(EncryptUtil.batchEncryptEmails(req.getFieldList()));
    }

    @ApiOperation(value = "批量解密地址", notes = "批量解密地址")
    @PostMapping("batchDecryptAddresses")
    public RespResult<Map<String,String>> batchDecryptAddresses(@RequestBody EncryptDTO req) {
        if(StrUtil.hasBlank(req.getSystem()) || CollUtil.isEmpty(req.getFieldList())){
            return RespResult.error("system或fieldList字段不能为空！");
        }
        return RespResult.success(EncryptUtil.batchDecryptAddresses(req.getFieldList()));
    }

    @ApiOperation(value = "加密银行卡", notes = "加密银行卡")
    @PostMapping("encryptBankCard")
    public RespResult<String> encryptBankCard(@RequestBody EncryptDTO req) {
        if(StrUtil.hasBlank(req.getSystem(), req.getField())){
            return RespResult.error("system或field字段不能为空！");
        }
        return RespResult.success(EncryptUtil.encryptBankCard(req.getField()));
    }

    @ApiOperation(value = "解密银行卡", notes = "解密银行卡")
    @PostMapping("decryptBankCard")
    public RespResult<String> decryptBankCard(@RequestBody EncryptDTO req) {
        if(StrUtil.hasBlank(req.getSystem(), req.getField())){
            return RespResult.error("system或field字段不能为空！");
        }
        return RespResult.success(EncryptUtil.decryptBankCard(req.getField()));
    }

    @ApiOperation(value = "批量加密银行卡", notes = "批量加密银行卡")
    @PostMapping("batchEncryptBankCards")
    public RespResult<Map<String,String>> batchEncryptBankCards(@RequestBody EncryptDTO req) {
        if(StrUtil.hasBlank(req.getSystem()) || CollUtil.isEmpty(req.getFieldList())){
            return RespResult.error("system或fieldList字段不能为空！");
        }
        return RespResult.success(EncryptUtil.batchEncryptBankCards(req.getFieldList()));
    }

    @ApiOperation(value = "批量解密银行卡", notes = "批量解密银行卡")
    @PostMapping("batchDecryptBankCards")
    public RespResult<Map<String,String>> batchDecryptBankCards(@RequestBody EncryptDTO req) {
        if(StrUtil.hasBlank(req.getSystem()) || CollUtil.isEmpty(req.getFieldList())){
            return RespResult.error("system或fieldList字段不能为空！");
        }
        return RespResult.success(EncryptUtil.batchDecryptBankCards(req.getFieldList()));
    }

    @ApiOperation(value = "加密银行卡", notes = "加密银行卡")
    @PostMapping("encryptIdCard")
    public RespResult<String> encryptIdCard(@RequestBody EncryptDTO req) {
        if(StrUtil.hasBlank(req.getSystem(), req.getField())){
            return RespResult.error("system或field字段不能为空！");
        }
        return RespResult.success(EncryptUtil.encryptIdCard(req.getField()));
    }

    @ApiOperation(value = "解密银行卡", notes = "解密银行卡")
    @PostMapping("decryptIdCard")
    public RespResult<String> decryptIdCard(@RequestBody EncryptDTO req) {
        if(StrUtil.hasBlank(req.getSystem(), req.getField())){
            return RespResult.error("system或field字段不能为空！");
        }
        return RespResult.success(EncryptUtil.decryptIdCard(req.getField()));
    }

    @ApiOperation(value = "批量加密银行卡", notes = "批量加密银行卡")
    @PostMapping("batchEncryptIdCards")
    public RespResult<Map<String,String>> batchEncryptIdCards(@RequestBody EncryptDTO req) {
        if(StrUtil.hasBlank(req.getSystem()) || CollUtil.isEmpty(req.getFieldList())){
            return RespResult.error("system或fieldList字段不能为空！");
        }
        return RespResult.success(EncryptUtil.batchEncryptIdCards(req.getFieldList()));
    }

    @ApiOperation(value = "批量解密银行卡", notes = "批量解密银行卡")
    @PostMapping("batchDecryptIdCards")
    public RespResult<Map<String,String>> batchDecryptIdCards(@RequestBody EncryptDTO req) {
        if(StrUtil.hasBlank(req.getSystem()) || CollUtil.isEmpty(req.getFieldList())){
            return RespResult.error("system或fieldList字段不能为空！");
        }
        return RespResult.success(EncryptUtil.batchDecryptIdCards(req.getFieldList()));
    }
}
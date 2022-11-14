package com.litian.dancechar.member.enums;

import com.litian.dancechar.framework.common.base.IRespResultCode;

/**
 * base模块返回码对象枚举
 *
 * @author tojson
 * @date 2021/6/21 21:25
 */
public enum BaseRespResultCode implements IRespResultCode {
    ERR_SOURCE_NOT_EMPTY(200000, "渠道不能为空", "渠道不能为空");

    /**
     * 错误码
     */
    private Integer code;

    /**
     * 错误消息
     */
    private String message;

    /**
     * 详细的错误消息(开发看)
     */
    private String detailMessage;

    BaseRespResultCode(Integer code, String message, String detailMessage) {
        this.code = code;
        this.message = message;
        this.detailMessage = detailMessage;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String getDetailMessage() {
        return detailMessage;
    }
}

package com.litian.dancechar.framework.common.base;

/**
 * 通用返回码对象(主要放各个模块的通用错误码)
 *
 * @author tojson
 * @date 2021/6/21 21:25
 */
public enum RespResultCode implements IRespResultCode {
    /**
     * 成功
     */
    OK(0, "成功", "成功"),
    /**
     * 系统异常
     */
    SYS_EXCEPTION(-1, "服务发生了一点小故障，请稍后重试", "系统异常"),
    /**
     * 参数不合法
     */
    ERR_PARAM_NOT_LEGAL(100000, "参数不合法", "参数不合法"),
    /**
     * 请勿重复操作
     */
    REPEATED_OPERATE(100001, "请勿重复操作", "请勿重复操作"),
    /**
     * 数据库有重复的记录
     */
    ERR_CONSTRAINT_VIOLATION(100002, "数据库记录重复", "数据库记录重复");

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

    RespResultCode(Integer code, String message, String detailMessage) {
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
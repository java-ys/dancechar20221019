package com.litian.dancechar.framework.common.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.litian.dancechar.framework.common.trace.TraceHelper;
import com.litian.dancechar.framework.common.util.SystemConfigUtil;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 通用返回结果对象
 *
 * @author tojson
 * @date 2021/6/13 21:25
 */
@Data
public class RespResult<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 结果码
     */
    private Integer code = RespResultCode.OK.getCode();

    /**
     * 结果信息
     */
    private String message = RespResultCode.OK.getMessage();

    /**
     * 详细的错误消息(开发看)
     */
    private String detailMessage = RespResultCode.OK.getDetailMessage();

    /**
     * 返回结果的数据对象
     */
    private T data;

    /**
     * 分布式链路traceId
     */
    private String traceId;

    /**
     * 分布式链路spanId
     */
    private String spanId;

    /**
     * 服务部署的环境
     */
    private String env;

    public RespResult() {
    }

    public RespResult(Integer code) {
        this.code = code;
    }

    public RespResult(Integer code, String message) {
        this.code = code;
        this.message = message;
        this.traceId = TraceHelper.getCurrentTrace().getTraceId();
        this.spanId = TraceHelper.getCurrentTrace().getSpanId();
        this.env = SystemConfigUtil.getEnv();
    }

    public RespResult(Integer code, String message, String detailMessage) {
        this.code = code;
        this.message = message;
        this.detailMessage = detailMessage;
        this.traceId = TraceHelper.getCurrentTrace().getTraceId();
        this.spanId = TraceHelper.getCurrentTrace().getSpanId();
        this.env = SystemConfigUtil.getEnv();
    }

    public RespResult(Integer code, String message, String detailMessage, String traceId, String spanId, String env) {
        this.code = code;
        this.message = message;
        this.detailMessage = detailMessage;
        this.traceId = traceId;
        this.spanId = spanId;
        this.env = env;
    }

    public RespResult(IRespResultCode respResultCode) {
        this.code = respResultCode.getCode();
        this.message = respResultCode.getMessage();
        this.detailMessage = respResultCode.getDetailMessage();
        this.traceId = TraceHelper.getCurrentTrace().getTraceId();
        this.spanId = TraceHelper.getCurrentTrace().getSpanId();
        this.env = SystemConfigUtil.getEnv();
    }

    public RespResult(IRespResultCode respResultCode, String traceId, String spanId, String env) {
        this.code = respResultCode.getCode();
        this.message = respResultCode.getMessage();
        this.detailMessage = respResultCode.getDetailMessage();
        this.traceId = traceId;
        this.spanId = spanId;
        this.env = env;
    }

    @JsonIgnore
    public boolean isOk() {
        return RespResultCode.OK.getCode().equals(code);
    }

    @JsonIgnore
    public boolean isNotOk() {
        return !this.isOk();
    }

    public static <T> RespResult<T> error(IRespResultCode apiResultCode) {
        return new RespResult<T>(apiResultCode);
    }

    public static <T> RespResult<T> error(IRespResultCode apiResultCode, T data) {
        return new RespResult<T>(apiResultCode).setData(data);
    }

    public static <T> RespResult<T> error(IRespResultCode apiResultCode, Object... params) {
        return new RespResult<T>(apiResultCode.getCode(), String.format(apiResultCode.getMessage(), params));
    }

    public static <T> RespResult<T> error(IRespResultCode apiResultCode, T data, Object... params) {
        return new RespResult<T>(apiResultCode.getCode(), String.format(apiResultCode.getMessage(), params))
                .setData(data);
    }

    public static <T> RespResult<T> error(String msg) {
        return new RespResult<T>(RespResultCode.SYS_EXCEPTION.getCode(), msg, msg);
    }

    public static <T> RespResult<T> error(String msg, String detailMessage) {
        return new RespResult<T>(RespResultCode.SYS_EXCEPTION.getCode(), msg, detailMessage);
    }

    public static <T> RespResult<T> error(String msg, T data) {
        return new RespResult<T>(RespResultCode.SYS_EXCEPTION.getCode(), msg, msg).setData(data);
    }

    public static <T> RespResult<T> error(String msg, String detailMessage, T data) {
        return new RespResult<T>(RespResultCode.SYS_EXCEPTION.getCode(), msg, detailMessage).setData(data);
    }

    public static <T> RespResult<T> error(Integer code, String msg) {
        return new RespResult<T>(code, msg, msg);
    }


    public static <T> RespResult<T> error(Integer code, String msg, String detailMessage) {
        return new RespResult<T>(code, msg, detailMessage);
    }

    public static <T> RespResult<T> error(Integer code, String msg, T data) {
        return new RespResult<T>(code, msg, msg).setData(data);
    }

    public static <T> RespResult<T> error(Integer code, String msg, String detailMessage, T data) {
        return new RespResult<T>(code, msg, detailMessage).setData(data);
    }

    public static <T> RespResult<T> error(List<Object> codeAndMsgList) {
        return new RespResult<>(Integer.valueOf(codeAndMsgList.get(0).toString()),
                codeAndMsgList.get(1).toString(), codeAndMsgList.get(1).toString());
    }

    public static <T> RespResult<T> error(List<Object> codeAndMsgList, T data) {
        RespResult<T> ar = error(codeAndMsgList);
        ar.setData(data);
        return ar;
    }

    public static RespResult<Void> success() {
        return new RespResult<>(RespResultCode.OK);
    }

    public static <T> RespResult<T> success(T data) {
        RespResult<T> ar = new RespResult<T>(RespResultCode.OK);
        ar.setData(data);
        return ar;
    }

    public RespResult<T> setRespMessage(IRespResultCode respResultCode) {
        this.code = respResultCode.getCode();
        this.message = respResultCode.getMessage();
        this.detailMessage = respResultCode.getDetailMessage();
        return this;
    }

    public String getMessage() {
        return message;
    }

    public RespResult<T> setMessage(String msg) {
        this.message = msg;
        return this;
    }

    public String getDetailMessage() {
        return detailMessage;
    }

    public RespResult<T> setDetailMessage(String detailMessage) {
        this.detailMessage = detailMessage;
        return this;
    }

    public RespResult<T> setMessage(String msg, Object... params) {
        this.message = String.format(msg, params);
        return this;
    }

    public T getData() {
        return data;
    }

    public RespResult<T> setData(T data) {
        this.data = data;
        return this;
    }
}

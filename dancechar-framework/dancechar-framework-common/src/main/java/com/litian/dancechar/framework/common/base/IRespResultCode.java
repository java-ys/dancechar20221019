package com.litian.dancechar.framework.common.base;

/**
 * 基础的返回码对象(基础的返回码)
 *
 * @author tojson
 * @date 2021/6/14 9:25
 */
public interface IRespResultCode {
    /**
     * 结果码
     */
    Integer getCode();

    /**
     * 返回消息
     */
    String getMessage();

    /**
     * 详细的错误消息(开发看)
     */
    String getDetailMessage();
}
package com.litian.dancechar.core.biz.activity.act20220910.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 中秋活动请求对象
 *
 * @author tojson
 * @date 2022/7/9 06:18
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Act20220910ReqDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 活动Id
     */
    private String actId;

    /**
     * 客户Id
     */
    private String customerId;

    /**
     * 任务Id
     */
    private String taskId;
}
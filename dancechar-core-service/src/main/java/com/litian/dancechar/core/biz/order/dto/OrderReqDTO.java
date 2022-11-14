package com.litian.dancechar.core.biz.order.dto;

import com.litian.dancechar.framework.common.base.BasePage;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 订单请求对象
 *
 * @author tojson
 * @date 2022/7/9 06:18
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OrderReqDTO extends BasePage implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 客户手机号
     */
    private String mobile;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 是否查询取消订单
     */
    private Integer isCancel;

    /**
     * 限制的记录
     */
    private Integer limitRecords;
}
package com.litian.dancechar.core.biz.order.dto;

import com.litian.dancechar.framework.common.base.BaseRespDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * 活动领取返回对象
 *
 * @author tojson
 * @date 2022/7/9 06:18
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OrderRespDTO extends BaseRespDTO implements Serializable {
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
     * 状态(1-已下单 2-已支付 3-退款 4-订单取消)
     */
    private Integer status;

    /**
     * 订单时间
     */
    private Date orderTime;

    /**
     * 支付时间
     */
    private Date payTime;

    /**
     * 退款时间
     */
    private Date refundTime;

    /**
     * 取消订单时间
     */
    private Date cancelOrderTime;
}
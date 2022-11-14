package com.litian.dancechar.core.biz.order.enums;

/**
 * 订单状态枚举
 *
 * @author tojson
 * @date 2021/6/21 21:25
 */
public enum OrderStatusEnum {
    /**
     * 已下单
     */
    CREATED(1, "已下单"),
    /**
     * 已支付
     */
    PAYED(2, "已支付"),
    /**
     * 已退款
     */
    REFUND(3, "已退款"),
    /**
     * 订单取消
     */
    CANCEL(4, "已取消"),
    ;

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 描述信息
     */
    private String message;

    OrderStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}

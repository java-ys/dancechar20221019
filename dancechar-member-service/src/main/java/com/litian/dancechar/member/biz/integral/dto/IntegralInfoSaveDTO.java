package com.litian.dancechar.member.biz.integral.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 会员积分保存对象
 *
 * @author tojson
 * @date 2022/9/6 11:18
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class IntegralInfoSaveDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 客户Id
     */
    @NotEmpty(message = "customerId不能为空")
    private String customerId;

    /**
     * 积分流水号
     */
    @NotEmpty(message = "serialNo不能为空")
    private String serialNo;

    /**
     * 操作类型，0:加积分 1:扣减积分
     */
    @NotNull(message = "operateType不能为空")
    private Integer operateType;

    /**
     * 操作数量
     */
    @NotNull(message = "operateNum不能为空")
    private Integer operateNum;
}
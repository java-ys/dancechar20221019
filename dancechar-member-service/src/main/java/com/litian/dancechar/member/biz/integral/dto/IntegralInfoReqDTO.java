package com.litian.dancechar.member.biz.integral.dto;

import com.litian.dancechar.framework.common.base.BasePage;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * 会员积分请求对象
 *
 * @author tojson
 * @date 2022/7/9 06:18
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class IntegralInfoReqDTO extends BasePage implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 主键Ids
     */
    private List<String> ids;

    /**
     * 客户Id
     */
    private String customerId;

    /**
     * 积分流水号
     */
    private String serialNo;

    /**
     * 操作类型，0:加积分 1:扣减积分
     */
    private Integer operateType;

    /**
     * 操作数量
     */
    private Integer operateNum;
}
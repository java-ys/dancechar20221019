package com.litian.dancechar.member.biz.customer.dto;

import com.litian.dancechar.framework.common.base.BaseRespDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 会员信息返回对象
 *
 * @author tojson
 * @date 2022/7/9 06:18
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CustomerInfoRespDTO extends BaseRespDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 客户Id
     */
    private String customerId;

    /**
     * 客户手机号
     */
    private String mobile;

    /**
     * 姓名
     */
    private String realName;
}
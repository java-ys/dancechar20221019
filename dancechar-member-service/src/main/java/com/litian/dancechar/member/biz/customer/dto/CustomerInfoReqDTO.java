package com.litian.dancechar.member.biz.customer.dto;

import com.litian.dancechar.framework.common.base.BasePage;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * 会员信息请求对象
 *
 * @author tojson
 * @date 2022/7/9 06:18
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CustomerInfoReqDTO extends BasePage implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 主键Ids
     */
    private List<String> ids;

    /**
     * 客户手机号
     */
    private String mobile;

    /**
     * 姓名
     */
    private String realName;
}
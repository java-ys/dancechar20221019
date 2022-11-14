package com.litian.dancechar.member.biz.customer.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.litian.dancechar.framework.common.mybatis.BaseDO;
import com.litian.dancechar.framework.encrypt.annotation.EncryptClass;
import com.litian.dancechar.framework.encrypt.annotation.EncryptField;
import com.litian.dancechar.framework.encrypt.enums.EncryptTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 会员信息DO
 *
 * @author tojson
 * @date 2022/9/5 06:18
 */
@Data
@TableName("customer_info")
@EqualsAndHashCode(callSuper = false)
@EncryptClass
public class CustomerInfoDO extends BaseDO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 客户手机号
     */
    @EncryptField(value = EncryptTypeEnum.MOBILE)
    private String mobile;

    /**
     * 姓名
     */
    private String realName;
}
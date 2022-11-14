package com.litian.dancechar.core.biz.blacklist.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.litian.dancechar.framework.common.mybatis.BaseDO;
import com.litian.dancechar.framework.encrypt.annotation.EncryptClass;
import com.litian.dancechar.framework.encrypt.annotation.EncryptField;
import com.litian.dancechar.framework.encrypt.enums.EncryptTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 黑名单请求DO
 *
 * @author tojson
 * @date 2021/6/19 11:17
 */
@Data
@TableName("sys_black_list")
@EqualsAndHashCode(callSuper = false)
@EncryptClass
public class SysBlackListDO extends BaseDO {

    /**
     * 主键
     */
    private String id;

    /**
     * 来源(1-管理后台  2-前端应用)
     */
    private String source;

    /**
     * 请求url
     */
    private String reqUrl;

    /**
     * 链接名称
     */
    private String blackName;

    /**
     * 手机号
     */
    @EncryptField(value = EncryptTypeEnum.MOBILE)
    private String mobile;

    /**
     * 备注
     */
    private String remark;
}
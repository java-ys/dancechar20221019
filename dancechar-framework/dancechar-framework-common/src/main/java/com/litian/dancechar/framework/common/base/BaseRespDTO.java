package com.litian.dancechar.framework.common.base;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 基本返回信息DTO(常见的几个字段)
 *
 * @author tojson
 * @date 2021/6/19 20:53
 */
@Data
public class BaseRespDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 更新人
     */
    private String updateUser;

    /**
     * 更新人姓名
     */
    private String updateUserName;

    /**
     * 更新时间
     */
    private Date updateDate;

    /**
     * 创建人IP
     */
    private String createIp;

    /**
     * 更新人IP
     */
    private String updateIp;

    /**
     * 删除标志-1: 已删除 0：未删除
     */
    private Integer deleteFlag;

    /**
     * 创建人姓名
     */
    private String createUserName;
}

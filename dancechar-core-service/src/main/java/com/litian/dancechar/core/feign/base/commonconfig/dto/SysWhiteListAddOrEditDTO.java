package com.litian.dancechar.core.feign.base.commonconfig.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 白名单-新增或修改DTO
 *
 * @author tojson
 * @date 2021/6/17 23:53
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysWhiteListAddOrEditDTO extends SysWhiteListCommonDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;
}

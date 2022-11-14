package com.litian.dancechar.core.feign.base.commonconfig.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 白名单返回對象
 *
 * @author tojson
 * @date 2021/6/19 13:20
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysWhiteListRespDTO extends SysWhiteListCommonDTO implements Serializable {
    private static final long serialVersionUID = 1L;

}
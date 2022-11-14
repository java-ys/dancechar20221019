package com.litian.dancechar.core.biz.blacklist.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 黑名单返回對象
 *
 * @author tojson
 * @date 2021/6/19 11:17
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysBlackListRespDTO extends SysBlackListCommonDTO implements Serializable {
    private static final long serialVersionUID = 1L;

}
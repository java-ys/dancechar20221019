package com.litian.dancechar.core.biz.blacklist.dto;

import com.litian.dancechar.framework.common.base.BasePage;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 黑名单-分页请求对象
 *
 * @author tojson
 * @date 2021/6/19 11:17
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysBlackListReqDTO extends BasePage implements Serializable {
    private static final long serialVersionUID = 1L;

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
     * 是否黑名单
     */
    private Integer isBlack;
}
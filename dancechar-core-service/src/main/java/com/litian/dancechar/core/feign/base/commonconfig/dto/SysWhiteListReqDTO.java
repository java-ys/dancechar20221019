package com.litian.dancechar.core.feign.base.commonconfig.dto;

import com.litian.dancechar.framework.common.base.BasePage;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 白名单-分页请求对象
 *
 * @author tojson
 * @date 2021/6/19 13:20
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysWhiteListReqDTO extends BasePage implements Serializable {
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
    private String whiteName;
}
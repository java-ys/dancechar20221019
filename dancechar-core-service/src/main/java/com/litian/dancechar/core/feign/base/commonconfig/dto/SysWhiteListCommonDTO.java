package com.litian.dancechar.core.feign.base.commonconfig.dto;

import com.litian.dancechar.framework.common.base.BaseRespDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 白名单-公共DTO
 *
 * @author tojson
 * @date 2021/6/17 20:53
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysWhiteListCommonDTO extends BaseRespDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 来源(1-管理后台  2-前端应用)
     */
    @NotNull(message = "source不能为空")
    private String source;

    /**
     * 请求url
     */
    @NotNull(message = "请求url不能为空")
    private String reqUrl;

    /**
     * 链接名称
     */
    private String whiteName;

    /**
     * 备注
     */
    private String remark;
}

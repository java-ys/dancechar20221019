package com.litian.dancechar.framework.common.context;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 用户访问信息
 *
 * @author tojson
 * @date 2021/6/22 09:51
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UserAccessInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户登录Id", name = "loginId")
    private String loginId;

    @ApiModelProperty(value = "客户端IP", name = "ip")
    private String ip;

    @ApiModelProperty(value = "版本号", name = "version")
    private String version;
}

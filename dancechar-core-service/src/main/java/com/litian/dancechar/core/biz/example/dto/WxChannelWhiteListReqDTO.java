package com.litian.dancechar.core.biz.example.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 微信渠道请求DTO
 *
 * @author tojson
 * @date 2021/6/5 12:43
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class WxChannelWhiteListReqDTO implements Serializable {

    /**
     * 来源(1-管理后台  2-前端应用)
     */
    @NotNull(message = "source不能为空")
    private String source;
}

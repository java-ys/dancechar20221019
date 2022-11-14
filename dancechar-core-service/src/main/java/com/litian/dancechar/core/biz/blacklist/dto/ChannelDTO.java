package com.litian.dancechar.core.biz.blacklist.dto;

import com.litian.dancechar.framework.common.validator.idcard.IdCard;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 渠道DTO
 *
 * @author tojson
 * @date 2021/6/19 11:17
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ChannelDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 渠道名称
     */
    @NotBlank(message = "渠道名称不能为空")
    @IdCard(message = "身份证格式不对")
    private String channelName;
}

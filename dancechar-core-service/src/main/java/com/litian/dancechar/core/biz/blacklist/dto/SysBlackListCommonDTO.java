package com.litian.dancechar.core.biz.blacklist.dto;

import com.litian.dancechar.framework.common.base.BaseRespDTO;
import com.litian.dancechar.framework.encrypt.annotation.DecryptClass;
import com.litian.dancechar.framework.encrypt.annotation.DecryptField;
import com.litian.dancechar.framework.encrypt.enums.EncryptTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 黑名单-公共DTO
 *
 * @author tojson
 * @date 2021/6/19 11:17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@DecryptClass
public class SysBlackListCommonDTO extends BaseRespDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 来源(1-管理后台  2-前端应用)
     */
    @NotBlank(message = "来源不能为空")
    private String source;

    /**
     * 请求url
     */
    @NotBlank(message = "请求url不能为空")
    private String reqUrl;

    /**
     * 链接名称
     */
    private String blackName;

    /**
     * 手机号
     */
    @DecryptField(value = EncryptTypeEnum.MOBILE)
    private String mobile;

    /**
     * 备注
     */
    private String remark;

    /**
     * 渠道列表
     */
    @Valid
    @NotNull(message = "渠道列表不能为空")
    private List<ChannelDTO> channelList;
}

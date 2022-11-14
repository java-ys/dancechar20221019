package com.litian.dancechar.core.biz.blacklist.dto;

import com.litian.dancechar.framework.common.validator.groups.Update;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 黑名单-新增或修改DTO
 *
 * @author tojson
 * @date 2021/6/19 11:17
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysBlackListAddOrEditDTO extends SysBlackListCommonDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @NotBlank(message = "id不能为空", groups = Update.class)
    private String id;
}

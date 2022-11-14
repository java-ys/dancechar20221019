package com.litian.dancechar.core.biz.activity.info.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 活动发布对象
 *
 * @author tojson
 * @date 2022/9/6 11:18
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ActInfoPublishDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @NotBlank(message = "id不能为空")
    private String id;

    /**
     * 开始时间
     */
    @NotNull(message = "startTime不能为空")
    private Date startTime;

    /**
     * 结束时间
     */
    @NotNull(message = "endTime不能为空")
    private Date endTime;
}
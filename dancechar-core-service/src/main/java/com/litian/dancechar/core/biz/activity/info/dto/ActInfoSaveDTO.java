package com.litian.dancechar.core.biz.activity.info.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 活动保存对象
 *
 * @author tojson
 * @date 2022/9/6 11:18
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ActInfoSaveDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 活动编码
     */
    private String actNo;

    /**
     * 活动名称
     */
    @NotBlank(message = "actName不能为空")
    private String actName;

    /**
     * 状态(1-未发布 2-已发布 3-进行中 4-已结束)
     */
    private Integer status;

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

    /**
     * 活动规则
     */
    @NotNull(message = "actRule不能为空")
    private String actRule;
}
package com.litian.dancechar.core.biz.activity.info.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.util.Date;

/**
 * 活动基本信息对象
 *
 * @author tojson
 * @date 2022/7/9 06:18
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ActBaseInfoDTO implements Serializable {
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
    private String actName;

    /**
     * 状态(1-未发布 2-已发布 3-进行中 4-已结束)
     */
    private Integer status;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 活动规则
     */
    private String actRule;
}
package com.litian.dancechar.core.biz.activity.info.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;

/**
 * 活动查询对象
 *
 * @author tojson
 * @date 2022/7/9 06:18
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ActInfoQueryDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 活动编码
     */
    private String actNo;

    /**
     * 状态(1-未发布 2-已发布 3-进行中 4-已结束)
     */
    private Integer status;
}
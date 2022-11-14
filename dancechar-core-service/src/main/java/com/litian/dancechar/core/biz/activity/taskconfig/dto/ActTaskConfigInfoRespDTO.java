package com.litian.dancechar.core.biz.activity.taskconfig.dto;

import com.litian.dancechar.framework.common.base.BaseRespDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 活动任务配置返回对象
 *
 * @author tojson
 * @date 2022/7/9 06:18
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ActTaskConfigInfoRespDTO extends BaseRespDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 活动Id
     */
    private String actId;

    /**
     * 活动编码
     */
    private String actCode;

    /**
     * 任务Id
     */
    private String taskId;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 奖励物品类型
     */
    private String itemType;

    /**
     * 奖励物品Id
     */
    private String itemId;

    /**
     * 奖励物品数量
     */
    private Integer itemNum;
}
package com.litian.dancechar.core.biz.activity.taskconfig.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.litian.dancechar.framework.common.mybatis.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 活动任务配置信息DO
 *
 * @author tojson
 * @date 2022/9/5 06:18
 */
@Data
@TableName("act_task_config_info")
@EqualsAndHashCode(callSuper = false)
public class ActTaskConfigInfoDO extends BaseDO implements Serializable {
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
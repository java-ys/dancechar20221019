package com.litian.dancechar.core.biz.activity.itemrecords.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 活动领取保存对象
 *
 * @author tojson
 * @date 2022/9/6 11:18
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ActItemRecordsSaveDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 客户Id
     */
    private String customerId;

    /**
     * 活动Id
     */
    private String actId;

    /**
     * 活动编码
     */
    private String actCode;

    /**
     * 物品Id
     */
    private String itemId;

    /**
     * 物品流水号
     */
    private String itemSerialNo;

    /**
     * 操作物品类型
     */
    private String operateItemType;

    /**
     * 操作物品数量
     */
    private Integer operateItemNum;
}
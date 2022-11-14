package com.litian.dancechar.core.biz.activity.info.enums;

import lombok.Getter;

/**
 * 活动状态
 *
 * @author tojson
 * @date 2022/10/06 22:18
 */
@Getter
public enum ActStatusEnum {
    /**
     * 未发布
     */
    NOT_PUBLISH(1, "未发布"),
    /**
     * 已发布
     */
    PUBLISH(2, "已发布"),
    /**
     * 进行中
     */
    STARTING(3, "进行中"),
    /**
     * 已结束
     */
    END(4, "已结束");

    ActStatusEnum(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }

    private Integer code;

    private String msg;
}

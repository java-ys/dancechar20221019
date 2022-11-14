package com.litian.dancechar.core.common.enums;

/**
 * redis消息主题枚举
 *
 * @author tojson
 * @date 2022/7/28 12:20
 */
public enum MessageChannelEnum {

    /**
     * 活动主题
     */
    ACT_CHANNEL("actChannel", "活动主题");

    private final String code;

    private final String name;

    MessageChannelEnum(String code, String name){
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}

package com.litian.dancechar.base.framework.redislistener.common;

/**
 * redis主题枚举
 *
 * @author tojson
 * @date 2022/7/28 12:20
 */
public enum RedisChannelEnum {

    /**
     * 测试主题
     */
    TEST_CHANNEL("testChannel", "测试主题"),
    /**
     * 例子主题
     */
    EXAMPLE_CHANNEL("exampleChannel", "例子主题");

    private final String code;

    private final String name;

    RedisChannelEnum(String code, String name){
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

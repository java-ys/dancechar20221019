package com.litian.dancechar.framework.common.thread;

/**
 * 线程池类型
 *
 * @author tojson
 * @date 2022/7/28 12:20
 */
public enum ThreadPoolTypeEnum {

    /**
     * cpu密集型
     */
    CPU("cpu", "cpu密集型"),
    /**
     * io密集型
     */
    IO("io", "io密集型"),
    ;

    private final String code;

    private final String name;

    ThreadPoolTypeEnum(String code, String name){
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

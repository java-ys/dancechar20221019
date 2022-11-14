package com.litian.dancechar.framework.delaymsg.netty;

import cn.hutool.core.util.IdUtil;
import lombok.Data;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 任务VO
 *
 * @author tojson
 * @date 2022/09/28 21:53
 */
@Data
public class TaskVO<T> {

    /**
     * 任务Id
     */
    private String id = IdUtil.objectId();

    /**
     * 重试次数
     */
    private int retryCount = 3;

    /**
     * 延时时间戳
     */
    private long timestamp;

    /**
     * 未来的某个时间执行
     */
    private Date futureTime;

    /**
     * 时间单位
     */
    private TimeUnit timeUnit;

    /**
     * 传递的数据
     */
    private T data;
}

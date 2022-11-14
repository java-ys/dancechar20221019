package com.litian.dancechar.framework.common.thread;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * 线程池参数配置
 *
 * @author tojson
 * @date 2021/6/14 22:28
 */
@Data
public class ThreadPoolConfig {
    private Integer threadPoolCorePoolSize;

    private Integer threadPoolMaxPoolSize;

    private Integer threadPoolQueueCapacity;

    private Boolean waitForTasksToCompleteOnShutdown;

    private Integer awaitTerminationSeconds;
}

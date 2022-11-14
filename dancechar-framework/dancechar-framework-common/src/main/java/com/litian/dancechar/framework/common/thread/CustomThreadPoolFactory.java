package com.litian.dancechar.framework.common.thread;

import cn.hutool.extra.spring.SpringUtil;
import com.litian.dancechar.framework.common.util.SpringContextHoldUtil;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 自定义线程池-自定义线程池工厂（重写了线程池，包装了traceId）
 *
 * @author tojson
 * @date 2021/6/14 22:28
 */
public class CustomThreadPoolFactory {

    /**
     * 创建指定大小的线程池
     * @param nThreads 线程池大小
     * @return 默认的线程池
     */
    public static ThreadPoolTaskExecutor newFixedThreadPool(Integer nThreads) {
        return new ThreadPoolCreate().getTaskExecutor(nThreads);
    }

    /**
     * 根据不同的线程池类型创建默认的线程池
     * @param threadPoolTypeEnum 线程池类型(IO密集型、CPU密集型)
     * @return 默认的线程池
     */
    public static ThreadPoolTaskExecutor newThreadPoolByType(ThreadPoolTypeEnum threadPoolTypeEnum) {
        return new ThreadPoolCreate().getTaskExecutorByType(threadPoolTypeEnum);
    }

    /**
     * 根据线程池的配置创建指定的线程池
     * @param threadPoolConfig 线程池配置
     * @return 指定的线程池
     */
    public static ThreadPoolTaskExecutor newDefaultFixedThreadPool(ThreadPoolConfig threadPoolConfig) {
        return new ThreadPoolCreate(threadPoolConfig).getTaskExecutor(null);
    }
}
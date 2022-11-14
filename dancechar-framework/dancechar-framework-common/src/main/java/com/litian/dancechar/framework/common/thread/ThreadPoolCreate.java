package com.litian.dancechar.framework.common.thread;

import cn.hutool.core.util.StrUtil;
import com.litian.dancechar.framework.common.trace.Trace;
import com.litian.dancechar.framework.common.trace.TraceHelper;
import com.litian.dancechar.framework.common.util.SpringContextHoldUtil;
import org.slf4j.MDC;
import org.springframework.context.annotation.DependsOn;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import javax.annotation.Resource;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 自定义线程池-重写线程池的创建
 *
 * @author tojson
 * @date 2021/6/14 22:28
 */
public class ThreadPoolCreate {
    private ThreadPoolConfig threadPoolConfig;

    public ThreadPoolCreate(){
        int cpuNum = Runtime.getRuntime().availableProcessors();
        threadPoolConfig = new ThreadPoolConfig();
        threadPoolConfig.setThreadPoolCorePoolSize(cpuNum);
        threadPoolConfig.setThreadPoolMaxPoolSize(cpuNum);
        threadPoolConfig.setThreadPoolQueueCapacity(65535);
        threadPoolConfig.setWaitForTasksToCompleteOnShutdown(true);
        threadPoolConfig.setAwaitTerminationSeconds(60);
    }
    public ThreadPoolCreate(ThreadPoolConfig customThreadPoolConfig){
        if(customThreadPoolConfig == null){
            int cpuNum = Runtime.getRuntime().availableProcessors();
            threadPoolConfig = new ThreadPoolConfig();
            threadPoolConfig.setThreadPoolCorePoolSize(cpuNum);
            threadPoolConfig.setThreadPoolMaxPoolSize(cpuNum);
            threadPoolConfig.setThreadPoolQueueCapacity(65535);
            threadPoolConfig.setWaitForTasksToCompleteOnShutdown(true);
            threadPoolConfig.setAwaitTerminationSeconds(60);
        }else{
            this.threadPoolConfig = customThreadPoolConfig;
        }
    }

    public ThreadPoolTaskExecutor getTaskExecutorByType(ThreadPoolTypeEnum threadPoolTypeEnum) {
        int cpuNum = Runtime.getRuntime().availableProcessors();
        if(ThreadPoolTypeEnum.CPU.getCode().equals(threadPoolTypeEnum.getCode())){
            return getTaskExecutor(cpuNum);
        }
        if(ThreadPoolTypeEnum.IO.getCode().equals(threadPoolTypeEnum.getCode())){
            return getTaskExecutor(2*cpuNum);
        }
        return getTaskExecutor(cpuNum);
    }

    public ThreadPoolTaskExecutor getTaskExecutor(Integer nThreads) {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor() {
            private static final long serialVersionUID = 1L;

            @Override
            public void execute(Runnable task, long startTimeout) {
                super.execute(wrap(task, MDC.get(Trace.TRACE)));
            }

            @Override
            public void execute(Runnable task) {
                super.execute(wrap(task, MDC.get(Trace.TRACE)));
            }

            @Override
            public <T> Future<T> submit(Callable<T> task) {
                return super.submit(wrap(task, MDC.get(Trace.TRACE)));
            }

            @Override
            public Future<?> submit(Runnable task) {
                return super.submit(wrap(task, MDC.get(Trace.TRACE)));
            }

            @Override
            public <T> ListenableFuture<T> submitListenable(Callable<T> task) {
                return super.submitListenable(wrap(task, MDC.get(Trace.TRACE)));
            }

            @Override
            public ListenableFuture<?> submitListenable(Runnable task) {
                return super.submitListenable(wrap(task, MDC.get(Trace.TRACE)));
            }
        };
        if (null == nThreads) {
            // 设置核心线程数
            executor.setCorePoolSize(threadPoolConfig.getThreadPoolCorePoolSize());
            // 设置最大线程数
            executor.setMaxPoolSize(threadPoolConfig.getThreadPoolMaxPoolSize());
        } else {
            executor.setCorePoolSize(nThreads);
            executor.setMaxPoolSize(nThreads);
        }
        // 设置队列容量
        executor.setQueueCapacity(threadPoolConfig.getThreadPoolQueueCapacity());
        // 设置拒绝策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 等待所有的任务结束后再关闭线程池
        executor.setWaitForTasksToCompleteOnShutdown(threadPoolConfig.getWaitForTasksToCompleteOnShutdown());
        // 等待指定时间没关闭自动关闭
        executor.setAwaitTerminationSeconds(threadPoolConfig.getAwaitTerminationSeconds());
        executor.initialize();
        return executor;
    }

    public static <T> Callable<T> wrap(final Callable<T> callable, final String traceId) {
        return () -> {
            if (StrUtil.isNotEmpty(traceId)) {
                TraceHelper.setCurrentTrace(traceId);
            }
            try {
                return callable.call();
            } finally {
                TraceHelper.clearCurrentTrace();
            }
        };
    }

    public static Runnable wrap(final Runnable runnable, final String traceId) {
        return () -> {
            if (StrUtil.isNotEmpty(traceId)) {
                TraceHelper.setCurrentTrace(traceId);
            }
            try {
                runnable.run();
            } finally {
                TraceHelper.clearCurrentTrace();
            }
        };
    }
}
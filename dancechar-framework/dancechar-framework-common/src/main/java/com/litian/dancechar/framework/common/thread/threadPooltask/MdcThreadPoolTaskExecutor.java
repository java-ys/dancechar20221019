package com.litian.dancechar.framework.common.thread.threadPooltask;

import com.litian.dancechar.framework.common.thread.ThreadPoolConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.Resource;
import java.util.concurrent.ThreadPoolExecutor;

@EnableAsync
@Configuration
public class MdcThreadPoolTaskExecutor {
    @Bean(name = "threadPoolTaskExecutor")
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        int cpuNum = Runtime.getRuntime().availableProcessors();
        executor.setMaxPoolSize(cpuNum);
        executor.setCorePoolSize(cpuNum);
        executor.setQueueCapacity(65535);
        executor.setKeepAliveSeconds(300);
        executor.setTaskDecorator(new MdcTaskDecorator());
        /**
         * 拒绝处理策略
         * CallerRunsPolicy()：交由调用方线程运行，比如main线程。
         * AbortPolicy()：直接抛出异常。
         * DiscardPolicy()：直接丢弃。
         * DiscardOldestPolicy()：丢弃队列中最老的任务。
         */
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }
}

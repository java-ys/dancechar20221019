package com.litian.dancechar.base.init;

import com.litian.dancechar.framework.common.thread.CustomThreadPoolFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

/**
 * 线程池初始化(不同的业务使用不同的线程池)
 *
 * @author tojson
 * @date 2022/8/28 23:25
 */
@Component
public class ThreadPoolInit {
    /**
     * 公共的线程池
     */
    private static ThreadPoolTaskExecutor threadPoolTaskExecutor =
            CustomThreadPoolFactory.newFixedThreadPool(16);

    /**
     * 学生业务线程池
     */
    private static ThreadPoolTaskExecutor studentThreadPoolTaskExecutor =
            CustomThreadPoolFactory.newFixedThreadPool(8);

    public static ThreadPoolTaskExecutor getThreadPoolTaskExecutor() {
        return threadPoolTaskExecutor;
    }

    public static ThreadPoolTaskExecutor getStudentThreadPoolTaskExecutor() {
        return studentThreadPoolTaskExecutor;
    }
}

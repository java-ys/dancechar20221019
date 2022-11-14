package com.litian.dancechar.core.init;

import com.litian.dancechar.framework.common.thread.CustomThreadPoolFactory;
import com.litian.dancechar.framework.common.thread.ThreadPoolCreate;
import com.litian.dancechar.framework.common.util.SpringContextHoldUtil;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

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
     * 黑名单业务线程池
     */
    private static ThreadPoolTaskExecutor blackListThreadPoolTaskExecutor =
            CustomThreadPoolFactory.newFixedThreadPool(8);

    public  static ThreadPoolTaskExecutor getThreadPoolTaskExecutor() {
        return threadPoolTaskExecutor;
    }

    public static ThreadPoolTaskExecutor getBlackListThreadPoolTaskExecutor() {
        return blackListThreadPoolTaskExecutor;
    }
}

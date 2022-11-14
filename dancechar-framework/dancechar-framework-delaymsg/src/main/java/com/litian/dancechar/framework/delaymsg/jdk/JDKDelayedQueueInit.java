package com.litian.dancechar.framework.delaymsg.jdk;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.litian.dancechar.framework.common.trace.TraceHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 基于jdk DelayQueue初始化队列监听
 *
 * @author tojson
 * @date 2022/09/28 23:53
 */
@Slf4j
@Component
public class JDKDelayedQueueInit implements ApplicationContextAware {

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, JDKDelayedQueueListener> map = applicationContext.getBeansOfType(JDKDelayedQueueListener.class);
        for (Map.Entry<String, JDKDelayedQueueListener> taskEventListenerEntry : map.entrySet()) {
            String queueName = taskEventListenerEntry.getValue().getClass().getName();
            startThread(queueName, taskEventListenerEntry.getValue());
        }
    }

    /**
     * 启动线程获取队列
     *
     * @param queueName                   queueName
     * @param jdkDelayedQueueListener     任务回调监听
     */
    private <T> void startThread(String queueName, JDKDelayedQueueListener jdkDelayedQueueListener) {
        //由于此线程需要常驻，可以新建线程，不用交给线程池管理
        Thread thread = new Thread(() -> {
            log.info("启动监听队列线程: {}", queueName);
            while (true) {
                try {
                    JDKDelayTask<T> jdkDelayTask = JDKDelayQueue.delayQueue.take();
                    if(ObjectUtil.isNull(jdkDelayTask)){
                        try{
                            // 睡眠一下，防止竞争太激烈
                            Thread.sleep(ThreadLocalRandom.current().nextInt(500, 1000));
                        }catch (Exception e){
                            log.error(e.getMessage(),e);
                        }
                        continue;
                    }
                    T t = jdkDelayTask.getBody();
                    if(t == null){
                        continue;
                    }
                    // 重新设置traceId
                    TraceHelper.getCurrentTrace();
                    long start = System.currentTimeMillis();
                    log.info("开始执行业务逻辑，data:{}", JSONUtil.toJsonStr(t));
                    jdkDelayedQueueListener.execute(t);
                    log.info("执行业务逻辑结束, 总耗时:{}ms", System.currentTimeMillis() - start);
                } catch (Exception e) {
                    log.error("监听队列线程系统异常! errMsg:{}", e.getMessage() ,e);
                }
            }
        });
        thread.setName(queueName);
        thread.setDaemon(true);
        thread.start();
    }
}

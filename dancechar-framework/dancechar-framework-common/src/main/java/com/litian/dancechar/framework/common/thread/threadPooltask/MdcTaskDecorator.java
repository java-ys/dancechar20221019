package com.litian.dancechar.framework.common.thread.threadPooltask;

import com.litian.dancechar.framework.common.trace.Trace;
import com.litian.dancechar.framework.common.trace.TraceHelper;
import org.slf4j.MDC;
import org.springframework.core.task.TaskDecorator;

/**
 * 任务适配器MdcTaskDecorator
 *
 * @author tojson
 * @date 2021/6/14 22:28
 */
public class MdcTaskDecorator implements TaskDecorator {

    /**
     * 使异步线程池获得主线程的上下文
     */
    @Override
    public Runnable decorate(Runnable runnable) {
        //Map<String,String> map = MDC.getCopyOfContextMap();
        return () -> {
            try{
                // MDC.setContextMap(map);
                MDC.put(Trace.PARENT_SPAN, TraceHelper.genSpanId());
                runnable.run();
            } finally {
                MDC.clear();
            }
        };
    }
}

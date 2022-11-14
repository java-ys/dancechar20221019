package com.litian.dancechar.framework.common.trace;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.generator.SnowflakeGenerator;
import cn.hutool.core.util.StrUtil;
import com.litian.dancechar.framework.common.util.DCMd5Util;
import org.slf4j.MDC;
import org.springframework.core.NamedThreadLocal;

import java.util.UUID;

/**
 * traceId和spanId工具类
 *
 * @author tojson
 * @date 2022/6/11 23:15
 */
public class TraceHelper {
    public static final ThreadLocal<Trace> CURRENT_SPAN = new NamedThreadLocal<>("TraceId Context");

    public static String genTraceId(){
        return DCMd5Util.getTraceId(UUID.randomUUID().toString());
    }

    public static String genSpanId(){
        return Convert.toStr(new SnowflakeGenerator().next());
    }

    public static String getTraceId(){
        return MDC.get(Trace.TRACE);
    }

    /**
     * 设置traceId
     */
    public static void setCurrentTrace(String traceId) {
        if (StrUtil.isBlank(traceId)) {
            traceId = genTraceId();
        }
        Trace trace = new Trace();
        trace.setTraceId(traceId);
        trace.setSpanId(genSpanId());
        MDC.put(Trace.TRACE, trace.getTraceId());
        MDC.put(Trace.PARENT_SPAN, trace.getSpanId());
        CURRENT_SPAN.set(trace);
    }

    /**
     * 获取traceId
     */
    public static Trace getCurrentTrace() {
        Trace trace = CURRENT_SPAN.get();
        if (trace == null) {
            trace = new Trace();
            trace.setTraceId(genTraceId());
            trace.setSpanId(genSpanId());
            MDC.put(Trace.TRACE, trace.getTraceId());
            MDC.put(Trace.PARENT_SPAN, trace.getSpanId());
            CURRENT_SPAN.set(trace);
        } else{
            // spanId每次不一样，重新生成
            trace.setSpanId(genSpanId());
            MDC.put(Trace.PARENT_SPAN, trace.getSpanId());
            CURRENT_SPAN.set(trace);
        }
        return trace;
    }

    /**
     * 清空traceId
     */
    public static void clearCurrentTrace() {
        CURRENT_SPAN.set(null);
        CURRENT_SPAN.remove();
    }
}
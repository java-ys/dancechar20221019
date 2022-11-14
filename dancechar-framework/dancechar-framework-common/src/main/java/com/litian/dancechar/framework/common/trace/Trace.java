package com.litian.dancechar.framework.common.trace;

import lombok.Data;

import java.io.Serializable;

/**
 * Trace对象
 *
 * @author tojson
 * @date 2022/6/11 20:50
 */
@Data
public class Trace implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final String TRACE = "X-B3-TraceId";
    public static final String PARENT_SPAN = "X-B3-SpanId";

    /**
     * 分布式traceId
     */
    private String traceId;
    /**
     * 分布式spanId
     */
    private String spanId;
}

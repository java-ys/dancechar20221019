package com.litian.dancechar.framework.common.trace;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * trace http header 枚举
 *
 * @author tojson
 * @date 2022/6/8 22:15
 */
@Getter
@AllArgsConstructor
public enum TraceHttpHeaderEnum {
    /**
     * header发送traceId
     */
    HEADER_TRACE_ID("header_trace_id", "http请求发送traceId");

    String code;

    String message;
}

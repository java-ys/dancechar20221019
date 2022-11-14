package com.litian.dancechar.framework.common.feign;

import cn.hutool.core.util.StrUtil;
import com.litian.dancechar.framework.common.trace.Trace;
import com.litian.dancechar.framework.common.trace.TraceHttpHeaderEnum;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Map;


/**
 * feign全局拦截器(服务之间传递traceId)
 *
 * @author tojson
 * @date 2022/6/4 14:13
 */
@Slf4j
public class FeignRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        String traceId = MDC.get(Trace.TRACE);
        if (StrUtil.isNotEmpty(traceId)) {
            // 传递traceId
            requestTemplate.header(TraceHttpHeaderEnum.HEADER_TRACE_ID.getCode(), traceId);
        }
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return;
        }
        HttpServletRequest request = attributes.getRequest();
        Enumeration<String> headerNames = request.getHeaderNames();
        try {
            if (headerNames != null) {
                Map<String, Collection<String>> headers = requestTemplate.headers();
                while (headerNames.hasMoreElements()) {
                    String name = headerNames.nextElement();
                    String values = request.getHeader(name);
                    if (!headers.containsKey(name) && !"content-length".equalsIgnoreCase(name)
                            && !"content-type".equalsIgnoreCase(name)) {
                        requestTemplate.header(name, values);
                    }
                }
            }
        } catch (Exception e) {
            log.error("执行FeignRequestInterceptor系统异常!errMsg: {}", e.getMessage(), e);
        }
    }
}
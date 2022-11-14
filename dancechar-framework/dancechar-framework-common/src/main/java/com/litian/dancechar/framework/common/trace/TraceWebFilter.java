package com.litian.dancechar.framework.common.trace;

import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.litian.dancechar.framework.common.context.BodyReaderRequestWrapper;
import com.litian.dancechar.framework.common.context.ReqGetBody;
import com.litian.dancechar.framework.common.context.RequestParamsUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
import java.util.SortedMap;


/**
 * web端链路过滤器处理(设置traceId,spanId)
 *
 * @author tojson
 * @date 2022/6/8 22:09
 */
@Component
@Slf4j
public class TraceWebFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain)
            throws IOException, ServletException {
        long start = System.currentTimeMillis();
        HttpServletRequest request = (HttpServletRequest) req;
        String traceId = String.valueOf(request.getHeader(
                TraceHttpHeaderEnum.HEADER_TRACE_ID.getCode()));
        if (StrUtil.isNotEmpty(traceId) && !"null".equals(traceId)) {
            TraceHelper.setCurrentTrace(traceId);
        } else {
            TraceHelper.getCurrentTrace();
        }
        BodyReaderRequestWrapper requestWrapper = printAccessLog(request);
        String dTraceId = TraceHelper.getCurrentTrace().getTraceId();
        log.debug("trace web filter-traceId:{}", dTraceId);
        filterChain.doFilter(requestWrapper != null ? requestWrapper : request, resp);
        MDC.put(Trace.PARENT_SPAN, TraceHelper.genSpanId());
        log.info("当前请求总耗时：{}ms", System.currentTimeMillis() - start);
        TraceHelper.clearCurrentTrace();
    }

    /**
     * 打印访问日志
     */
    @SuppressWarnings({"rawtypes","unchecked"})
    private BodyReaderRequestWrapper printAccessLog(HttpServletRequest request) throws IOException {
        BodyReaderRequestWrapper requestWrapper;
        String requestUrl = request.getRequestURI();
        SortedMap<String, Object> paramResult = Maps.newTreeMap();
        paramResult.putAll(RequestParamsUtil.getUrlParams(request));
        if (!StrUtil.equals(HttpMethod.GET.name(), request.getMethod())) {
            String contentType = request.getContentType();
            if (StrUtil.containsIgnoreCase(contentType, "json")) {
                requestWrapper = new BodyReaderRequestWrapper(request);
                paramResult.putAll(new Gson().fromJson(ReqGetBody.getBody(requestWrapper), Map.class));
                log.info("开始当前请求-{},方法-{}，body参数：{}", requestUrl,request.getMethod(),
                        new Gson().toJson(paramResult));
                return requestWrapper;
            } else if (StrUtil.containsIgnoreCase(contentType, "form")) {
                paramResult.putAll(RequestParamsUtil.getFormParams(request));
            }
        }
        log.info("开始当前请求-{},方法-{}，body参数：{}", requestUrl,request.getMethod(), new Gson().toJson(paramResult));
        return null;
    }
}
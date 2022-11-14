package com.litian.dancechar.idgenerator.framework.advice;

import com.google.gson.Gson;
import com.litian.dancechar.framework.common.base.RespResult;
import com.litian.dancechar.framework.common.trace.Trace;
import com.litian.dancechar.framework.common.trace.TraceHelper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 返回结果日志处理
 *
 * @author tojson
 * @date 2021/6/5 21:13
 */
@ControllerAdvice
@Slf4j
public class ResponseLogAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Nullable
    @Override
    public Object beforeBodyWrite(@Nullable Object body, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        // 重新设置一下spanId
        MDC.put(Trace.PARENT_SPAN, TraceHelper.genSpanId());
        ServletServerHttpResponse responseTemp = (ServletServerHttpResponse) serverHttpResponse;
        HttpServletResponse resp = responseTemp.getServletResponse();
        ServletServerHttpRequest servletServerHttpRequest = (ServletServerHttpRequest) serverHttpRequest;
        HttpServletRequest req = servletServerHttpRequest.getServletRequest();
        // 此处的 Result 对象是我自定义的返回值类型,具体根据自己需求修改即可
        if(body instanceof RespResult){
            RespResult result = (RespResult) body;
            if(result != null) {
                String resultData = result.getData()!=null?new Gson().toJson(result.getData()) :null;
                // 记录日志等操作
                log.info("当前请求返回结果：{}", resultData);
            }
            // 这里可以对返回值进行修改二次封装等操作
        }
        return body;
    }
}
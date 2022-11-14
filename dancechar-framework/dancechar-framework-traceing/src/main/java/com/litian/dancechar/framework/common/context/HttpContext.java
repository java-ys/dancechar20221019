package com.litian.dancechar.framework.common.context;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 获取http上下文(context,response,session)
 *
 * @author tojson
 * @date 2021/6/14 22:28
 */
public class HttpContext {

    private HttpContext() {
    }

    /**
     * 获取header中的手机号
     */
    public static String getMobile() {
        return getRequestHeader(HttpConstants.Header.MOBILE);
    }

    /**
     * 获取header中的client ip
     */
    public static String getClientIp() {
        return getRequestHeader(HttpConstants.Header.CLIENT_IP);
    }

    public static String getRequestHeader(String headerName) {
        return getRequest().getHeader(headerName);
    }

    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    public static HttpServletResponse getResponse() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
    }

    public static HttpSession getSession() {
        return getRequest().getSession();
    }

    public static String getHeaderByName(String headerName) {
        return getRequest().getHeader(headerName);
    }
}
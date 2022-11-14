package com.litian.dancechar.framework.common.util;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.MD5;

/**
 * Md5工具类
 *
 * @author tojson
 * @date 2021/6/14 21:42
 */
public class DCMd5Util extends MD5 {

    /**
     * 功能: 获取traceId
     */
    public static String getTraceId(String source) {
        return SecureUtil.md5(source).substring(2, 16);
    }
}

package com.litian.dancechar.framework.common.context;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StreamUtils;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * body reader请求
 *
 * @author tojson
 * @date 2021/6/14 22:38
 */
@Slf4j
public class ReqGetBody {

    public static String getBody(HttpServletRequest request) {
        try {
            ServletInputStream in = request.getInputStream();
            String body;
            body = StreamUtils.copyToString(in, Charset.forName("UTF-8"));
            return body;
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return "";
        }
    }
}

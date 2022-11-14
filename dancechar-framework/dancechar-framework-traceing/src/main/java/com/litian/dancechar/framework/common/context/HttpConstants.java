package com.litian.dancechar.framework.common.context;

/**
 * http常量类
 *
 * @author tojson
 * @date 2021/6/14 22:28
 */
public class HttpConstants {

    private HttpConstants() {
    }

    public static class Common {
        private Common() {
        }

        public static final long ADMIN_JWT_TOKEN_EXPIRE = 30 * 60 * 1000L;
        public static final long ADMIN_JWT_REFRESH_TOKEN_EXPIRE = 60 * 60 * 1000L;
        public static final String JWT_SECRET = "dancechar-secret";
        public static final String ISSUER = "dancechar-issuser";
    }

    public static class Header {
        private Header() {
        }

        public static final String USER_ID = "userId";
        public static final String MOBILE = "mobile";
        public static final String CLIENT_IP = "ip";

        /**
         * http请求发送traceId
         */
        public static final String TRACE_HEADER = "http_header_trace_id";

        /**
         * jwt 前缀的前几位(bearer:)
         */
        public static final int JWT_LEASE_LENGTH = 7;

        /**
         * 访问用户信息
         */
        public static final String USER_ACCESS_INFO = "userAccessInfo";
        public static final String AUTHORIZATION = "Authorization";
        public static final String BEARER = "bearer:";
    }
}
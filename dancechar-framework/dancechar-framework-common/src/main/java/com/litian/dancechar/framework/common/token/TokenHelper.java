package com.litian.dancechar.framework.common.token;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.litian.dancechar.framework.common.context.HttpConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.Base64Codec;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

/**
 * jwt token解析工具类
 *
 * @author tojson
 * @date 2021/6/13 21:25
 */
@Slf4j
public class TokenHelper {
    private TokenHelper() {
    }

    private static final int JWT_LENGTH = 3;
    private static final String SPLIT = "\\.";

    /**
     * 生成admin token
     */
    public static String buildAdminToken(String userId, String mobile) {
        return buildToken(userId, mobile, 1, HttpConstants.Common.ADMIN_JWT_TOKEN_EXPIRE);
    }

    /**
     * 生成admin refresh token
     */
    public static String buildAdminRefreshToken(String userId, String mobile) {
        return buildToken(userId, mobile, 2, HttpConstants.Common.ADMIN_JWT_REFRESH_TOKEN_EXPIRE);
    }

    /**
     * 生成token
     */
    public static String buildAdminToken(String userId, String mobile, Long expireMilliseconds) {
        return buildToken(userId, mobile, 1, expireMilliseconds);
    }

    /**
     * 返回token
     */
    public static String buildToken(String userId, String mobile, Integer type, Long expireMilliseconds) {
        Date now = new Date();
        if (Objects.isNull(expireMilliseconds) || expireMilliseconds <= 0) {
            expireMilliseconds = HttpConstants.Common.ADMIN_JWT_TOKEN_EXPIRE;
            if (ObjectUtil.equal(type, 2)) {
                expireMilliseconds = HttpConstants.Common.ADMIN_JWT_REFRESH_TOKEN_EXPIRE;
            }
        }
        Date expiresAt = new Date(now.getTime() + expireMilliseconds);
        return JWT.create().withIssuer(HttpConstants.Common.ISSUER).withIssuedAt(now)
                .withExpiresAt(expiresAt)
                .withClaim(HttpConstants.Header.USER_ID, userId)
                .withClaim(HttpConstants.Header.MOBILE, mobile)
                .sign(Algorithm.HMAC256(HttpConstants.Common.JWT_SECRET));
    }

    public static String build(Map<String, Object> claims, long ttlMillis, String base64Security) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS512;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        // 生成签名密钥
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(base64Security);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        String jwtType = "JWT";
        String jwtAlgorithm = "HS512";
        // 添加构成JWT的参数
        JwtBuilder builder = Jwts.builder().setHeaderParam("typ", jwtType)
                .setHeaderParam("alg", jwtAlgorithm)
                .setClaims(claims).signWith(signatureAlgorithm, signingKey);

        // 添加Token过期时间,在当前时间之前均不会生效,过期时间秒为单位
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp).setNotBefore(now);
        }
        // 生成JWT
        return builder.compact();
    }

    /**
     * 反向解析JWT
     */
    public static Claims parse(String jsonWebToken, String base64Security) {
        try {
            return Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(base64Security))
                    .parseClaimsJws(jsonWebToken).getBody();
        } catch (Exception e) {
            log.error("执行JwtUtil.parse()方法,发生异常：{}", e);
            return null;
        }
    }

    /**
     * 校验Token过期时间是否有效
     */
    public static boolean checkExp(String jwt) {
        if (StrUtil.isBlank(jwt) || jwt.split(SPLIT).length != JWT_LENGTH) {
            return false;
        }

        if (jwt.startsWith(HttpConstants.Header.BEARER)) {
            jwt = jwt.substring(HttpConstants.Header.BEARER.length());
        }
        String[] split = jwt.split(SPLIT);
        String payload = split[1];
        String payloadClear = Base64Codec.BASE64URL.decodeToString(payload);
        JSONObject payloadJson = JSONUtil.parseObj(payloadClear);

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        Integer expiresSecond = (Integer) payloadJson.get("exp");

        // 判断是否过期
        boolean isExpire = expiresSecond.longValue() * 1000L >= now.getTime();
        log.info("jwt expire:{},{},{}",
                now.getTime(), expiresSecond.longValue() * 1000L, isExpire);
        return isExpire;

    }

    /**
     * 获取过期时间
     */
    public static Integer getExpiresTime(String jwt) {
        if (jwt.split(SPLIT).length == JWT_LENGTH) {
            String[] split = jwt.split(SPLIT);
            String payload = split[1];
            String payloadClear = Base64Codec.BASE64URL.decodeToString(payload);
            JSONObject payloadJson = JSONUtil.parseObj(payloadClear);
            return (Integer) payloadJson.get("exp");
        }
        return null;
    }

    /**
     * 获取主账号的手机号
     *
     * @param jwt 用户jwt
     * @return 登录用户主账号的手机号
     */
    public static String getMobile(String jwt) {
        return getClaim(jwt, HttpConstants.Header.MOBILE);
    }

    /**
     * 获取用户Id
     *
     * @param jwt 用户jwt
     * @return 返回用户Id
     */
    public static String getUserId(String jwt) {
        return getClaim(jwt, HttpConstants.Header.USER_ID);
    }

    /**
     * 获取jwt中的payload中业务属性值
     *
     * @param jwt     用户jwt
     * @param claimId claimId
     * @return 业务属性值
     */
    public static String getClaim(String jwt, String claimId) {
        if (StrUtil.isBlank(jwt) || jwt.split(SPLIT).length != JWT_LENGTH) {
            return "";
        }
        String[] split = jwt.split(SPLIT);
        String payload = split[1];
        String payloadClear = Base64Codec.BASE64URL.decodeToString(payload);
        JSONObject payloadJson = JSONUtil.parseObj(payloadClear);
        if (null == payloadJson) {
            return "";
        }
        return payloadJson.getStr(claimId);
    }
}

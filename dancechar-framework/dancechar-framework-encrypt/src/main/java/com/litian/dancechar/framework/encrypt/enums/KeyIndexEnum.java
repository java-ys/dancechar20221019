package com.litian.dancechar.framework.encrypt.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 加密key
 *
 * @author tojson
 * @date 2022/06/04 18:50
 */
@Getter
@AllArgsConstructor
public enum KeyIndexEnum {
    /**
     * 手机号
     */
    MOBILE("mobile", "手机号", "11"),
    /**
     * 身份证
     */
    IDCARD("idcard", "身份证", "21"),
    /**
     * 邮件
     */
    EMAIL("email", "邮件", "31"),
    /**
     * 地址
     */
    ADDRESS("mobile", "地址", "41"),
    /**
     * 银行卡
     */
    BANKCARD("bankCard", "银行卡", "51");

    public String code;

    public String message;

    public String keyIndex;
}

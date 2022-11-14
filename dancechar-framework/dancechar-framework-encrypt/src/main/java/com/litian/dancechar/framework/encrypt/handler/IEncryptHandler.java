package com.litian.dancechar.framework.encrypt.handler;

import com.litian.dancechar.framework.encrypt.enums.EncryptTypeEnum;

/**
 * 加解密处理接口
 *
 * @author tojson
 * @date 2022/06/04 18:50
 */
public interface IEncryptHandler {
    /**
     * 获取加密类型
     */
    EncryptTypeEnum getEncryptType();

    /**
     * 解密处理
     */
    String decrypt(String decryptStr);

    /**
     * 加密处理
     */
    String encrypt(String encryptStr);
}

package com.litian.dancechar.framework.encrypt.handler.impl;

import com.litian.dancechar.framework.encrypt.enums.EncryptTypeEnum;
import com.litian.dancechar.framework.encrypt.enums.KeyIndexEnum;
import com.litian.dancechar.framework.encrypt.handler.IEncryptHandler;
import com.litian.dancechar.framework.encrypt.util.AesUtil;

/**
 * 手机号加密处理（AES加密是例子演示需要，真正的业务换成加密机）
 *
 * @author tojson
 * @date 2022/08/28 11:00
 */
public class MobileEncryptHandler implements IEncryptHandler {
    @Override
    public EncryptTypeEnum getEncryptType() {
        return EncryptTypeEnum.MOBILE;
    }

    /**
     * 解密处理
     */
    public String decrypt(String decryptStr){
        // 真实应用需要换成加密机调用
        return AesUtil.decrypt(decryptStr, KeyIndexEnum.MOBILE);
    }

    /**
     * 加密处理
     */
    public String encrypt(String encryptStr){
        // 真实应用需要换成加密机调用
        return AesUtil.encrypt(encryptStr, KeyIndexEnum.MOBILE);
    }

}

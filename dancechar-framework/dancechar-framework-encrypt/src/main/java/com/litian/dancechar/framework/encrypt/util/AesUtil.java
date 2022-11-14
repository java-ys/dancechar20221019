package com.litian.dancechar.framework.encrypt.util;

import cn.hutool.core.util.StrUtil;
import com.litian.dancechar.framework.encrypt.enums.KeyIndexEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Aes加密工具类
 *
 * @author tojson
 * @date 2022/08/28 10:00
 */
@Slf4j
public class AesUtil {
    private static final String KEY_ALGORITHM = "AES";
    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";

    public static final String PREFIX = "DE";

    public static String encrypt(String value, KeyIndexEnum keyIndexEnum){
        try{
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(keyIndexEnum.getKeyIndex()));
            byte[] content = value.getBytes("UTF-8");
            byte[] encryptData = cipher.doFinal(content);
            return PREFIX + Hex.encodeHexString(encryptData);
        }catch (Exception e){
            log.error("AES加密时出现问题，值为：{}",value);
            throw new IllegalStateException("AES加密时出现问题"+e.getMessage(),e);
        }
    }

    public static String decrypt(String value, KeyIndexEnum keyIndexEnum){
        if (StrUtil.isEmpty(value)) {
            return "";
        }
        try {
            if(value.startsWith(PREFIX) && value.length() > 2){
                value = value.substring(2);
            }
            byte[] encryptData = Hex.decodeHex(value);
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, getSecretKey(keyIndexEnum.getKeyIndex()));
            byte[] content = cipher.doFinal(encryptData);
            return new String(content, "UTF-8");
        }catch (Exception e){
            log.error("AES解密时出现问题，值为：{}",value);
            throw new IllegalStateException("AES解密时出现问题"+e.getMessage(),e);
        }
    }

    private static SecretKeySpec getSecretKey(final String password) throws NoSuchAlgorithmException{
        //返回生成指定算法密钥生成器的 KeyGenerator 对象
        KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);
        //AES 要求密钥长度为 128
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(password.getBytes());
        kg.init(128, random);
        //生成一个密钥
        SecretKey secretKey = kg.generateKey();
        // 转换为AES专用密钥
        return new SecretKeySpec(secretKey.getEncoded(), KEY_ALGORITHM);
    }
}

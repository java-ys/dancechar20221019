package com.litian.dancechar.framework.encrypt.handler;

import com.litian.dancechar.framework.encrypt.enums.EncryptTypeEnum;
import com.litian.dancechar.framework.encrypt.handler.impl.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 加解密处理注册表
 *
 * @author tojson
 * @date 2022/08/28 11:15
 */
@SuppressWarnings("unused")
public class EncryptHandlerRegistry {
    private static final Map<EncryptTypeEnum, IEncryptHandler> HANDLER_REGISTY = new ConcurrentHashMap<>();

    static {
        HANDLER_REGISTY.put(EncryptTypeEnum.ID_CARD,new IDCardEncryptHandler());
        HANDLER_REGISTY.put(EncryptTypeEnum.MOBILE,new MobileEncryptHandler());
        HANDLER_REGISTY.put(EncryptTypeEnum.ADDRESS,new AddressEncryptHandler());
        HANDLER_REGISTY.put(EncryptTypeEnum.EMAIL,new EmailEncryptHandler());
        HANDLER_REGISTY.put(EncryptTypeEnum.BANK_CARD,new BankCardEncryptHandler());
    }

    public static void put(IEncryptHandler encryptHandler){
        HANDLER_REGISTY.put(encryptHandler.getEncryptType(),encryptHandler);
    }

    public static IEncryptHandler get(EncryptTypeEnum encryptTypeEnum){
        IEncryptHandler encryptHandler =  HANDLER_REGISTY.get(encryptTypeEnum);
        if(encryptHandler == null){
            throw new IllegalArgumentException("none encryptHandler found!, type:"
                    + encryptTypeEnum.name());
        }
        return encryptHandler;
    }
}

package com.litian.dancechar.framework.encrypt.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.litian.dancechar.framework.encrypt.annotation.DecryptClass;
import com.litian.dancechar.framework.encrypt.annotation.DecryptField;
import com.litian.dancechar.framework.encrypt.annotation.EncryptClass;
import com.litian.dancechar.framework.encrypt.annotation.EncryptField;
import com.litian.dancechar.framework.encrypt.enums.KeyIndexEnum;
import com.litian.dancechar.framework.encrypt.handler.EncryptHandlerRegistry;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 加解密工具类
 *
 * @author tojson
 * @date 2022/08/28 11:15
 */
@Slf4j
public class EncryptUtil {
    /**
     * 加密手机号
     */
    public static String encryptMobile(String encryptMobile){
        // 如果已经是加密串，则不处理
        if(StrUtil.isEmpty(encryptMobile) || encryptMobile.startsWith(AesUtil.PREFIX)){
            return encryptMobile;
        }
        try{
            return AesUtil.encrypt(encryptMobile, KeyIndexEnum.MOBILE);
        }catch (Exception e){
            log.error("加密手机号异常！mobile:{}", encryptMobile);
            return encryptMobile;
        }
    }

    /**
     * 批量加密手机号
     */
    public static Map<String, String> batchEncryptMobiles(List<String> encryptMobiles){
        if(CollUtil.isEmpty(encryptMobiles)){
            return null;
        }
        Map<String, String> result = Maps.newConcurrentMap();
        for(String mobile : encryptMobiles){
            result.put(mobile,encryptMobile(mobile));
        }
        return result;
    }

    /**
     * 解密手机号
     */
    public static String decryptMobile(String decryptMobile){
        // 如果已经是解密串，则不处理
        if(StrUtil.isEmpty(decryptMobile) || !decryptMobile.startsWith(AesUtil.PREFIX)){
            return decryptMobile;
        }
        try{
            return AesUtil.decrypt(decryptMobile, KeyIndexEnum.MOBILE);
        }catch (Exception e){
            log.error("解密手机号异常！mobile:{}", decryptMobile);
            return decryptMobile;
        }
    }

    /**
     * 批量解密手机号
     */
    public static Map<String, String> batchDecryptMobiles(List<String> decryptMobiles){
        if(CollUtil.isEmpty(decryptMobiles)){
            return null;
        }
        Map<String, String> result = Maps.newConcurrentMap();
        for(String mobile : decryptMobiles){
            result.put(mobile,decryptMobile(mobile));
        }
        return result;
    }

    /**
     * 加密邮件
     */
    public static String encryptEmail(String encryptEmail){
        // 如果已经是加密串，则不处理
        if(StrUtil.isEmpty(encryptEmail) || encryptEmail.startsWith(AesUtil.PREFIX)){
            return encryptEmail;
        }
        try{
            return AesUtil.encrypt(encryptEmail, KeyIndexEnum.EMAIL);
        }catch (Exception e){
            log.error("加密邮件异常！email:{}", encryptEmail);
            return encryptEmail;
        }
    }

    /**
     * 批量加密邮件
     */
    public static Map<String, String> batchEncryptEmails(List<String> encryptEmails){
        if(CollUtil.isEmpty(encryptEmails)){
            return null;
        }
        Map<String, String> result = Maps.newConcurrentMap();
        for(String email : encryptEmails){
            result.put(email,encryptEmail(email));
        }
        return result;
    }

    /**
     * 解密邮件
     */
    public static String decryptEmail(String decryptEmail){
        // 如果已经是解密串，则不处理
        if(StrUtil.isEmpty(decryptEmail) || !decryptEmail.startsWith(AesUtil.PREFIX)){
            return decryptEmail;
        }
        try{
            return AesUtil.decrypt(decryptEmail, KeyIndexEnum.EMAIL);
        }catch (Exception e){
            log.error("解密邮件异常！email:{}", decryptEmail);
            return decryptEmail;
        }
    }

    /**
     * 批量解密邮件
     */
    public static Map<String, String> batchDecryptEmails(List<String> decryptEmails){
        if(CollUtil.isEmpty(decryptEmails)){
            return null;
        }
        Map<String, String> result = Maps.newConcurrentMap();
        for(String email : decryptEmails){
            result.put(email,decryptEmail(email));
        }
        return result;
    }

    /**
     * 加密地址
     */
    public static String encryptAddress(String encryptAddress){
        // 如果已经是加密串，则不处理
        if(StrUtil.isEmpty(encryptAddress) || encryptAddress.startsWith(AesUtil.PREFIX)){
            return encryptAddress;
        }
        try{
            return AesUtil.encrypt(encryptAddress, KeyIndexEnum.ADDRESS);
        }catch (Exception e){
            log.error("加密地址异常！address:{}", encryptAddress);
            return encryptAddress;
        }
    }

    /**
     * 批量加密地址
     */
    public static Map<String, String> batchEncryptAddresses(List<String> batchEncryptAddresses){
        if(CollUtil.isEmpty(batchEncryptAddresses)){
            return null;
        }
        Map<String, String> result = Maps.newConcurrentMap();
        for(String address : batchEncryptAddresses){
            result.put(address,encryptAddress(address));
        }
        return result;
    }

    /**
     * 解密地址
     */
    public static String decryptAddress(String decryptAddress){
        // 如果已经是解密串，则不处理
        if(StrUtil.isEmpty(decryptAddress) || !decryptAddress.startsWith(AesUtil.PREFIX)){
            return decryptAddress;
        }
        try{
            return AesUtil.decrypt(decryptAddress, KeyIndexEnum.ADDRESS);
        }catch (Exception e){
            log.error("解密地址异常！address:{}", decryptAddress);
            return decryptAddress;
        }
    }

    /**
     * 批量解密地址
     */
    public static Map<String, String> batchDecryptAddresses(List<String> batchDecryptAddresses){
        if(CollUtil.isEmpty(batchDecryptAddresses)){
            return null;
        }
        Map<String, String> result = Maps.newConcurrentMap();
        for(String address : batchDecryptAddresses){
            result.put(address,decryptAddress(address));
        }
        return result;
    }

    /**
     * 加密银行卡
     */
    public static String encryptBankCard(String encryptBankCard){
        // 如果已经是加密串，则不处理
        if(StrUtil.isEmpty(encryptBankCard) || encryptBankCard.startsWith(AesUtil.PREFIX)){
            return encryptBankCard;
        }
        try{
            return AesUtil.encrypt(encryptBankCard, KeyIndexEnum.BANKCARD);
        }catch (Exception e){
            log.error("加密银行卡异常！bankCard:{}", encryptBankCard);
            return encryptBankCard;
        }
    }

    /**
     * 批量加密银行卡
     */
    public static Map<String, String> batchEncryptBankCards(List<String> batchEncryptBankCards){
        if(CollUtil.isEmpty(batchEncryptBankCards)){
            return null;
        }
        Map<String, String> result = Maps.newConcurrentMap();
        for(String bankCard : batchEncryptBankCards){
            result.put(bankCard,encryptBankCard(bankCard));
        }
        return result;
    }

    /**
     * 解密银行卡
     */
    public static String decryptBankCard(String decryptBankCard){
        // 如果已经是解密串，则不处理
        if(StrUtil.isEmpty(decryptBankCard) || !decryptBankCard.startsWith(AesUtil.PREFIX)){
            return decryptBankCard;
        }
        try{
            return AesUtil.decrypt(decryptBankCard, KeyIndexEnum.BANKCARD);
        }catch (Exception e){
            log.error("解密银行卡异常！bankCard:{}", decryptBankCard);
            return decryptBankCard;
        }
    }

    /**
     * 批量解密银行卡
     */
    public static Map<String, String> batchDecryptBankCards(List<String> batchDecryptBankCards){
        if(CollUtil.isEmpty(batchDecryptBankCards)){
            return null;
        }
        Map<String, String> result = Maps.newConcurrentMap();
        for(String bankCard : batchDecryptBankCards){
            result.put(bankCard,decryptBankCard(bankCard));
        }
        return result;
    }

    /**
     * 加密身份证
     */
    public static String encryptIdCard(String encryptIdCard){
        // 如果已经是加密串，则不处理
        if(StrUtil.isEmpty(encryptIdCard) || encryptIdCard.startsWith(AesUtil.PREFIX)){
            return encryptIdCard;
        }
        try{
            return AesUtil.encrypt(encryptIdCard, KeyIndexEnum.IDCARD);
        }catch (Exception e){
            log.error("加密身份证异常！idCard:{}", encryptIdCard);
            return encryptIdCard;
        }
    }

    /**
     * 批量加密身份证
     */
    public static Map<String, String> batchEncryptIdCards(List<String> batchEncryptIdCards){
        if(CollUtil.isEmpty(batchEncryptIdCards)){
            return null;
        }
        Map<String, String> result = Maps.newConcurrentMap();
        for(String idCard : batchEncryptIdCards){
            result.put(idCard, encryptIdCard(idCard));
        }
        return result;
    }

    /**
     * 解密身份证
     */
    public static String decryptIdCard(String decryptIdCard){
        // 如果已经是解密串，则不处理
        if(StrUtil.isEmpty(decryptIdCard) || !decryptIdCard.startsWith(AesUtil.PREFIX)){
            return decryptIdCard;
        }
        try{
            return AesUtil.decrypt(decryptIdCard, KeyIndexEnum.IDCARD);
        }catch (Exception e){
            log.error("解密身份证异常！idCard:{}", decryptIdCard);
            return decryptIdCard;
        }
    }

    /**
     * 批量解密身份证
     */
    public static Map<String, String> batchDecryptIdCards(List<String> batchDecryptIdCards){
        if(CollUtil.isEmpty(batchDecryptIdCards)){
            return null;
        }
        Map<String, String> result = Maps.newConcurrentMap();
        for(String idCard : batchDecryptIdCards){
            result.put(idCard, decryptIdCard(idCard));
        }
        return result;
    }

    /**
     * 列表对象敏感字段加密
     */
    public static List handleEncryptList(List<?> requestObj) {
        requestObj.forEach(vo->handleEncryptObject(vo));
        return requestObj;
    }

    /**
     * 对象敏感字段加密
     */
    public static Object handleEncryptObject(Object requestObj){
        if (Objects.isNull(requestObj)) {
            return null;
        }
        EncryptClass encryptClass = requestObj.getClass().getAnnotation(EncryptClass.class);
        if(null == encryptClass || !encryptClass.isEnable()){
            return requestObj;
        }
        Field[] fieldList = FieldUtils.getAllFields(requestObj.getClass());
        try{
            for (Field field : fieldList) {
                boolean hasSecureField = field.isAnnotationPresent(EncryptField.class);
                if (hasSecureField) {
                    Boolean accessible = field.isAccessible();
                    if (!accessible) {
                        field.setAccessible(true);
                    }
                    String plaintextValue = (String) field.get(requestObj);
                    EncryptField encryptField = field.getAnnotation(EncryptField.class);
                    String encryptValue = EncryptHandlerRegistry.get(encryptField.value()).encrypt(plaintextValue);
                    field.set(requestObj, encryptValue);
                    if (!accessible) {
                        field.setAccessible(false);
                    }
                }
            }
        } catch (Exception e){
            log.error("对象敏感字段加密！errMsg:{}", e.getMessage() ,e);
        }
        return requestObj;
    }

    /**
     * 列表对象敏感解密
     */
    public static List handleDecryptList(List<?> requestObj) {
        requestObj.forEach(vo->handleDecryptObject(vo));
        return requestObj;
    }

    /**
     * 对象敏感字段加密
     */
    public static Object handleDecryptObject(Object requestObj){
        if (Objects.isNull(requestObj)) {
            return null;
        }
        DecryptClass decryptClass = requestObj.getClass().getAnnotation(DecryptClass.class);
        if(null == decryptClass || !decryptClass.isEnable()){
            return requestObj;
        }
        Field[] fieldList = FieldUtils.getAllFields(requestObj.getClass());
        try{
            for (Field field : fieldList) {
                boolean hasSecureField = field.isAnnotationPresent(DecryptField.class);
                if (hasSecureField) {
                    Boolean accessible = field.isAccessible();
                    if (!accessible) {
                        field.setAccessible(true);
                    }
                    String plaintextValue = (String) field.get(requestObj);
                    DecryptField decryptField = field.getAnnotation(DecryptField.class);
                    String decryptValue = EncryptHandlerRegistry.get(decryptField.value()).decrypt(plaintextValue);
                    field.set(requestObj, decryptValue);
                    if (!accessible) {
                        field.setAccessible(false);
                    }
                }
            }
        } catch (Exception e){
            log.error("对象敏感字段解密！errMsg:{}", e.getMessage() ,e);
        }
        return requestObj;
    }

    private volatile static EncryptUtil instance = null;
    private  EncryptUtil(){}

    public static EncryptUtil getInstance(){
        if(instance == null){
            synchronized (EncryptUtil.class){
                if(instance == null){
                    instance = new EncryptUtil();
                }
            }
        }
        return instance;
    }

    public static void main(String[] args) {
        //System.out.println(batchEncryptMobiles(Lists.newArrayList("13543432345", "13678434563")));
        System.out.println(batchDecryptMobiles(Lists.newArrayList("DE1ad52833a1ee6d139dce92afc9d803a0",
                "DE6c1c48acd4d3023a17d4897b47f40fa3")));
    }
}

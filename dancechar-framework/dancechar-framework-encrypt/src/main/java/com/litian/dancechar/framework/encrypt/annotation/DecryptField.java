package com.litian.dancechar.framework.encrypt.annotation;

import com.litian.dancechar.framework.encrypt.enums.EncryptTypeEnum;

import java.lang.annotation.*;

/**
 * 作用到属性的解密注解
 *
 * @author tojson
 * @date 2021/8/28 08:17
 */
@Target({ElementType.FIELD,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface DecryptField {
    EncryptTypeEnum value();
}

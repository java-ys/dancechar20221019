package com.litian.dancechar.framework.encrypt.annotation;

import java.lang.annotation.*;

/**
 * 作用到类的加密注解
 *
 * @author tojson
 * @date 2021/8/28 08:17
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface EncryptClass {

    /**
     * 是否启用
     */
    boolean isEnable() default true;
}

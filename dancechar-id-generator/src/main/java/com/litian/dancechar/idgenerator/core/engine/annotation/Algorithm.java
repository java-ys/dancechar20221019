package com.litian.dancechar.idgenerator.core.engine.annotation;

import java.lang.annotation.*;

/**
 *
 * 算法注解
 *
 * @author tojson
 * @date 2022/09/11 22:53
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Algorithm {

    String value() default "";
}

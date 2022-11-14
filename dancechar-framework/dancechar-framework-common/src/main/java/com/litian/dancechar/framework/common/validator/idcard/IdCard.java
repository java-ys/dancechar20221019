package com.litian.dancechar.framework.common.validator.idcard;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy= IdCardValidator.class)
public @interface IdCard {

    String message() default "身份证号码格式不对";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

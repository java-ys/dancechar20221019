package com.litian.dancechar.framework.common.validator.idcard;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IdCardValidator implements ConstraintValidator<IdCard, String> {
    /**
     * 身份证规则校验正则表达式
     */
    private String reg = "^(\\d{6})(\\d{4})(\\d{2})(\\d{2})(\\d{3})([0-9]|X)$";
    private Pattern pattern = Pattern.compile(reg);

    public IdCardValidator() {
    }

    public boolean isValid(String value, ConstraintValidatorContext arg1) {
        if (value == null) {
            return true;
        }
        Matcher m = pattern.matcher(value);
        return m.find();
    }
}
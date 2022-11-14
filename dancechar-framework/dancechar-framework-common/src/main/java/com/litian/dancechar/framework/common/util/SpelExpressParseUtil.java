package com.litian.dancechar.framework.common.util;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;

/**
 * 解析spel表达式工具类
 * @author tojson
 * @date 2021/6/19 13:20
 */
public class SpelExpressParseUtil {
    private static ExpressionParser parser = new SpelExpressionParser();
    private static LocalVariableTableParameterNameDiscoverer discoverer = new LocalVariableTableParameterNameDiscoverer();

    /**
     * 解析spel表达式
     * @param srcObj           目标对象
     * @param spelExpression   表达式
     * @param srcMethod        目标方法
     * @param methodArgs       方法参数
     * @return 解析的结果
     */
    public static String parse(Object srcObj, String spelExpression, Method srcMethod, Object[] methodArgs){
        if(ObjectUtil.isNull(srcObj) || StrUtil.isEmpty(spelExpression)){
            return "";
        }
        StandardEvaluationContext context = new MethodBasedEvaluationContext(srcObj, srcMethod, methodArgs, discoverer);
        String[] paramNameArr = discoverer.getParameterNames(srcMethod);
        for(int i=0; i <paramNameArr.length;i++){
            context.setVariable(paramNameArr[i], methodArgs[i]);
        }
        return parser.parseExpression(spelExpression).getValue(context, String.class);
    }
}

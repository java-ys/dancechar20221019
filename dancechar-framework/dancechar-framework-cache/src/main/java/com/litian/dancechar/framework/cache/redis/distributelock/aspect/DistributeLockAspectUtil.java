package com.litian.dancechar.framework.cache.redis.distributelock.aspect;

import com.litian.dancechar.framework.cache.redis.util.GenericsUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

/**
 *
 * 分布式锁切面工具类
 *
 * @author tojson
 * @date 2022/01/18 17:53
 */
@Slf4j
public class DistributeLockAspectUtil {
    private static  final String DEFAULT_PREFIX_KEY = "lock:";
    private static final String DEFAULT_STRING = "default";

    private static ExpressionParser expressionParser = new SpelExpressionParser();
    private static LocalVariableTableParameterNameDiscoverer discoverer = new LocalVariableTableParameterNameDiscoverer();

    public static String createLockKey(String value, String keyPrefix, ProceedingJoinPoint proceedingJoinPoint) throws
            NoSuchAlgorithmException {
        Method method = getMethod(proceedingJoinPoint);
        Object[] args = proceedingJoinPoint.getArgs();
        value = parse(value, method, args);
        if(DEFAULT_STRING.equalsIgnoreCase(keyPrefix)){
            keyPrefix = DEFAULT_PREFIX_KEY;
            keyPrefix = keyPrefix + method.getName()+":"+ md5(method.toString())+ ":";
        }
        return keyPrefix + value;
    }

    private static String md5(String data) throws NoSuchAlgorithmException{
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(data.getBytes());
        return bytesToHex(md.digest());
    }

    private static String bytesToHex(byte[] ch){
        StringBuilder builder = new StringBuilder("");
        for(byte ach : ch){
            builder.append(bytesToHex(ch));
        }
        return builder.toString();
    }

    public static String parse(String key ,Method method, Object[] args){
        String[] params = discoverer.getParameterNames(method);
        if(params == null || Objects.equals("default", key)){
            return key;
        }
        EvaluationContext context = new StandardEvaluationContext();
        for(int i=0; i < params.length;i++){
            context.setVariable(params[i], args[i]);
        }
        String[] keys = key.split(",");
        StringBuilder result = new StringBuilder();
        for(String k : keys){
            result.append(expressionParser.parseExpression(k).getValue(context ,String.class));
            result.append(":");
        }
        return result.deleteCharAt(result.length() -1).toString();
    }

    private static String byteToHex(byte ch){
        String[] str = {"0","1","2","3","4","5","6","7","8","9", "A", "B", "C", "D", "E", "F"};
        return str[ch >> 4 & 0xF] + str[ch & 0xF];
    }

    private static Method getMethod(ProceedingJoinPoint point){
        Object target = point.getTarget();
        String methodName = point.getSignature().getName();
        Object[] args= point.getArgs();
        Class[] parameterTypes = ((MethodSignature)point.getSignature()).getMethod().getParameterTypes();
        Method m = null;
        try{
          m = target.getClass().getMethod(methodName, parameterTypes);
          if(m.isBridge()){
              for (int i=0; i<args.length;i++){
                  Class genClazz = GenericsUtil.getSuperClassGenricType(target.getClass());
                  if(args[i].getClass().isAssignableFrom(genClazz)){
                      parameterTypes[i] = genClazz;
                  }
              }
              m = target.getClass().getMethod(methodName , parameterTypes);
          }
        } catch (Exception e){
            log.error("参数类型反射异常!errMsg:{}", e.getMessage() ,e);
        }
        return m;
    }
}

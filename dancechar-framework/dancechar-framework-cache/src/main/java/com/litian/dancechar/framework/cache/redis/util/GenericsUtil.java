package com.litian.dancechar.framework.cache.redis.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class GenericsUtil {

    public static Class getSuperClassGenricType(Class clazz, int index){
        Type genType = clazz.getGenericSuperclass();
        if(!(genType instanceof ParameterizedType)){
            return Object.class;
        }
        Type[] params = ((ParameterizedType)genType).getActualTypeArguments();
        if(index >= params.length || index < 0){
            throw new RuntimeException("索引不对");
        }
        if(!((params[index]) instanceof Class)){
            return Object.class;
        }
        return (Class)params[index];
    }

    public static Class getSuperClassGenricType(Class clazz){
        return getSuperClassGenricType(clazz, 0);
    }
}

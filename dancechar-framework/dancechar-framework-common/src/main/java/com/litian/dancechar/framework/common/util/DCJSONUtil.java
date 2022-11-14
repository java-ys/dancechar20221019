package com.litian.dancechar.framework.common.util;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * json操作工具类
 *
 * @author tojson
 * @date 2022/6/22 11:16
 */
public class DCJSONUtil extends JSONUtil {

    public static <T> T toBean(String jsonString, Class<T> beanClass){
        if(StrUtil.isEmpty(jsonString)){
            return null;
        }
        return JSON.parseObject(jsonString ,beanClass);
    }

    /**
     * 将jsonArray转换为list
     */
    public static <T> List<T> toList(String jsonArray, Class<T> cls){
        return JSONArray.parseArray(jsonArray, cls);
    }

    /**
     * 将jsonStr转换为map
     */
    public static Map toMap(String jsonStr){
        return (Map)JSON.parse(jsonStr);
    }

    /**
     * 将对象转为字符串
     */
    public static String toJsonStr(Object object){
        return JSON.toJSONString(object, SerializerFeature.DisableCircularReferenceDetect);
    }

    public static <T> List<T> toBeanList(List<String> jsonList, Class<T> elementType){
        if(CollUtil.isEmpty(jsonList)){
            return CollUtil.newArrayList();
        }
        return jsonList.stream().map(json -> toBean(json, elementType)).collect(Collectors.toList());
    }

    public static <T> Set<T> toBeanSet(Set<String> jsonSet, Class<T> elementType){
        if(CollUtil.isEmpty(jsonSet)){
            return CollUtil.newHashSet();
        }
        return jsonSet.stream().map(json -> toBean(json, elementType)).collect(Collectors.toSet());
    }
}

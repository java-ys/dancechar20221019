package com.litian.dancechar.framework.common.util;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONUtil;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * json操作工具类
 *
 * @author tojson
 * @date 2022/6/22 11:16
 */
public class DCJSONUtil extends JSONUtil {

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

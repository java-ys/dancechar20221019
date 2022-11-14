package com.litian.dancechar.framework.common.util;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Bean操作工具类
 *
 * @author tojson
 * @date 2021/6/19 11:16
 */
@Slf4j
public class DCBeanUtil extends BeanUtil {
    /**
     * 拷贝Object(排除Null)
     *
     * @param target 目标
     * @param orig   源
     * @return 目标对象
     */
    public static <T> T copyNotNull(T target, Object orig) {
        if (ObjectUtil.isNull(target) || ObjectUtil.isNull(orig)) {
            return target;
        }
        copy(target, orig, true);
        return target;
    }

    /**
     * 拷贝list列表(排除Null)
     *
     * @param sourceList  源列表
     * @param targetClass 目标class
     */
    public static <T> List<T> copyList(List<?> sourceList, Class<T> targetClass) {
        if (null == sourceList || sourceList.size() == 0) {
            return null;
        }
        try {
            List<T> targetList = new ArrayList<T>(sourceList.size());
            for (Object source : sourceList) {
                T target = targetClass.newInstance();
                copyNotNull(target, source);
                targetList.add(target);
            }
            return targetList;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 拷贝Object(排除Null)
     *
     * @param target       目标
     * @param orig         源
     * @param fieldMapping 两个对象属性不同时，通过设置map映射
     * @return 目标对象
     */
    public static Object copyNotNull(Object target, Object orig, Map<String, String> fieldMapping) {
        if (ObjectUtil.isNull(target) || ObjectUtil.isNull(orig)) {
            return target;
        }
        copy(target, orig, true, fieldMapping);
        return target;
    }

    public static void copy(Object target, Object orig, boolean ignoreNullValue, Map<String, String> fieldMapping,
                            String... ignoreProperties) {
        if (ObjectUtil.isNull(target) || ObjectUtil.isNull(orig)) {
            return;
        }
        CopyOptions copyOptions = CopyOptions.create().ignoreError().ignoreCase()
                .setIgnoreNullValue(ignoreNullValue)
                .setIgnoreProperties(ignoreProperties)
                .setFieldMapping(fieldMapping);
        BeanUtil.copyProperties(orig, target, copyOptions);
    }

    public static void copy(Object target, Object orig, boolean ignoreNullValue, String... ignoreProperties) {
        if (ObjectUtil.isNull(target) || ObjectUtil.isNull(orig)) {
            return;
        }
        CopyOptions copyOptions = CopyOptions.create().ignoreError().ignoreCase()
                .setIgnoreNullValue(ignoreNullValue)
                .setIgnoreProperties(ignoreProperties);
        BeanUtil.copyProperties(orig, target, copyOptions);
    }
}
package com.litian.dancechar.framework.common.mybatis;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.litian.dancechar.framework.common.context.HttpContext;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

/**
 * mybatis plus 自动填充常用字段处理类
 *
 * @author tojson
 * @date 2021/6/14 22:28
 */
@Configuration
public class MybatisPlusMetaObjectHandler implements MetaObjectHandler {
    /**
     * 未删除
     */
    private static final Integer NOT_DELETED = 0;

    @Override
    public void insertFill(MetaObject metaObject) {
        if (hasProperty(metaObject, BaseDO.CREATE_DATE)) {
            this.setFieldValByName(BaseDO.CREATE_DATE, new Date(), metaObject);
        }
        if (hasProperty(metaObject, BaseDO.UPDATE_DATE)) {
            this.setFieldValByName(BaseDO.UPDATE_DATE, new Date(), metaObject);
        }
        if (hasProperty(metaObject, BaseDO.CREATE_USER)) {
            this.setFieldValByName(BaseDO.CREATE_USER, Convert.toStr(HttpContext.getMobile(), ""), metaObject);
        }
        if (hasProperty(metaObject, BaseDO.DELETE_FLAG)) {
            this.setFieldValByName(BaseDO.DELETE_FLAG, NOT_DELETED, metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        if (hasProperty(metaObject, BaseDO.UPDATE_USER)) {
            this.setFieldValByName(BaseDO.UPDATE_USER, Convert.toStr(HttpContext.getMobile(), ""), metaObject);
        }
        if (hasProperty(metaObject, BaseDO.UPDATE_DATE)) {
            this.setFieldValByName(BaseDO.UPDATE_DATE, new Date(), metaObject);
        }
    }

    private boolean hasProperty(MetaObject metaObject, String fieldName) {
        return metaObject.hasGetter(fieldName) || metaObject.hasGetter("et." + fieldName);
    }
}
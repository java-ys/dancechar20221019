package com.litian.dancechar.framework.encrypt.interceptor;

import com.litian.dancechar.framework.encrypt.annotation.DecryptClass;
import com.litian.dancechar.framework.encrypt.annotation.DecryptField;
import com.litian.dancechar.framework.encrypt.handler.EncryptHandlerRegistry;
import com.litian.dancechar.framework.encrypt.util.PluginUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 读取表字段解密拦截器
 *
 * @author tojson
 * @date 2021/8/28 08:17
 */
@Intercepts({
        @Signature(type = ResultSetHandler.class, method = "handleResultSets", args = {java.sql.Statement.class})
})
@Slf4j
public class DecryptInterceptor implements Interceptor {
    private static final String MAPPED_STATEMENT="delegate.mappedStatement";
    private static final String BOUND_SQL="delegate.boundSql";

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        final List<Object> results = (List<Object>)invocation.proceed();
        if (results.isEmpty()) {
            return results;
        }
        Object result0 = results.get(0);
        DecryptClass decryptClass = result0.getClass().getAnnotation(DecryptClass.class);
        if(decryptClass == null || !decryptClass.isEnable()){
            return results;
        }
        final ResultSetHandler statementHandler = PluginUtil.realTarget(invocation.getTarget());
        final MetaObject metaObject = SystemMetaObject.forObject(statementHandler);
        final MappedStatement mappedStatement = (MappedStatement)metaObject.getValue(MAPPED_STATEMENT);
        final ResultMap resultMap = mappedStatement.getResultMaps().isEmpty() ? null : mappedStatement.getResultMaps().get(0);
        final Map<String, DecryptField> decryptFieldMap = getDecryptByResultMap(resultMap);
        for (Object obj: results) {
            final MetaObject objMetaObject = mappedStatement.getConfiguration().newMetaObject(obj);
            for (Map.Entry<String, DecryptField> entry : decryptFieldMap.entrySet()) {
                String property = entry.getKey();
                DecryptField decryptField = entry.getValue();
                String value = (String) objMetaObject.getValue(property);
                if (value != null) {
                    String decryptValue = EncryptHandlerRegistry.get(decryptField.value()).decrypt(value);
                    objMetaObject.setValue(property, decryptValue);
                }
            }
        }
        return results;
    }

    private Map<String, DecryptField> getDecryptByResultMap(ResultMap resultMap) {
        if (resultMap == null) {
            return new HashMap<>(16);
        }
        return getDecryptByType(resultMap.getType());
    }

    private Map<String, DecryptField> getDecryptByType(Class<?> clazz) {
        Map<String, DecryptField> decryptFieldMap = new HashMap<>(16);
        Field[] filedList = FieldUtils.getAllFields(clazz);
        for (Field field: filedList) {
            DecryptField decryptField = field.getAnnotation(DecryptField.class);
            if (decryptField != null) {
                decryptFieldMap.put(field.getName(), decryptField);
            }
        }
        return decryptFieldMap;
    }

    @Override
    public Object plugin(Object o) {
        return Plugin.wrap(o, this);
    }

    @Override
    public void setProperties(Properties properties) {
    }
}

package com.litian.dancechar.framework.encrypt.interceptor;

import com.litian.dancechar.framework.encrypt.annotation.EncryptClass;
import com.litian.dancechar.framework.encrypt.annotation.EncryptField;
import com.litian.dancechar.framework.encrypt.handler.EncryptHandlerRegistry;
import com.litian.dancechar.framework.encrypt.util.PluginUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.Configuration;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 写入表字段加密拦截器
 *
 * @author tojson
 * @date 2021/8/28 08:17
 */
@Intercepts({
        @Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class}),
})
@Slf4j
public class EncryptInterceptor implements Interceptor {
    private static final String MAPPED_STATEMENT="delegate.mappedStatement";
    private static final String BOUND_SQL="delegate.boundSql";

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = PluginUtil.realTarget(invocation.getTarget());
        MetaObject metaObject = SystemMetaObject.forObject(statementHandler);
        MappedStatement mappedStatement = (MappedStatement)metaObject.getValue(MAPPED_STATEMENT);
        SqlCommandType commandType = mappedStatement.getSqlCommandType();

        BoundSql boundSql = (BoundSql)metaObject.getValue(BOUND_SQL);
        Object params = boundSql.getParameterObject();
        if(params instanceof Map){
            return invocation.proceed();
        }
        EncryptClass encryptClass = params != null ? params.getClass().getAnnotation(EncryptClass.class) :
                null;
        if(encryptClass != null && encryptClass.isEnable()){
            handleParameters(mappedStatement.getConfiguration(), boundSql,params,commandType);
        }
        return invocation.proceed();
    }

    private void handleParameters(Configuration configuration, BoundSql boundSql, Object param, SqlCommandType commandType) throws Exception {
        Map<String, Object> newValues = new HashMap<>(16);
        MetaObject metaObject = configuration.newMetaObject(param);
        for (Field field : param.getClass().getDeclaredFields()) {
            String filedName = field.getName();
            if("serialVersionUID".equals(filedName) || !metaObject.hasGetter(filedName)){
                continue;
            }
            Object value = metaObject.getValue(filedName);
            Object newValue = value;
            if(value instanceof CharSequence){
                newValue = handleEncryptField(field,newValue);
            }
            if(value != null && newValue != null && !value.equals(newValue)) {
                newValues.put(filedName, newValue);
            }
        }
        for (Map.Entry<String, Object> entry: newValues.entrySet()) {
            boundSql.setAdditionalParameter(entry.getKey(),entry.getValue());
        }
    }

    private Object handleEncryptField(Field field, Object value) {
        EncryptField encryptField = field.getAnnotation(EncryptField.class);
        Object newValue = value;
        if (encryptField != null && value != null) {
            newValue = EncryptHandlerRegistry.get(encryptField.value()).encrypt(value.toString());
        }
        return newValue;
    }

    @Override
    public Object plugin(Object o) {
        return Plugin.wrap(o, this);
    }

    @Override
    public void setProperties(Properties properties) {
    }
}

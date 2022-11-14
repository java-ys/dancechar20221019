package com.litian.dancechar.framework.common.mybatis;

import cn.hutool.core.convert.Convert;
import cn.hutool.db.sql.SqlFormatter;
import cn.hutool.json.JSONUtil;
import com.google.common.base.Stopwatch;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Mybatis sql拦截器
 *
 * @author tojson
 * @date 2021/6/14 22:13
 */
@Intercepts({
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class,
                RowBounds.class, ResultHandler.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class,
                RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class})
})
@Slf4j
@Component
public class MybatisInterceptor implements Interceptor {
    @Resource
    private MybatisConfig mybatisConfig;
    @Resource
    private Environment environment;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        try {
            MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
            Object parameter = null;
            if (invocation.getArgs().length > 1) {
                parameter = invocation.getArgs()[1];
            }
            // 限制查询最多的条数，防止内存被耗尽
            if (mybatisConfig.getQueryMaxEnable()) {
                int srcMaxRecords = mybatisConfig.getQueryMaxRecords();
                invocation.getArgs()[1] = new RowBounds(0, srcMaxRecords);
                log.info("mybatis拦截器限制最多返回数量-{}", srcMaxRecords);
            }
            BoundSql boundSql = mappedStatement.getBoundSql(parameter);
            Configuration configuration = mappedStatement.getConfiguration();
            Stopwatch stopwatch = Stopwatch.createStarted();
            Object returnVal = invocation.proceed();
            Boolean enableSqlFormat = Convert.toBool(environment.getProperty("enableSqlFormat"), true);
            if(enableSqlFormat){
                String sql = getSql(configuration, boundSql);
                log.info("执行sql-{}", SqlFormatter.format(sql));
                if (returnVal instanceof List<?>) {
                    List<?> list = (ArrayList<?>) returnVal;
                    log.info("返回sql执行结果-耗时:{}ms,返回总行数:{},结果集:{}", stopwatch.elapsed(TimeUnit.MILLISECONDS),
                            list.size(), JSONUtil.toJsonStr(returnVal));
                } else if (returnVal instanceof Integer) {
                    log.info("返回sql执行结果-耗时:{}ms,成功执行行数:{}", stopwatch.elapsed(TimeUnit.MILLISECONDS), Convert.toInt(returnVal));
                } else {
                    log.info("返回sql执行结果-耗时:{}ms,结果集:{}", stopwatch.elapsed(TimeUnit.MILLISECONDS), JSONUtil.toJsonStr(returnVal));
                }
            }
            return returnVal;
        } catch (Exception e) {
            log.error("mybatis拦截器执行sql出现系统异常!errMsg:{}", e.getMessage(), e);
            return invocation.proceed();
        }
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties arg0) {
    }

    /**
     * 获取SQL
     */
    private String getSql(Configuration configuration, BoundSql boundSql) {
        Object parameterObject = boundSql.getParameterObject();
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        String sql = boundSql.getSql().replaceAll("[\\s]+", " ");
        if (parameterObject == null || parameterMappings.size() == 0) {
            return sql;
        }
        TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
        if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
            sql = sql.replaceFirst("\\?", getParameterValue(parameterObject));
        } else {
            MetaObject metaObject = configuration.newMetaObject(parameterObject);
            for (ParameterMapping parameterMapping : parameterMappings) {
                String propertyName = parameterMapping.getProperty();
                if (metaObject.hasGetter(propertyName)) {
                    Object obj = metaObject.getValue(propertyName);
                    sql = sql.replaceFirst("\\?", getParameterValue(obj));
                } else if (boundSql.hasAdditionalParameter(propertyName)) {
                    Object obj = boundSql.getAdditionalParameter(propertyName);
                    sql = sql.replaceFirst("\\?", getParameterValue(obj));
                }
            }
        }
        return sql;
    }

    private String getParameterValue(Object obj) {
        String value = null;
        if (obj instanceof String) {
            value = "'" + obj.toString() + "'";
        } else if (obj instanceof Date) {
            DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.CHINA);
            value = "'" + formatter.format(obj) + "'";
        } else {
            if (obj != null) {
                value = obj.toString();
            } else {
                value = "";
            }
        }
        return value;
    }
}
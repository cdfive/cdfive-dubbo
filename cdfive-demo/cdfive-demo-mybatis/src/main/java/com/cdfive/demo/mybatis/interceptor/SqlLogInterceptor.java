package com.cdfive.demo.mybatis.interceptor;

import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.ResultHandler;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StopWatch;

import java.lang.reflect.Proxy;
import java.sql.Statement;
import java.util.*;

/**
 * @author cdfive
 */
@RequiredArgsConstructor
@Intercepts({
        @Signature(type = StatementHandler.class, method = "query", args = {Statement.class, ResultHandler.class}),
        @Signature(type = StatementHandler.class, method = "update", args = Statement.class),
        @Signature(type = StatementHandler.class, method = "batch", args = Statement.class)})
@Slf4j
@SuppressWarnings("all")
public class SqlLogInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object result;
        StopWatch stopWatch = new StopWatch();
        try {
            stopWatch.start();
            result = invocation.proceed();
        } catch (Throwable e) {
            throw e;
        } finally {
            stopWatch.stop();
            try {
                final String originalSql = getSql(invocation);
                Object target = PluginUtils.realTarget(invocation.getTarget());
                MetaObject metaObject = SystemMetaObject.forObject(target);
                MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
                String id = mappedStatement.getId();
                if (log.isInfoEnabled()) {
                    log.info(
                            "\n\n------------ 执行sql开始  ------------\n" + "id: {}\nsql: {}\ntime: {}ms"
                                    + "\n------------   执行sql结束   ------------\n",
                            id, originalSql, stopWatch.getTotalTimeMillis());
                }
            } catch (Exception e) {
            }
        }
        return result;

    }

    public String getSql(Invocation invocation) {
        Statement statement;
        Object firstArg = invocation.getArgs()[0];
        if (Proxy.isProxyClass(firstArg.getClass())) {
            statement = (Statement) SystemMetaObject.forObject(firstArg).getValue("h.statement");
        } else {
            statement = (Statement) firstArg;
        }
        MetaObject stmtMetaObj = SystemMetaObject.forObject(statement);
        try {
            // Druid
            statement = (Statement) stmtMetaObj.getValue("stmt.statement");
        } catch (Exception e) {
        }
        if (stmtMetaObj.hasGetter("delegate")) {
            // Hikari
            try {
                statement = (Statement) stmtMetaObj.getValue("delegate");
            } catch (Exception e) {
            }
        }
        String originalSql = statement.toString();
        originalSql = originalSql.replaceAll("[\\s]+", StringPool.SPACE);
        int index = indexOfSqlStart(originalSql);
        if (index > 0) {
            originalSql = originalSql.substring(index);
        }
        return originalSql;
    }

    /**
     * 获取sql语句开头部分
     *
     * @param sql ignore
     * @return ignore
     */
    private int indexOfSqlStart(String sql) {
        String upperCaseSql = sql.toUpperCase();
        Set<Integer> set = new HashSet<>();
        set.add(upperCaseSql.indexOf("SELECT "));
        set.add(upperCaseSql.indexOf("UPDATE "));
        set.add(upperCaseSql.indexOf("INSERT "));
        set.add(upperCaseSql.indexOf("DELETE "));
        set.remove(-1);
        if (CollectionUtils.isEmpty(set)) {
            return -1;
        }
        List<Integer> list = new ArrayList<>(set);
        list.sort(Comparator.naturalOrder());
        return list.get(0);
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof StatementHandler) {
            return Plugin.wrap(target, this);
        }
        return target;
    }

}

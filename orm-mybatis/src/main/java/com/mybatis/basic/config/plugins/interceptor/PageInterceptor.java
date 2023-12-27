package com.mybatis.basic.config.plugins.interceptor;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import javax.naming.ConfigurationException;
import java.util.Properties;

@Intercepts({@Signature(type = Executor.class, method = "query",
        args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})})
public class PageInterceptor implements Interceptor {

    static int MAPPED_STATEMENT_INDEX = 0;
    static int PARAMETER_INDEX = 1;
    static int ROWBOUNDS_INDEX = 2;
    static int RESULT_HANDLER_INDEX = 3;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        processIntercept(invocation.getArgs());
        return invocation.proceed();
    }

    public void processIntercept(Object[] queryArgs) throws ConfigurationException {
        // 当前环境 MappedStatement，BoundSql，及sql取得
        MappedStatement mappedStatement = (MappedStatement) queryArgs[MAPPED_STATEMENT_INDEX];
        Object parameter = queryArgs[PARAMETER_INDEX];
        RowBounds rowBounds = (RowBounds) queryArgs[ROWBOUNDS_INDEX];
        int offset = rowBounds.getOffset();
        int limit = rowBounds.getLimit();
        if (offset != 0 || limit != Integer.MAX_VALUE) {
            // Dialect dialect = getDialect(mappedStatement.getConfiguration());
            BoundSql boundSql = mappedStatement.getBoundSql(parameter);
            String sql = boundSql.getSql().trim();
            // sql = dialect.getPaginationSql(sql, offset, limit);
            offset = 0;
            limit = Integer.MAX_VALUE;
            queryArgs[ROWBOUNDS_INDEX] = new RowBounds(offset, limit);
            BoundSql newBoundSql = new BoundSql(mappedStatement.getConfiguration(), sql, boundSql.getParameterMappings(), boundSql.getParameterObject());
            MappedStatement newMs = copyFromMappedStatement(mappedStatement, new BoundSqlSqlSource(newBoundSql));
            queryArgs[MAPPED_STATEMENT_INDEX] = newMs;
        }
    }

    // private Dialect getDialect(Configuration configuration) throws ConfigurationException {
    //     Dialect.Type databaseType = null;
    //     try {
    //         databaseType = Dialect.Type.valueOf(configuration.getVariables().getProperty("dialect").toUpperCase());
    //     } catch (Exception e) {
    //         throw new ConfigurationException("the value of the dialect property in mybatis-config.xml is not defined");
    //     }
    //     Dialect dialect = null;
    //     switch (databaseType) {
    //         case MYSQL:
    //             dialect = new MySQL5Dialect();
    //         case SQLSERVER:
    //             dialect = new SqlServerDialect();
    //         case ORACLE:
    //             dialect = new OracleDialect();
    //     }
    //     return dialect;
    // }

    private MappedStatement copyFromMappedStatement(MappedStatement ms, SqlSource newSqlSource) {
        MappedStatement.Builder builder = new MappedStatement.Builder(ms.getConfiguration(), ms.getId(), newSqlSource, ms.getSqlCommandType());
        builder.resource(ms.getResource());
        builder.fetchSize(ms.getFetchSize());
        builder.statementType(ms.getStatementType());
        builder.keyGenerator(ms.getKeyGenerator());
        builder.keyProperty(ms.getKeyProperties()[0]);
        builder.timeout(ms.getTimeout());
        builder.parameterMap(ms.getParameterMap());
        builder.resultMaps(ms.getResultMaps());
        builder.resultSetType(ms.getResultSetType());
        builder.cache(ms.getCache());
        builder.flushCacheRequired(ms.isFlushCacheRequired());
        builder.useCache(ms.isUseCache());
        return builder.build();
    }

    public Object plugin(Object arg0) {
        return Plugin.wrap(arg0, this);
    }

    public void setProperties(Properties properties) {
    }
}

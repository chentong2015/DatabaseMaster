package mybatis_basics.config.plugins;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;

import java.util.Properties;

// 默认情况下，MyBatis允许使用插件来拦截的方法调用包括
// 1. Executor (update, query, flushStatements, commit, rollback, getTransaction, close, isClosed)
//    拦截Executor接口的部分方法，比如update，query，commit，rollback等方法
// 2. ParameterHandler (getParameterObject, setParameters)
// 3. ResultSetHandler (handleResultSets, handleOutputParameters)
// 4. StatementHandler (prepare, parameterize, batch, update, query)

// TODO: 自定义的拦截器拦截Executor接口的update方法(SqlSession的新增，删除，修改操作)
//       所有执行executor的update方法都会被该拦截器拦截到
@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})})
public class MyPlugin implements Interceptor {

    private Properties properties = new Properties();

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // implement pre-processing if needed
        Object returnObject = invocation.proceed();
        // implement post-processing if needed
        return returnObject;
    }

    @Override
    public void setProperties(Properties properties) {
        this.properties = properties;
    }
}

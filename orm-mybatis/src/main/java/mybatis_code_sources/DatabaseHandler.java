package mybatis_code_sources;

// TODO: Mybatis有三种执行器，默认是SimpleExecutor()
// MyBatis分为一级缓存和二级缓存
// 1. 优点：本地缓存不需要走网络IO和磁盘IO，提升查询效率
// 2. 缺点: 数据库更换造成缓存数据一致性问题 ==> "缓存双写一致性问题"

// 通过Executor执行器封装对数据库操作：Connection, PrepareStatement, ResultSet
// 默认Configuration.cacheEnabled=true开启了缓存，将会使用CachingExecutor
// 1. 默认情况下，只启用了本地的会话缓存，它仅仅对一个会话中的数据进行缓存
// 2. 要启用全局的二级缓存，只需要在你的SQL映射文件中添加一行 <cache/>
public class DatabaseHandler {

    // Configuration.newExecutor()
    // public Executor newExecutor(Transaction transaction, ExecutorType executorType) {
    //     executorType = executorType == null ? this.defaultExecutorType : executorType;
    //     executorType = executorType == null ? ExecutorType.SIMPLE : executorType;
    //     Object executor;
    //     if (ExecutorType.BATCH == executorType) {
    //         executor = new BatchExecutor(this, transaction);
    //     } else if (ExecutorType.REUSE == executorType) {
    //         executor = new ReuseExecutor(this, transaction);
    //     } else {
    //         executor = new SimpleExecutor(this, transaction);
    //     }
    //     if (this.cacheEnabled) {
    //         executor = new CachingExecutor((Executor)executor);
    //     }
    //     Executor executor = (Executor)this.interceptorChain.pluginAll(executor);
    //     return executor;
    // }

    // SimpleExecutor > BaseExecutor.query() 调用的方法
    // public <E> List<E> query(MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, CacheKey key, BoundSql boundSql) throws SQLException {
    //   orContext.instance().resource(ms.getResource()).activity("executing a query").object(ms.getId());
    //   (this.closed) {
    //    throw new ExecutorException("Executor was closed.");
    //   lse {
    //    if (this.queryStack == 0 && ms.isFlushCacheRequired()) {
    //        this.clearLocalCache();
    //    }
    //    List list;
    //    try {
    //        ++this.queryStack;
    //        // 缓存非空则从本地缓存中取获取，反之从数据库中获取
    //        list = resultHandler == null ? (List)this.localCache.getObject(key) : null;
    //        if (list != null) {
    //            this.handleLocallyCachedOutputParameters(ms, key, parameter, boundSql);
    //        } else {
    //            list = this.queryFromDatabase(ms, parameter, rowBounds, resultHandler, key, boundSql);
    //        }
    //    } finally {
    //        --this.queryStack;
    //    }
    //    ...

    //  BaseExecutor.queryFromDatabase()
    //  this.doQuery()在查询完成之后，保证清除本地的缓存中指定的key，然后在.putObject(key, list)设置key
    //  private <E> List<E> queryFromDatabase(MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, CacheKey key, BoundSql boundSql) throws SQLException {
    //        this.localCache.putObject(key, ExecutionPlaceholder.EXECUTION_PLACEHOLDER);
    //        List list;
    //        try {
    //            list = this.doQuery(ms, parameter, rowBounds, resultHandler, boundSql);
    //        } finally {
    //            this.localCache.removeObject(key);
    //        }
    //        this.localCache.putObject(key, list);
    //        if (ms.getStatementType() == StatementType.CALLABLE) {
    //            this.localOutputParameterCache.putObject(key, parameter);
    //        }
    //        return list;
    //    }

    // 最后底层使用JDBC的PrepareStatement, ResultSet... ???

    // CachingExecutor.query()
    // public <E> List<E> query(MappedStatement ms, Object parameterObject, RowBounds rowBounds, ResultHandler resultHandler) throws SQLException {
    //     BoundSql boundSql = ms.getBoundSql(parameterObject);
    //     // 使用4个参数封装成CacheKey
    //     CacheKey key = this.createCacheKey(ms, parameterObject, rowBounds, boundSql);
    //     return this.query(ms, parameterObject, rowBounds, resultHandler, key, boundSql);
    // }
}

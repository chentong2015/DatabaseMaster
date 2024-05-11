package source;

// 通过Executor执行器封装对数据库操作：Connection, PrepareStatement, ResultSet
// 1. Mybatis有3+1种执行器，默认是SimpleExecutor()
// 2. 需要考虑事务(BaseExecutor > transaction)和不同的隔离级别
public class MybatisExecutorHandler {

    // Configuration.newExecutor()
    // public Executor newExecutor(Transaction transaction, ExecutorType executorType) {
    //     executorType = executorType == null ? this.defaultExecutorType : executorType;
    //     executorType = executorType == null ? ExecutorType.SIMPLE : executorType;
    //     Object executor;
    //     if (ExecutorType.BATCH == executorType) {
    //         executor = new BatchExecutor(this, transaction); 批量执行器
    //     } else if (ExecutorType.REUSE == executorType) {
    //         executor = new ReuseExecutor(this, transaction); 复用执行器
    //     } else {
    //         executor = new SimpleExecutor(this, transaction); 简单执行器
    //     }
    //     if (this.cacheEnabled) {
    //         executor = new CachingExecutor((Executor)executor);
    //     }
    //     调用插件链的加载插件功能，形成调用链
    //     Executor executor = (Executor)this.interceptorChain.pluginAll(executor);
    //     return executor;
    // }

    // CachingExecutor.query() 简单的缓存执行器的query()
    // public <E> List<E> query(MappedStatement ms, Object parameterObject, RowBounds rowBounds, ResultHandler resultHandler) throws SQLException {
    //     BoundSql boundSql = ms.getBoundSql(parameterObject);
    //     // TODO: 使用4个参数封装成CacheKey缓存key值
    //     CacheKey key = this.createCacheKey(ms, parameterObject, rowBounds, boundSql);
    //     return this.query(ms, parameterObject, rowBounds, resultHandler, key, boundSql);
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
    //        // TODO: 缓存非空则从本地缓存中取获取，反之从数据库中获取
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
    //  this.doQuery()查询完成之后，保证清除本地的缓存中指定的key，然后在.putObject(key, list)设置key
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

    // 最终执行的SimpleExecutor.doQuery()
    // public <E> List<E> doQuery(MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) throws SQLException {
    //        Statement stmt = null;
    //        List var9;
    //        try {
    //            Configuration configuration = ms.getConfiguration();
    //            // 这里根据不同的StatementType，创建不同的StatementHandler
    //            StatementHandler handler = configuration.newStatementHandler(this.wrapper, ms, parameter, rowBounds, resultHandler, boundSql);
    //            stmt = this.prepareStatement(handler, ms.getStatementLog());
    //            // 最钟由RoutingStatementHandler创建指定的StatementHandler来执行.query()
    //            var9 = handler.query(stmt, resultHandler);
    //        } finally {
    //            this.closeStatement(stmt);
    //        }
    //
    //        return var9;
    //    }

    // configuration.newStatementHandler()
    //  public StatementHandler newStatementHandler(Executor executor, MappedStatement mappedStatement, Object parameterObject, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) {
    //    StatementHandler statementHandler = new RoutingStatementHandler(executor, mappedStatement, parameterObject, rowBounds, resultHandler, boundSql);
    //    statementHandler = (StatementHandler) interceptorChain.pluginAll(statementHandler);
    //    return statementHandler;
    //  }

    // 在准备Statement的过程中，底层使用JDBC完成数据库的连接
    // 调用SimpleExecutor.getConnection()
    // private Statement prepareStatement(StatementHandler handler, Log statementLog) throws SQLException {
    //    Statement stmt;
    //    Connection connection = getConnection(statementLog);
    //    stmt = handler.prepare(connection, transaction.getTimeout());
    //    handler.parameterize(stmt);
    //    return stmt;
    //  }

    // TODO: 根据项目的配置信息，设置<transactionManager type="JDBC"/>，源码中将使用JdbcTransaction
    // 调用BaseExecutor.getConnection()
    // protected Connection getConnection(Log statementLog) throws SQLException {
    //   Connection connection = transaction.getConnection();
    //   if (statementLog.isDebugEnabled()) {
    //     return ConnectionLogger.newInstance(connection, statementLog, queryStack);
    //   } else {
    //     return connection;
    //   }
    // }

    // JdbcTransaction.getConnection()
    // protected void openConnection() throws SQLException {
    //    if (log.isDebugEnabled()) {
    //      log.debug("Opening JDBC Connection");
    //    }
    //    connection = dataSource.getConnection();
    //    if (level != null) {
    //      connection.setTransactionIsolation(level.getLevel());
    //    }
    //    setDesiredAutoCommit(autoCommit);
    //  }

    // TODO: 根据项目的配置信息，设置<dataSource type="POOLED">，源码中将使用PooledDataSource, 构建PooledConnection数据库连接
    //       PooledDataSource 实现了DataSource接口
    // @Override
    // public Connection getConnection() throws SQLException {
    //    return popConnection(dataSource.getUsername(), dataSource.getPassword()).getProxyConnection();
    // }
    //  private PooledConnection popConnection(String username, String password) throws SQLException {
    //    boolean countedWait = false;
    //    PooledConnection conn = null;
    //    long t = System.currentTimeMillis();
    //    int localBadConnectionCount = 0;
    //    while (conn == null) {
    //      synchronized (state) {
    //        if (!state.idleConnections.isEmpty()) {
    //          // Pool has available connection
    //          conn = state.idleConnections.remove(0);
    //          if (log.isDebugEnabled()) {
    //            log.debug("Checked out connection " + conn.getRealHashCode() + " from pool.");
    //          }
    //        } else {
    //          // Pool does not have available connection
    //          if (state.activeConnections.size() < poolMaximumActiveConnections) {
    //            // dataSource = = new UnpooledDataSource();
    //            // 这里通过UnpooledDataSource的方法来最终构建PooledConnection的实例
    //            conn = new PooledConnection(dataSource.getConnection(), this);
    //  ...

    //  UnpooledDataSource.getConnection()
    //  @Override
    //  public Connection getConnection() throws SQLException {
    //    return doGetConnection(username, password);
    //  }

    // private Connection doGetConnection(String username, String password) throws SQLException {
    //    Properties props = new Properties();
    //    if (driverProperties != null) {
    //      props.putAll(driverProperties);
    //    }
    //    if (username != null) {
    //      props.setProperty("user", username);
    //    }
    //    if (password != null) {
    //      props.setProperty("password", password);
    //    }
    //    return doGetConnection(props);
    //  }
    //
    //  private Connection doGetConnection(Properties properties) throws SQLException {
    //    initializeDriver();
    //    // 最底层JDBC连接数据库的代码, 类型java.sql.Connection
    //    Connection connection = DriverManager.getConnection(url, properties);
    //    configureConnection(connection);
    //    return connection;
    //  }

    // TODO: RoutingStatementHandler路由声明处理器, 该类型使用delegate委托模式
    // public RoutingStatementHandler(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) {
    //    switch (ms.getStatementType()) {
    //      case STATEMENT:
    //        delegate = new SimpleStatementHandler(executor, ms, parameter, rowBounds, resultHandler, boundSql);
    //        break;
    //      case PREPARED:
    //        delegate = new PreparedStatementHandler(executor, ms, parameter, rowBounds, resultHandler, boundSql);
    //        break;
    //      case CALLABLE:
    //        delegate = new CallableStatementHandler(executor, ms, parameter, rowBounds, resultHandler, boundSql);
    //        break;
    //      default:
    //        throw new ExecutorException("Unknown statement type: " + ms.getStatementType());
    //    }
    //  }

    // PreparedStatementHandler.query()
    //  @Override
    //  public <E> List<E> query(Statement statement, ResultHandler resultHandler) throws SQLException {
    //    PreparedStatement ps = (PreparedStatement) statement;
    //    ps.execute();
    //    return resultSetHandler.handleResultSets(ps);
    //  }

    // TODO: 最后使用DefaultResultSetHandler来获得ResultSet执行的结果
    // @Override
    // public List<Object> handleResultSets(Statement stmt) throws SQLException {
    //   ErrorContext.instance().activity("handling results").object(mappedStatement.getId());
    //   final List<Object> multipleResults = new ArrayList<>();
    //   int resultSetCount = 0;
    //   ResultSetWrapper rsw = getFirstResultSet(stmt);
    //
    //   List<ResultMap> resultMaps = mappedStatement.getResultMaps();
    //   int resultMapCount = resultMaps.size();
    //   validateResultMapsCount(rsw, resultMapCount);
    //   while (rsw != null && resultMapCount > resultSetCount) {
    //     ResultMap resultMap = resultMaps.get(resultSetCount);
    //     handleResultSet(rsw, resultMap, multipleResults, null);
    //     rsw = getNextResultSet(stmt);
    //     cleanUpAfterHandlingResultSet();
    //     resultSetCount++;
    //   }
    //   ...

    //  private void handleResultSet(ResultSetWrapper rsw, ResultMap resultMap, List<Object> multipleResults, ResultMapping parentMapping) throws SQLException {
    //    try {
    //      if (parentMapping != null) {
    //        handleRowValues(rsw, resultMap, null, RowBounds.DEFAULT, parentMapping);
    //      } else {
    //        if (resultHandler == null) {
    //          DefaultResultHandler defaultResultHandler = new DefaultResultHandler(objectFactory);
    //          handleRowValues(rsw, resultMap, defaultResultHandler, rowBounds, null);
    //          multipleResults.add(defaultResultHandler.getResultList());
    //        } else {
    //          handleRowValues(rsw, resultMap, resultHandler, rowBounds, null);
    //        }
    //      }
    //    } finally {
    //      // issue #228 (close resultsets)
    //      closeResultSet(rsw.getResultSet());
    //    }
    //  }

    //  public void handleRowValues(ResultSetWrapper rsw, ResultMap resultMap, ResultHandler<?> resultHandler, RowBounds rowBounds, ResultMapping parentMapping) throws SQLException {
    //    if (resultMap.hasNestedResultMaps()) {
    //      ensureNoRowBounds();
    //      checkResultHandler();
    //      handleRowValuesForNestedResultMap(rsw, resultMap, resultHandler, rowBounds, parentMapping);
    //    } else {
    //      handleRowValuesForSimpleResultMap(rsw, resultMap, resultHandler, rowBounds, parentMapping);
    //    }
    //  }

    // private void handleRowValuesForSimpleResultMap(ResultSetWrapper rsw, ResultMap resultMap, ResultHandler<?> resultHandler, RowBounds rowBounds, ResultMapping parentMapping)
    //      throws SQLException {
    //    DefaultResultContext<Object> resultContext = new DefaultResultContext<>();
    //    ResultSet resultSet = rsw.getResultSet();
    //    skipRows(resultSet, rowBounds);
    //    while (shouldProcessMoreRows(resultContext, rowBounds) && !resultSet.isClosed() && resultSet.next()) {
    //      ResultMap discriminatedResultMap = resolveDiscriminatedResultMap(resultSet, resultMap, null);
    //      Object rowValue = getRowValue(rsw, discriminatedResultMap, null);
    //      storeObject(resultHandler, resultContext, rowValue, parentMapping, resultSet);
    //    }
    //  }

    // private Object getRowValue(ResultSetWrapper rsw, ResultMap resultMap, String columnPrefix) throws SQLException {
    //    final ResultLoaderMap lazyLoader = new ResultLoaderMap();
    //    Object rowValue = createResultObject(rsw, resultMap, lazyLoader, columnPrefix);
    //    if (rowValue != null && !hasTypeHandlerForResultObject(rsw, resultMap.getType())) {
    //      final MetaObject metaObject = configuration.newMetaObject(rowValue);
    //      boolean foundValues = this.useConstructorMappings;
    //      if (shouldApplyAutomaticMappings(resultMap, false)) {
    //        foundValues = applyAutomaticMappings(rsw, resultMap, metaObject, columnPrefix) || foundValues;
    //      }
    //      foundValues = applyPropertyMappings(rsw, resultMap, metaObject, lazyLoader, columnPrefix) || foundValues;
    //      foundValues = lazyLoader.size() > 0 || foundValues;
    //      rowValue = foundValues || configuration.isReturnInstanceForEmptyRow() ? rowValue : null;
    //    }
    //    return rowValue;
    //  }

    // private boolean applyAutomaticMappings(ResultSetWrapper rsw, ResultMap resultMap, MetaObject metaObject, String columnPrefix) throws SQLException {
    //    List<UnMappedColumnAutoMapping> autoMapping = createAutomaticMappings(rsw, resultMap, metaObject, columnPrefix);
    //    boolean foundValues = false;
    //    if (!autoMapping.isEmpty()) {
    //      for (UnMappedColumnAutoMapping mapping : autoMapping) {
    //        // TODO: 最后调用不同的TypeHandler来获取结果
    //        // IntegerTypeHandler.getInt(columnName);
    //        // StringTypeHandler.getString(columnName);
    //        final Object value= mapping.typeHandler.getResult(rsw.getResultSet(), mapping.column);
    //        if (value != null) {
    //          foundValues = true;
    //        }
    //        if (value != null || (configuration.isCallSettersOnNulls() && !mapping.primitive)) {
    //          // gcode issue #377, call setter on nulls (value is not 'found')
    //          metaObject.setValue(mapping.property, value);
    //        }
    //      }
    //    }
    //    return foundValues;
    //  }
}

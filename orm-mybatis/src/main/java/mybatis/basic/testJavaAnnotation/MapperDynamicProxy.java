package mybatis.basic.testJavaAnnotation;

// TODO: 通过提供接口类型，如何构建BlogMapper接口类型的实例化对象 ?
// 通过动态代理调用到对应的session方法
// 动态代理优势: 即使接口中定义的方法selectBlog()改变，最终底层根据command.getType()调用到对应方法
public class MapperDynamicProxy {

    // BlogMapper mapper = session.getMapper(BlogMapper.class);
    // Blog blog = mapper.selectBlog(10);

    // 调用DefaultSqlSession.getMapper()方法
    //  @Override
    //  public <T> T getMapper(Class<T> type) {
    //    return configuration.getMapper(type, this);
    //  }

    // 调用configuration.getMapper()方法
    //  public <T> T getMapper(Class<T> type, SqlSession sqlSession) {
    //    return mapperRegistry.getMapper(type, sqlSession);
    //  }

    // 调用MapperRegistry.getMapper()方法
    // public <T> void addMapper(Class<T> type) {
    //    if (type.isInterface()) {
    //      if (hasMapper(type)) {
    //        throw new BindingException("Type " + type + " is already known to the MapperRegistry.");
    //      }
    //      boolean loadCompleted = false;
    //      try {
    //        knownMappers.put(type, new MapperProxyFactory<>(type)); 使用动态代理
    //        MapperAnnotationBuilder parser = new MapperAnnotationBuilder(config, type);  获取注解SQL的实现
    //        parser.parse();
    //        loadCompleted = true;
    //      } finally {
    //        if (!loadCompleted) {
    //          knownMappers.remove(type);
    //        }
    //      }
    //    }
    //  }

    // 调用MapperProxyFactory.newInstance()方法, 其中会创建MapperProxy对象，作为handler
    // public T newInstance(SqlSession sqlSession) {
    //    final MapperProxy<T> mapperProxy = new MapperProxy<>(sqlSession, mapperInterface, methodCache);
    //    return newInstance(mapperProxy);
    //  }
    //
    // public class MapperProxy<T> implements InvocationHandler, Serializable {
    //   ...
    //   @Override
    //   public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    //      try {
    //        if (Object.class.equals(method.getDeclaringClass())) {
    //          return method.invoke(this, args);
    //        } else {
    //          return cachedInvoker(method).invoke(proxy, method, args, sqlSession);
    //        }
    //      } catch (Throwable t) {
    //        throw ExceptionUtil.unwrapThrowable(t);
    //      }
    //   }
    // }

    // protected T newInstance(MapperProxy<T> mapperProxy) {
    //    return (T) Proxy.newProxyInstance(mapperInterface.getClassLoader(), new Class[] { mapperInterface }, mapperProxy);
    // }

    // private static class PlainMethodInvoker implements MapperMethodInvoker {
    //    private final MapperMethod mapperMethod;
    //    public PlainMethodInvoker(MapperMethod mapperMethod) {
    //      super();
    //      this.mapperMethod = mapperMethod;
    //    }
    //    @Override
    //    public Object invoke(Object proxy, Method method, Object[] args, SqlSession sqlSession) throws Throwable {
    //      return mapperMethod.execute(sqlSession, args);
    //    }
    //  }

    // MapperMethod.execute() 执行的关键方法
    //  public Object execute(SqlSession sqlSession, Object[] args) {
    //    Object result;
    //    switch (command.getType()) {
    //      case INSERT: {
    //        Object param = method.convertArgsToSqlCommandParam(args);
    //        result = rowCountResult(sqlSession.insert(command.getName(), param));
    //        break;
    //      }
    //      case UPDATE: {
    //        Object param = method.convertArgsToSqlCommandParam(args);
    //        result = rowCountResult(sqlSession.update(command.getName(), param));
    //        break;
    //      }
    //      case DELETE: {
    //        Object param = method.convertArgsToSqlCommandParam(args);
    //        result = rowCountResult(sqlSession.delete(command.getName(), param));
    //        break;
    //      }
    //      case SELECT:
    //        if (method.returnsVoid() && method.hasResultHandler()) {
    //          executeWithResultHandler(sqlSession, args);
    //          result = null;
    //        } else if (method.returnsMany()) {
    //          result = executeForMany(sqlSession, args);
    //        } else if (method.returnsMap()) {
    //          result = executeForMap(sqlSession, args);
    //        } else if (method.returnsCursor()) {
    //          result = executeForCursor(sqlSession, args);
    //        } else {
    //          Object param = method.convertArgsToSqlCommandParam(args);
    //          // TODO: 最终执行的方法
    //          result = sqlSession.selectOne(command.getName(), param);
    //          if (method.returnsOptional()
    //              && (result == null || !method.getReturnType().equals(result.getClass()))) {
    //            result = Optional.ofNullable(result);
    //          }
    //        }
    //        break;
    //      case FLUSH:
    //        result = sqlSession.flushStatements();
    //        break;
    //      default:
    //        throw new BindingException("Unknown execution method for: " + command.getName());
    //    }
    //    if (result == null && method.getReturnType().isPrimitive() && !method.returnsVoid()) {
    //      throw new BindingException("Mapper method '" + command.getName()
    //          + " attempted to return null from a method with a primitive return type (" + method.getReturnType() + ").");
    //    }
    //    return result;
    //  }
}

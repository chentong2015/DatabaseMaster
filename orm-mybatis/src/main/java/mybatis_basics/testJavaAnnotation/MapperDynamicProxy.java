package mybatis_basics.testJavaAnnotation;

// MyBatis支持以注解的方式来执行映射语句，声明指定的映射Mapper接口
// BlogMapper mapper = session.getMapper(BlogMapper.class);
// Blog blog = mapper.selectBlog(10);

// TODO: 通过提供接口类型，如何构建BlogMapper接口类型的实例化对象 ?
// MapperProxyFactory > MapperProxy
// 1. 底层通过动态代理(Java基础类库提供的Proxy)，使得在最终执行时，调用到session.selectOne()原始方法
// 2. 动态代理优势: 即使接口中定义的方法selectBlog()改变，最终底层根据command.getType()还是能够调用到对应的方法
public class MapperDynamicProxy {

    // session.getMapper(BlogMapper.class)
    // 调用方法DefaultSqlSession.getMapper()
    //  @Override
    //  public <T> T getMapper(Class<T> type) {
    //    return configuration.getMapper(type, this);
    //  }

    // 调用方法configuration.getMapper()
    //  public <T> T getMapper(Class<T> type, SqlSession sqlSession) {
    //    return mapperRegistry.getMapper(type, sqlSession);
    //  }

    // MapperRegistry.getMapper()
    // public <T> void addMapper(Class<T> type) {
    //    if (type.isInterface()) {
    //      if (hasMapper(type)) {
    //        throw new BindingException("Type " + type + " is already known to the MapperRegistry.");
    //      }
    //      boolean loadCompleted = false;
    //      try {
    //        // 动态代理的实现
    //        knownMappers.put(type, new MapperProxyFactory<>(type));
    //        // 获取注解SQL的实现
    //        MapperAnnotationBuilder parser = new MapperAnnotationBuilder(config, type);
    //        parser.parse();
    //        loadCompleted = true;
    //      } finally {
    //        if (!loadCompleted) {
    //          knownMappers.remove(type);
    //        }
    //      }
    //    }
    //  }

    // MapperProxyFactory.newInstance()
    // public T newInstance(SqlSession sqlSession) {
    //    final MapperProxy<T> mapperProxy = new MapperProxy<>(sqlSession, mapperInterface, methodCache);
    //    return newInstance(mapperProxy);
    //  }
    //
    // TODO: 调用Java原生提供的Proxy代理的实现，mapperProxy作为代理对象
    // protected T newInstance(MapperProxy<T> mapperProxy) {
    //    return (T) Proxy.newProxyInstance(mapperInterface.getClassLoader(), new Class[] { mapperInterface }, mapperProxy);
    // }

    // TODO: 由于MapperProxy<T>实现了动态代理(实现InvocationHandler接口), 调用方法.invoke()
    // public class MapperProxy<T> implements InvocationHandler, Serializable {...}

    //  @Override
    //  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    //    try {
    //      if (Object.class.equals(method.getDeclaringClass())) {
    //        return method.invoke(this, args);
    //      } else {
    //        // 下面调用到嵌套类型的PlainMethodInvoker.invoke()方法
    //        return cachedInvoker(method).invoke(proxy, method, args, sqlSession);
    //      }
    //    } catch (Throwable t) {
    //      throw ExceptionUtil.unwrapThrowable(t);
    //    }
    //  }

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
    //          // 最终执行的方法
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

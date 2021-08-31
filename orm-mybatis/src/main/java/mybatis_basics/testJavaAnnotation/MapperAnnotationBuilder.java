package mybatis_basics.testJavaAnnotation;

// TODO: 如何从注解中拿到SQL ?
// @Select("SELECT * FROM blog WHERE id = #{id}")
// Blog selectBlog(int id);

// public @interface Select {
//   String[] value();
//   ...
// }
// 由于注解中使用的是数组，因此在使用@Select注解时，可以提供多个SQL，返回多结果
// @Select{"SELECT id, name FROM t_user WHERE id = #{id}", "Select * from users"}
public class MapperAnnotationBuilder {

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

    // 最终调用MapperAnnotationBuilder的.parse()方法
    //  public void parse() {
    //    String resource = type.toString();
    //    if (!configuration.isResourceLoaded(resource)) {
    //      loadXmlResource();
    //      configuration.addLoadedResource(resource);
    //      assistant.setCurrentNamespace(type.getName());
    //      parseCache();
    //      parseCacheRef();
    //      for (Method method : type.getMethods()) {
    //        if (!canHaveStatement(method)) {
    //          continue;
    //        }
    //        if (getAnnotationWrapper(method, false, Select.class, SelectProvider.class).isPresent()
    //            && method.getAnnotation(ResultMap.class) == null) {
    //          parseResultMap(method);
    //        }
    //        try {
    //          parseStatement(method);
    //        } catch (IncompleteElementException e) {
    //          configuration.addIncompleteMethod(new MethodResolver(this, method));
    //        }
    //      }
    //    }
    //    parsePendingMethods();
    //  }

    // 调用MapperAnnotationBuilder.loadXmlResource();方法，会先判断是否通过".xml"文件加载
    // TODO: ".xml"配置比使用Annotations注解方式优先级更高
    // private void loadXmlResource() {
    //    // Spring may not know the real resource name so we check a flag
    //    // to prevent loading again a resource twice
    //    // this flag is set at XMLMapperBuilder#bindMapperForNamespace
    //    if (!configuration.isResourceLoaded("namespace:" + type.getName())) {
    //      String xmlResource = type.getName().replace('.', '/') + ".xml";
    //      // #1347
    //      InputStream inputStream = type.getResourceAsStream("/" + xmlResource);
    //      if (inputStream == null) {
    //        // Search XML mapper that is not in the module but in the classpath.
    //        try {
    //          inputStream = Resources.getResourceAsStream(type.getClassLoader(), xmlResource);
    //        } catch (IOException e2) {
    //          // ignore, resource is not required
    //        }
    //      }
    //      if (inputStream != null) {
    //        XMLMapperBuilder xmlParser = new XMLMapperBuilder(inputStream, assistant.getConfiguration(), xmlResource, configuration.getSqlFragments(), type.getName());
    //        xmlParser.parse();
    //      }
    //    }
    //  }

    // 最终底层通过method.getAnnotation(sqlAnnotationType)拿到添加在方法上的指定的注解
    // 然后获取注解中的SQL执行语句，buildSqlSourceFromAnnotation()构造执行语句
}

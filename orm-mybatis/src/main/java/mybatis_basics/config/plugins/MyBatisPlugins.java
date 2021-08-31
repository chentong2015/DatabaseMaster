package mybatis_basics.config.plugins;

// plugins源码解析：扩展机制, 自定义操作
// 1. 插件类必须实现Interceptor接口
// 2. 添加@Intercepte，@Signature注解，指定要拦截的目标方法

// Plugins使用场景:
// 1. 获取SQL执行时间
// 2. 实现分页
public class MyBatisPlugins {

    // 从XML配置文件中获取所有的参数设置XMLConfigBuilder.parseConfiguration()
    // private void parseConfiguration(XNode root) {
    //       try {
    //           // issue #117 read properties first
    //           propertiesElement(root.evalNode("properties"));
    //           Properties settings = settingsAsProperties(root.evalNode("settings"));
    //           loadCustomVfs(settings);
    //           loadCustomLogImpl(settings);
    //           typeAliasesElement(root.evalNode("typeAliases"));
    //           pluginElement(root.evalNode("plugins"));
    //           objectFactoryElement(root.evalNode("objectFactory"));
    //           objectWrapperFactoryElement(root.evalNode("objectWrapperFactory"));
    //           reflectorFactoryElement(root.evalNode("reflectorFactory"));
    //           settingsElement(settings);
    //           // read it after objectFactory and objectWrapperFactory issue #631
    //           environmentsElement(root.evalNode("environments"));
    //           databaseIdProviderElement(root.evalNode("databaseIdProvider"));
    //           typeHandlerElement(root.evalNode("typeHandlers"));
    //           mapperElement(root.evalNode("mappers"));
    //       } catch (Exception e) {
    //           throw new BuilderException("Error parsing SQL Mapper Configuration. Cause: " + e, e);
    //       }
    //  }

    // private void pluginElement(XNode parent) throws Exception {
    //    if (parent != null) {
    //      for (XNode child : parent.getChildren()) {
    //        String interceptor = child.getStringAttribute("interceptor");
    //        Properties properties = child.getChildrenAsProperties();
    //        Interceptor interceptorInstance = (Interceptor) resolveClass(interceptor).getDeclaredConstructor().newInstance();
    //        interceptorInstance.setProperties(properties);
    //        configuration.addInterceptor(interceptorInstance);
    //      }
    //    }
    //  }

    // 最终使用"责任链"的设计模式来实现 + org.apache.ibatis.plugin.Plugin动态代理(最终调用自定义的intercept()方法)
    // protected final InterceptorChain interceptorChain = new InterceptorChain();
    //
    // public class InterceptorChain {
    //  private final List<Interceptor> interceptors = new ArrayList<>();
    //  public Object pluginAll(Object target) {
    //    for (Interceptor interceptor : interceptors) {
    //      target = interceptor.plugin(target);
    //    }
    //    return target;
    //  }
    //  public void addInterceptor(Interceptor interceptor) {
    //    interceptors.add(interceptor);
    //  }
    //  public List<Interceptor> getInterceptors() {
    //    return Collections.unmodifiableList(interceptors);
    //  }
    //}
}

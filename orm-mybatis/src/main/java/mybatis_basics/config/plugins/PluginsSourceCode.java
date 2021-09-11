package mybatis_basics.config.plugins;

// Plugins：插件，拦截器
// TODO: 允许在已映射语句执行过程中的某一点进行拦截调用 / 获取SQL执行时间 & 实现分页
// 1. 插件类必须实现Interceptor接口
// 2. 添加@Interceptes，@Signature注解，指定要拦截的目标方法
public class PluginsSourceCode {

    // 从XML配置文件中获取所有的参数设置XMLConfigBuilder.parseConfiguration()
    // private void parseConfiguration(XNode root) {
    //       try {
    //           propertiesElement(root.evalNode("properties"));
    //           Properties xml_settings = settingsAsProperties(root.evalNode("xml_settings"));
    //           loadCustomVfs(xml_settings);
    //           loadCustomLogImpl(xml_settings);
    //           typeAliasesElement(root.evalNode("typeAliases"));
    //           pluginElement(root.evalNode("plugins"));
    //           objectFactoryElement(root.evalNode("objectFactory"));
    //           objectWrapperFactoryElement(root.evalNode("objectWrapperFactory"));
    //           reflectorFactoryElement(root.evalNode("reflectorFactory"));
    //           settingsElement(xml_settings);
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
    //        TODO: 通过反射，创建自定义拦截器的对象
    //        Interceptor interceptorInstance = (Interceptor) resolveClass(interceptor).getDeclaredConstructor().newInstance();
    //        根据XML中配置的属性设置对象的属性
    //        interceptorInstance.setProperties(properties);
    //        保存到插件链中
    //        configuration.addInterceptor(interceptorInstance);
    //      }
    //    }
    //  }

    // Configuration.addInterceptor
    // public void addInterceptor(Interceptor interceptor) {
    //     interceptorChain.addInterceptor(interceptor);
    // }

    // TODO: "责任链"的设计模式 + Plugin中的Proxy动态代理
    // public class InterceptorChain {
    //    所有拦截器的实例
    //    private final List<Interceptor> interceptors = new ArrayList<>();
    //
    //    public Object pluginAll(Object target) {
    //      for (Interceptor interceptor : interceptors) {
    //        target = interceptor.plugin(target);
    //      }
    //      return target;
    //    }
    //
    //    public void addInterceptor(Interceptor interceptor) {
    //      interceptors.add(interceptor);
    //    }
    //
    //    public List<Interceptor> getInterceptors() {
    //      return Collections.unmodifiableList(interceptors);
    //    }
    //}

    // 上面的pluginAll()方法依次调用每个插件的plugin方法，如果该插件无需拦截target，则直接返回target
    // interface Interceptor
    // default Object plugin(Object target) {
    //        return Plugin.wrap(target, this);
    //  }

    // Plugin.wrap()
    // public static Object wrap(Object target, Interceptor interceptor) {
    //     Map<Class<?>, Set<Method>> signatureMap = getSignatureMap(interceptor);
    //     Class<?> type = target.getClass();
    //     Class<?>[] interfaces = getAllInterfaces(type, signatureMap);
    //     if (interfaces.length > 0) {
    //         return Proxy.newProxyInstance(target.getClass().getClassLoader(),
    //                 interfaces, new Plugin(target, interceptor, signatureMap));
    //     }
    //     return target;
    // }
}

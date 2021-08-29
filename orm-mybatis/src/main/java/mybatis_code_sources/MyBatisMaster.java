package mybatis_code_sources;

// TODO: 对ORM架构源码级别的理解
// https://www.bilibili.com/video/BV1QA411A7q1
// https://www.bilibili.com/video/BV1U64y1W7fw
public class MyBatisMaster {

    // TODO: MyBatis持久层框架的基本组成件，模块和核心功能 ?

    // 数据库开发规范：访问数据库必须使用连接池, 提供可靠性保护：网络中断自动重连, 连接数限制，定期回收连接，设置超时时间等
    // 1. 必须具有自动重连的功能
    // 2. 必须设置何理的超时时间, 防止程序访问数据库长时间的挂死
    // 3. 数据库连接池必须设置最大值, 默认是500个连接
    // 4. Java语言推荐使用mybatis持久层框架来访问数据库

    // 源码级别是如何解析MyBatis-config.xml配置文件的 ?
    // 对于xml配置<configuration>中的所有全局参数，源码中都有对应的解析
    // XMLConfigBuilder.private void parseConfiguration(XNode root) {}
    //
    // 对于环境参数的解析
    // <environments default="development">
    //     <environment id="development">
    //         <!-- 决定事务作用域和控制方式的事务管理器 -->
    //         <transactionManager type="JDBC"/>
    //         <!-- 获取数据库连接实例的数据源DataSource: 基本名称变量不可修改 -->
    //         <dataSource type="POOLED">
    //             <property name="driver" value="${driverClassName}"/>
    //             <property name="url" value="${url}"/>
    //             <property name="username" value="${username}"/>
    //             <property name="password" value="${password}"/>
    //         </dataSource>
    //     </environment>
    // </environments>
    //
    // private void environmentsElement(XNode context) throws Exception {
    //     if (context != null) {
    //         if (this.environment == null) {
    //             this.environment = context.getStringAttribute("default");
    //         }
    //         Iterator var2 = context.getChildren().iterator();
    //         while(var2.hasNext()) {
    //             XNode child = (XNode)var2.next();
    //             String id = child.getStringAttribute("id");
    //             对于每一个带有id的environment配置，进行解析
    //             if (this.isSpecifiedEnvironment(id)) {
    //                 TransactionFactory txFactory = this.transactionManagerElement(child.evalNode("transactionManager"));
    //                 DataSourceFactory dsFactory = this.dataSourceElement(child.evalNode("dataSource"));
    //                 DataSource dataSource = dsFactory.getDataSource();
    //                 Builder environmentBuilder = (new Builder(id)).transactionFactory(txFactory).dataSource(dataSource);
    //                 this.configuration.setEnvironment(environmentBuilder.build());
    //             }
    //         }
    //     }
    // }
    //
    //  通过返回的DataSourceFactory，生成dsFactory.getDataSource();数据资源
    //  private DataSourceFactory dataSourceElement(XNode context) throws Exception {
    //      if (context != null) {
    //          String type = context.getStringAttribute("type");
    //          Properties props = context.getChildrenAsProperties();
    //          DataSourceFactory factory = (DataSourceFactory)this.resolveClass(type).getDeclaredConstructor().newInstance();
    //          factory.setProperties(props);
    //          return factory;
    //      } else {
    //          throw new BuilderException("Environment declaration requires a DataSourceFactory.");
    //      }
    //  }
    //
    //  通过配置的参数，返回DataSourceFactory
    //  public Properties getChildrenAsProperties() {
    //      Properties properties = new Properties();
    //      Iterator var2 = this.getChildren().iterator();
    //      while(var2.hasNext()) {
    //          XNode child = (XNode)var2.next();
    //          String name = child.getStringAttribute("name");
    //          String value = child.getStringAttribute("value");
    //          if (name != null && value != null) {
    //              properties.setProperty(name, value);
    //          }
    //      }
    //      return properties;
    //  }

    // Mapper解析和加载对应的映射语句
    //
    // private void mapperElement(XNode parent) throws Exception {
    //   // "Package加载的方式"优先级高的原因：能够加载一个包下的多个文件，加载的映射语句更多
    //   if ("package".equals(child.getName())) {
    //        resource = child.getStringAttribute("name");
    //        this.configuration.addMappers(resource);
    //
    //   if (resource != null && url == null && mapperClass == null) {
    //      ErrorContext.instance().resource(resource);
    //      inputStream = Resources.getResourceAsStream(resource);
    //      mapperParser = new XMLMapperBuilder(inputStream, this.configuration, resource, this.configuration.getSqlFragments());
    //      mapperParser.parse();
    //
    //


}

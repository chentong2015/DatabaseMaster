package mybatis_code_sources;

// 源码主要通过XMLConfigBuilder获取和解析MyBatis-config.xml配置文件
public class XmlDataSourceLoader {

    // 使用SqlSessionFactoryBuilder构建SqlSessionFactory时
    // 创建 XMLConfigBuilder parser = new XMLConfigBuilder(inputStream, environment, properties);

    // 对于xml配置<configuration>中的所有全局参数，源码中都有对应的解析方法
    // private void parseConfiguration(XNode root) {
    //     try {
    //         this.propertiesElement(root.evalNode("properties"));
    //         Properties settings = this.settingsAsProperties(root.evalNode("settings"));
    //         this.loadCustomVfs(settings);
    //         this.loadCustomLogImpl(settings);
    //         this.typeAliasesElement(root.evalNode("typeAliases"));
    //         this.pluginElement(root.evalNode("plugins"));
    //         this.objectFactoryElement(root.evalNode("objectFactory"));
    //         this.objectWrapperFactoryElement(root.evalNode("objectWrapperFactory"));
    //         this.reflectorFactoryElement(root.evalNode("reflectorFactory"));
    //         this.settingsElement(settings);
    //         this.environmentsElement(root.evalNode("environments"));
    //         this.databaseIdProviderElement(root.evalNode("databaseIdProvider"));
    //         this.typeHandlerElement(root.evalNode("typeHandlers"));
    //         this.mapperElement(root.evalNode("mappers"));
    //     } catch (Exception var3) {
    //         throw new BuilderException("Error parsing SQL Mapper Configuration. Cause: " + var3, var3);
    //     }
    // }
    //
    // 以下师对于环境参数的解析
    // <environments default="development">
    //     <environment id="development">
    //         <transactionManager type="JDBC"/>
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
    //             // 对于每一个带有id的environment配置，进行解析
    //             if (this.isSpecifiedEnvironment(id)) {
    //                 TransactionFactory txFactory = this.transactionManagerElement(child.evalNode("transactionManager"));
    //                 DataSourceFactory dsFactory = this.dataSourceElement(child.evalNode("dataSource"));
    //                 // 通过返回的DataSourceFactory，生成dsFactory.getDataSource();
    //                 DataSource dataSource = dsFactory.getDataSource();
    //                 Builder environmentBuilder = (new Builder(id)).transactionFactory(txFactory).dataSource(dataSource);
    //                 // 最终设置数据库源到Configuration
    //                 this.configuration.setEnvironment(environmentBuilder.build());
    //             }
    //         }
    //     }
    // }
    //
    //  // 根据"dataSource"生成DataSourceFactory
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
    //  // Class XNode该类型用于获取一个property配置的"name"和"value"
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
}

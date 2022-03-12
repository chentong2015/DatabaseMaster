package mybatis.basic.config.xml_settings;

public class MyBatisSettings {

    // 从XML配置文件中获取所有的参数设置XMLConfigBuilder.parseConfiguration()
    // private void parseConfiguration(XNode root) {
    //     try {
    //         // issue #117 read properties first
    //         propertiesElement(root.evalNode("properties"));
    //         Properties xml_settings = settingsAsProperties(root.evalNode("xml_settings"));
    //         loadCustomVfs(xml_settings);
    //         loadCustomLogImpl(xml_settings);
    //         typeAliasesElement(root.evalNode("typeAliases"));
    //         pluginElement(root.evalNode("plugins"));
    //         objectFactoryElement(root.evalNode("objectFactory"));
    //         objectWrapperFactoryElement(root.evalNode("objectWrapperFactory"));
    //         reflectorFactoryElement(root.evalNode("reflectorFactory"));
    //         settingsElement(xml_settings);
    //         // read it after objectFactory and objectWrapperFactory issue #631
    //         environmentsElement(root.evalNode("environments"));
    //         databaseIdProviderElement(root.evalNode("databaseIdProvider"));
    //         typeHandlerElement(root.evalNode("typeHandlers"));
    //         mapperElement(root.evalNode("mappers"));
    //     } catch (Exception e) {
    //         throw new BuilderException("Error parsing SQL Mapper Configuration. Cause: " + e, e);
    //     }
    // }

    // 解析settings配置属性: 提取XNode的内设置的属性.getChildrenAsProperties()
    // private Properties settingsAsProperties(XNode context) {
    //    if (context == null) {
    //      return new Properties();
    //    }
    //    Properties props = context.getChildrenAsProperties();
    //    // Check that all xml_settings are known to the configuration class
    //    MetaClass metaConfig = MetaClass.forClass(Configuration.class, localReflectorFactory);
    //    for (Object key : props.keySet()) {
    //      if (!metaConfig.hasSetter(String.valueOf(key))) {
    //        throw new BuilderException("The setting " + key + " is not known.
    //        Make sure you spelled it correctly (case sensitive).");
    //      }
    //    }
    //    return props;
    //  }

    // Properties的标准格式：name=value
    //  public Properties getChildrenAsProperties() {
    //    Properties properties = new Properties();
    //    for (XNode child : getChildren()) {
    //      String name = child.getStringAttribute("name");
    //      String value = child.getStringAttribute("value");
    //      if (name != null && value != null) {
    //        properties.setProperty(name, value);
    //      }
    //    }
    //    return properties;
    //  }
}

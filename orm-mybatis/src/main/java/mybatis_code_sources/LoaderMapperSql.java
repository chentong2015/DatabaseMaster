package mybatis_code_sources;

// 源码主要通过XMLMapperBuilder获取和解析xxxMapper.xml映射语句
public class LoaderMapperSql {

    // <mapper namespace="mybatis_basics.testConfigXml.BlogMapper">
    //    <select id="selectBlog" resultType="mybatis_basics.DataModel.Blog">
    //        select * from blog where id = #{id}
    //    </select>
    // </mapper>

    // XMLMapperBuilder下主要的加载方法
    // XMLConfigBuilder.parseConfiguration(XNode root)
    //   > XMLConfigBuilder.mapperElement(XNode parent)
    //
    // private void mapperElement(XNode parent) throws Exception {
    //   "Package加载的方式"优先级高的原因：能够加载一个包下的多个文件，加载的映射语句更多，方便维护
    //   if ("package".equals(child.getName())) {
    //        resource = child.getStringAttribute("name");
    //        this.configuration.addMappers(resource);
    //   ...
    //   if (resource != null && url == null && mapperClass == null) {
    //      ErrorContext.instance().resource(resource);
    //      inputStream = Resources.getResourceAsStream(resource);
    //      // 使用特定的类型来加载xxxMapper.xml
    //      mapperParser = new XMLMapperBuilder(inputStream, this.configuration, resource, this.configuration.getSqlFragments());
    //      mapperParser.parse();
    //      ...
    //   } else if (resource == null && url == null && mapperClass != null) {
    //      // 以下是使用<mapper class="xxx" />的方式添加映射语句
    //      Class<?> mapperInterface = Resources.classForName(mapperClass);
    //      configuration.addMapper(mapperInterface);
    //

    // XMLMapperBuilder.configurationElement(XNode context)
    // private void configurationElement(XNode context) {
    //     try {
    //         String namespace = context.getStringAttribute("namespace");
    //         所声明的mapper必须定义namespace
    //         if (namespace != null && !namespace.isEmpty()) {
    //             this.builderAssistant.setCurrentNamespace(namespace);
    //             this.cacheRefElement(context.evalNode("cache-ref"));
    //             this.cacheElement(context.evalNode("cache"));
    //             this.parameterMapElement(context.evalNodes("/mapper/parameterMap"));
    //             this.resultMapElements(context.evalNodes("/mapper/resultMap"));
    //             this.sqlElement(context.evalNodes("/mapper/sql"));
    //             this.buildStatementFromContext(context.evalNodes("select|insert|update|delete"));
    //         } else {
    //             throw new BuilderException("Mapper's namespace cannot be empty");
    //         }
    //     } catch (Exception var3) {
    //         throw new BuilderException("Error parsing Mapper XML. The XML location is '" + this.resource + "'. Cause: " + var3, var3);
    //     }
    // }

    // private void buildStatementFromContext(List<XNode> list, String requiredDatabaseId) {
    //      Iterator var3 = list.iterator();
    //      while(var3.hasNext()) {
    //          XNode context = (XNode)var3.next();
    //          XMLStatementBuilder statementParser = new XMLStatementBuilder(this.configuration, this.builderAssistant, context, requiredDatabaseId);
    //          try {
    //              statementParser.parseStatementNode();
    //          } catch (IncompleteElementException var7) {
    //              this.configuration.addIncompleteStatement(statementParser);
    //          }
    //      }
    //  }
    //
    // 实际操作SQL语句的方法class XMLStatementBuilder中的方法, 对xml中配置的key的值进行解析
    // public void parseStatementNode() {
    //     String id = this.context.getStringAttribute("id");
    //     String databaseId = this.context.getStringAttribute("databaseId");
    //     if (this.databaseIdMatchesCurrent(id, databaseId, this.requiredDatabaseId)) {
    //         String nodeName = this.context.getNode().getNodeName();
    //         SqlCommandType sqlCommandType = SqlCommandType.valueOf(nodeName.toUpperCase(Locale.ENGLISH));
    //         boolean isSelect = sqlCommandType == SqlCommandType.SELECT;
    //         ...
}

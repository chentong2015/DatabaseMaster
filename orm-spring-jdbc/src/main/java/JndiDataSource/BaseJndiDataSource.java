package JndiDataSource;

/**
 * TODO JNDI深入研究: https://docs.oracle.com/javase/tutorial/jndi/index.html
 * JNDI: Java Name and Directory Interface
 * 1. Name/Directory Service maps the names of network (address) resources
 * 2. Each resource on network is an object by the directory server   !!
 * 3. JNDI是一个访问名称或者目录服务的APIs, 它使用名称去定位和查找对应objects !!
 */

/**
 * Naming service
 * 1. DNS(Domain name system): 域名解析系统, Map machine names to IP Address
 * .
 * Directory services
 * 1. LDAP: Lightweight Directory Access Protocol 轻量级目录访问协议
 * 2. Corba: Common Object Request Broker Architecture 通用对象请求代理架构  ==> 分布式对象系统
 * 3. RMI: Java Remote Method Invocation 远程方法调用
 * 4. EJB: Enterprise JavaBeans, Java platform's component technology 组件技术，方便重用软件的组件
 */
// TODO: MyBatis中可以配置使用JNDI DataSource数据库服务
public class BaseJndiDataSource {

    // Running an config on a Java EE application server, looks up and locates a database
    // using JNDI name of the datasource without having to know the details about teh connection
    // 能够将APP移植和部署到使用不同data source的server服务器
    // Java Code只需要使用dataSourceName就能连接, 而不必关注具体的连接细节

    // https://tomcat.apache.org/tomcat-8.0-doc/jndi-datasource-examples-howto.html
    /**
     * Tomcat JNDI 配置：/conf/context.xml 配置Server连接和使用的数据资源
     *  <Resource name="jdbc/nameDataSource"
     *  auth="Container"
     *  type="javax.sql.DataSource"
     *  maxTotal="100"  ==> The maximum number of active instances that can be allocated from the pool at the same time
     *  maxIdle="30"    ==> The maximum number of connection that can sit idle in this pool 池中可以闲置的最大连接数
     *  maxWaitMillis="10000"  ==> The time that pool will wait when there is not available connections
     *  username="javauser"
     *  password="javadude"
     *  driverClassName="com.mysql.jdbc.Driver"
     *  url="jdbc:mysql://localhost:3306/javatest"/>
     */

    /**
     * JSTL SQL: 可在前端VIEW直接使用SQL查询语句
     * <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
     * <%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
     * <sql:query var="resultSet" dataSource="jdbc/nameDataSource">
     * .   SELECT * FROM information
     * </sql:query>
     * <c:forEach var="row" items="{resultSet}">
     * .   Name: ${row.name} </br>  这里遍历使用的是数据库中列的名称
     * </c:forEach>
     */
}

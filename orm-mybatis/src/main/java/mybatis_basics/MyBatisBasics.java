package mybatis_basics;

// SSM(SpringMVC+Spring+Mybatis)设计思路
// SSH(Struts2+Spring+Hibernate)由于Struts2高危漏洞, 被SSM替代

// iBatis: Google 2001年发起的开放源代码项目, 一个基于Java的持久层框架, 2010 Apache软件基金会退役
// MyBatis: 持久层框架，它支持自定义SQL、存储过程以及高级映射
// 1. 提供SQL(sql语句)和DAO(Data Access Objects实体类)之间的映射Map, 省掉了相同功能的JDBC很多代码
// 2. ORM的补充: "半自动化"的ORM实现, 需要写SQL, 增加了程序的灵活性

// TODO: 两种实现方式"基于XML映射(优先级更高，代码会先判断inputStream)"或者"Java注解" !!
// 1. SqlSessionFactoryBuilder: 通过xml配置或者java程序两种方式.build()出SqlSessionFactory，一次实例化之后直接关闭
// 2. SqlSessionFactory: 应用内保持唯一(单例模式或者静态单例模式)，负责SqlSession的创建
// 3. SqlSession: 每个线程都有独立的SqlSession实例，负责映射语句的调用和执行
//    3.1 一般使用try-resource声明, 保证所有数据库资源都能被正确地关闭
//    3.2 不能将SqlSession实例的引用放在一个类的静态域，或者类型的托管作用域中
//    3.3 使用Web框架, 应该将SqlSession放在和HTTP请求相似的作用域中, 收到请求时打开, 返回响应后关闭
//    TODO: DefaultSqlSession不是线程安全的, 需要使用在方法作用域层面method scope
// 4. Mapper:
//    映射器接口实例是从SqlSession中获得的, 最好放在方法作用域内, 映射器实例并不需要被显式地关闭
//    MyBatis创建结果对象的新实例时, 它都会使用对象工厂(ObjectFactory)实例来完成实例化工作
public class MyBatisBasics {

    // TODO: ORM(Object Relational Mapping)层的精髓: Map映射 / Row Mapper思想
    // 对于面向对象(类型安全)编程语言，实现不同"类型系统"数据之间的转换
    //  Java类型               数据库表字段      数据库类型
    //  java.lang.Integer     id              Integer
    //  java.lang.String      name            VARCHAR
    //  java.lang.String      context         LONGVARCHAR

    // TODO：A系统传递给B系统一条SQL, B系统如何检测判断这条SQL正确性 ?
    //      本质：SQL改写，语法引擎(SQL必须符合引擎所规定的语法)
    // select * from t_blog where id=#{id};  JDBC无法执行
    // select * from t_blog where id=?;      MyBatis需要对其进行改写

    // ${}和#{}的区别
    // 1. ${}是字符串替换，直接把${}替换成变量的值，调用.createStatement()
    // 2. #{}是预编译，会先替换成?，调用.prepareStatement(query)来赋值
    // 3. #{}可以防止SQLz注入

    // TODO: 访问数据库必须使用连接池, 提供可靠性保护：网络中断自动重连, 连接数限制，定期回收连接，设置超时时间等
    // 1. 必须具有自动重连的功能
    // 2. 必须设置何理的超时时间, 防止程序访问数据库长时间的挂死
    // 3. 数据库连接池必须设置最大值, 默认是500个连接
    // 4. Java语言推荐使用mybatis持久层框架来访问数据库
}

package mybatis_basics;

import mybatis_basics.datasource.MyDataSourceFactory;
import mybatis_basics.datasource.PropertiesLoader;
import mybatis_basics.mapper.BlogMapper;
import mybatis_basics.model.Blog;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;

/**
 * iBatis: 2001年发起的开放源代码项目, 一个基于Java的持久层框架, 2010Apache软件基金会退役, 形成现在的MyBatis
 * 1. 主要提供SQL和DAO(Data Access Objects)之间的Map映射
 * 2. "半自动化"的ORM实现, 需要写SQL, 增加了程序的灵活性(作为ORM的一种补充)
 * 3. iBatis是把"实体类"和"sql语句"之间建立映射关系
 * .
 * MyBatis: 持久层框架，它支持自定义SQL、存储过程以及高级映射
 * 1. 取消JDBC代码以及设置参数和获取结果集的代码
 * 2. MyBatis特性的两种实现方式"基于XML映射"或者"Java注解"
 * 3. MyBatis强大在于它的语句映射, 省掉了相同功能的JDBC的很多代码
 */
// 官网: https://mybatis.org/mybatis-3/zh/getting-started.html
// APIs: https://mybatis.org/mybatis-3/es/apidocs/index.html?org/apache/ibatis
// SSM(SpringMVC+Spring+Mybatis)设计思路: https://paper.seebug.org/1075/
// SSH(Struts2+Spring+Hibernate)由于Struts2爆出众多高危漏洞, 被SSM替代
public class MyBatisBasics {

    public static void main(String[] args) throws IOException {
        testAnnotationBasedSqlSession();
    }

    // 全部使用XML配置，在配置信息更改后无需再编译源代码
    private static void testXmlBasedSqlSession() throws IOException {
        // 默认是资源的路径/resources
        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        // SqlSession提供了在数据库执行SQL命令所需的所有方法, 直接执行已映射的SQL语句 !!
        try (SqlSession session = sqlSessionFactory.openSession()) {
            // 使用完整的名称空间来调用指定的方法
            Blog blog = session.selectOne("com.ctong.main.BlogMapper.selectBlog", 1);
            System.out.println(blog.getName());
        }
    }

    // 全部使用Java源码和注解的方式，提高编译时的类型安全性
    private static void testAnnotationBasedSqlSession() throws IOException {
        MyDataSourceFactory dataSourceFactory = new MyDataSourceFactory();
        dataSourceFactory.setProperties(PropertiesLoader.getPropertiesFromResourcePath());
        DataSource dataSource = dataSourceFactory.getDataSource();
        TransactionFactory transactionFactory = new JdbcTransactionFactory();

        Environment environment = new Environment("development", transactionFactory, dataSource);
        Configuration configuration = new Configuration(environment);
        configuration.addMapper(BlogMapper.class);

        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
        try (SqlSession session = sqlSessionFactory.openSession()) {
            BlogMapper mapper = session.getMapper(BlogMapper.class);
            Blog blog = mapper.selectBlog(2);
            System.out.println(blog.getName());
        }
    }
}

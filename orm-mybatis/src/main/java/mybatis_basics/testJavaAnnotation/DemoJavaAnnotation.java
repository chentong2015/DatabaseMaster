package mybatis_basics.testJavaAnnotation;

import mybatis_basics.DataModel.Blog;
import mybatis_basics.DataModel.User;
import mybatis_basics.testJavaAnnotation.datasource.MyDataSourceFactory;
import mybatis_basics.testJavaAnnotation.datasource.PropertiesLoader;
import mybatis_basics.testJavaAnnotation.mapper.BlogMapper;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import javax.sql.DataSource;
import java.io.IOException;

// 全部使用Java源码和注解的方式，提高编译时的类型安全性，但是不利于SQL的维护，重新编译
public class DemoJavaAnnotation {

    public static void main(String[] args) throws IOException {
        MyDataSourceFactory dataSourceFactory = new MyDataSourceFactory();
        dataSourceFactory.setProperties(PropertiesLoader.getPropertiesFromResource());
        DataSource dataSource = dataSourceFactory.getDataSource();

        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("development", transactionFactory, dataSource);
        Configuration configuration = new Configuration(environment);
        configuration.addMapper(BlogMapper.class); // 最终注入到MapperRegister

        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
        try (SqlSession session = sqlSessionFactory.openSession()) {
            BlogMapper mapper = session.getMapper(BlogMapper.class);
            Blog blog = mapper.selectBlog(10);
            // 使用对象工厂(ObjectFactory)实例来完成结果对象的实例化工作
            User user = mapper.findUserByColumn("name", "test");
            System.out.println(user.getAge());
        }
    }
}

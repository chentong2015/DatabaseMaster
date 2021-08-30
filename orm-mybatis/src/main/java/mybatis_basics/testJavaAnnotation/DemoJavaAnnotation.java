package mybatis_basics.testJavaAnnotation;

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
            // 使用对象工厂(ObjectFactory)实例来完成结果对象的实例化工作
            User user = mapper.findUserByColumn("name", "test");
            System.out.println(user.getAge());
        }
    }

    // TODO: 如何构建BlogMapper接口类型的实例化对象 ?
    //       BlogMapper mapper = session.getMapper(BlogMapper.class); 新版本的使用方式
    //       最终在执行的时候，还是调用到session.selectOne()原始方法
    // MapperRegistry.getMapper()
    // public <T> T getMapper(Class<T> type, SqlSession sqlSession) {
    //   final MapperProxyFactory<T> mapperProxyFactory = (MapperProxyFactory<T>) knownMappers.get(type);
    //   if (mapperProxyFactory == null) {
    //     throw new BindingException("Type " + type + " is not known to the MapperRegistry.");
    //   }
    //   try {
    //     return mapperProxyFactory.newInstance(sqlSession);
    //   } catch (Exception e) {
    //     throw new BindingException("Error getting mapper instance. Cause: " + e, e);
    //   }
    // }

    // MapperProxyFactory.newInstance()
    // public T newInstance(SqlSession sqlSession) {
    //    final MapperProxy<T> mapperProxy = new MapperProxy<>(sqlSession, mapperInterface, methodCache);
    //    return newInstance(mapperProxy);
    //  }

    // protected T newInstance(MapperProxy<T> mapperProxy) {
    //    // 调用Java原生提供的java.lang.reflect.Proxy代理
    //    return (T) Proxy.newProxyInstance(mapperInterface.getClassLoader(), new Class[] { mapperInterface }, mapperProxy);
    //  }

    // TODO: 由于MapperProxy<T>实现了动态代理，因此会调用实现InvocationHandler接口的方法
    // 调用MapperProxy.invoke()
    //  @Override
    //  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    //    try {
    //      if (Object.class.equals(method.getDeclaringClass())) {
    //        return method.invoke(this, args);
    //      } else {
    //        return cachedInvoker(method).invoke(proxy, method, args, sqlSession);
    //      }
    //    } catch (Throwable t) {
    //      throw ExceptionUtil.unwrapThrowable(t);
    //    }
    //  }
}

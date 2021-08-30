package mybatis_basics.Cache;

import mybatis_basics.DataModel.Blog;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

// 缓存特点
// 1. 优点：本地缓存不需要走网络IO和磁盘IO，提升查询效率
// 2. 缺点: 数据库更换造成缓存数据一致性问题 ==> "缓存双写一致性问题"
// MyBatis分一级缓存和二级缓存
// 1. TODO: 默认情况下，只启用了本地的会话缓存，它仅仅对一个会话中的数据进行缓存
// 2. 二级缓存是事务性的，当SqlSession完成并提交，或完成并回滚但没有执行flushCache=true的insert/delete/update语句时，缓存会获得更新
public class MyBatisCache {

    // 启用全局的二级缓存，只需要在SQL映射文件中添加一行<cache/>
    // <cache
    //  eviction="FIFO"
    //  flushInterval="60000"
    //  size="512"
    //  readOnly="true"/>

    public static void main(String[] args) throws IOException {
        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        String statement = "mybatis_basics.testConfigXml.BlogMapper.selectBlog";
        try (SqlSession session = sqlSessionFactory.openSession()) {
            Blog blog1 = session.selectOne(statement, 1);
            Blog blog2 = session.selectOne(statement, 1); // 第二次查询时，直接从缓存中取数据
            System.out.println(blog1.getName());
            System.out.println(blog2.getName());
        }

        try (SqlSession session = sqlSessionFactory.openSession()) {
            Blog blog1 = session.selectOne(statement, 1);
            Blog blog2 = session.selectOne(statement, 2); // 第二次查询时，如果缓存中找不到，则查询数据库
            System.out.println(blog1.getName());
            System.out.println(blog2.getName());
        }

        SqlSession session = sqlSessionFactory.openSession();
        Blog blog1 = session.selectOne(statement, 1);
        session.commit();                                      // 如果Session会话已经Commit，则下次查询时将重新走数据库
        Blog blog2 = session.selectOne(statement, 1);
        System.out.println(blog1.getName());
        System.out.println(blog2.getName());
        session.close();
    }
}

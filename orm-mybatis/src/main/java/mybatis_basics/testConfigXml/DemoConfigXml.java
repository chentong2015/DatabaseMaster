package mybatis_basics.testConfigXml;

import mybatis_basics.DataModel.Blog;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

// 全部使用XML配置，在配置信息更改后无需再编译源代码
public class DemoConfigXml {

    public static void main(String[] args) throws IOException {
        // 默认是资源的路径/resources
        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        // SqlSession提供了在数据库执行SQL命令所需的所有方法, 直接执行已映射的SQL语句
        try (SqlSession session = sqlSessionFactory.openSession()) {
            // 使用完整的名称空间来调用指定的方法
            Blog blog = session.selectOne("mybatis_basics.testConfigXml.BlogMapper.selectBlog", 1);
            System.out.println(blog.getName());
        }
    }
}

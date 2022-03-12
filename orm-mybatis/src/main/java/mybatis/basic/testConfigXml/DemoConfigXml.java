package mybatis.basic.testConfigXml;

import mybatis.basic.model.Blog;
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

        try (SqlSession session = sqlSessionFactory.openSession()) {
            // BlogMapper mapper = session.getMapper(BlogMapper.class); 新版使用方式
            // 使用完整的名称空间来调用指定的方法(老版本的使用方式)
            Blog blog = session.selectOne("mybatis_basics.testConfigXml.BlogMapper.selectBlog", 1);
            System.out.println(blog.getName());
        }
    }
}

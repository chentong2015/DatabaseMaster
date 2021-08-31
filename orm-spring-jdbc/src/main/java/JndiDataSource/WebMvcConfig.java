package JndiDataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;

@Configuration
@EnableWebMvc
@ComponentScan("JndiDataSource")
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * 使用JNDI重新定义使用Data Source数据资源
     * 1. .setResourceRef(true) 设置"相对路径"引用, 反之需要使用"java:comp/env/jdbc/dataSourceName"
     * 2. 从Web Server(Tomcat) context.xml配置文件中dataSourceName名称来获取数据资源的连接 !!
     */
    @Bean
    public DataSource dataSource() {
        final JndiDataSourceLookup dataSourceLookup = new JndiDataSourceLookup();
        dataSourceLookup.setResourceRef(true);
        DataSource dataSource = dataSourceLookup.getDataSource("jdbc/myDataSource");
        return dataSource;
    }
}

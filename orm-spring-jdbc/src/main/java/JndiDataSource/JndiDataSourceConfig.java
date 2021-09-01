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
public class JndiDataSourceConfig implements WebMvcConfigurer {

    // TODO: Spring可以通过JndiTemplate获取DataSource数据库源

    // 自定义配置DataSource的来源
    // 1. .setResourceRef(true) 设置"相对路径"引用, 反之使用"java:comp/env/jdbc/dataSourceName"
    // 2. .getDataSource(name)  直接使用Tomcat context.xml配置文件中dataSourceName名称来获取数据资源
    @Bean
    public DataSource dataSource() {
        final JndiDataSourceLookup dataSourceLookup = new JndiDataSourceLookup();
        dataSourceLookup.setResourceRef(true);
        DataSource dataSource = dataSourceLookup.getDataSource("jdbc/myDataSource");
        return dataSource;
    }

    // JSTL SQL: 前端VIEW直接使用SQL查询语句
    // <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    // <%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
    // <sql:query var="resultSet" dataSource="jdbc/nameDataSource">
    //     SELECT * FROM information
    // </sql:query>
    // <c:forEach var="row" items="{resultSet}">
    //     Name: ${row.name} </br>  这里遍历使用的是数据库中列的名称
    // </c:forEach>
}

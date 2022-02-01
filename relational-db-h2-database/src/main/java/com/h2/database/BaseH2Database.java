package com.h2.database;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

// http://www.h2database.com/html/cheatSheet.html
// http://www.h2database.com/html/quickstart.html
// https://www.tutorialspoint.com/h2_database/h2_database_quick_guide.htm 完整的测试教程

// H2 is a JAVA database, opensource and lightweight DB that can be used in an in-memory manner.
// Embedded database it is not used for production development, mostly for development and testing.
public class BaseH2Database {

    // H2 Local Server:
    // URL: jdbc:h2:tcp://localhost/~/test 默认的连接
    // login: sa, sa

    // H2 Database Engine: 创建新的数据库
    // JDBC URL for H2 Console: jdbc:h2:./murex_retail_experience 创建的连接
    // Login: admin, admin

    public static void main(String[] a) throws Exception {
        Class.forName("org.h2.Driver");
        // jdbc:h2:tcp://localhost/~/test
        System.out.println("Application ");
        Connection conn = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/test", "sa", "sa");
        // add application code here
        String sql = "Insert into t_test (id, name) values (7, 'item07');";
        conn.createStatement().execute(sql);
        conn.close();
    }

    // TODO: 使用加载类型的ClassLoader来从资源中加载Stream
    private void loadDbConfigFromProperties() throws IOException {
        ClassLoader classLoader = BaseH2Database.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("db.properties");
        Properties properties = new Properties();
        properties.load(inputStream);
        String driverClass = properties.getProperty("jdbc.driverClass");
        String jdbcUrl = properties.getProperty("jdbc.url");
        String username = properties.getProperty("jdbc.user");
        String password = properties.getProperty("jdbc.password");
    }
}

package com.h2.database;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

// http://www.h2database.com/html/cheatSheet.html
// http://www.h2database.com/html/quickstart.html
// https://www.tutorialspoint.com/h2_database/h2_database_quick_guide.htm 完整的测试教程
// http://www.vue5.com/h2_database/h2_database_quick_guide.html H2数据库教程

// H2 is a JAVA database, opensource and lightweight DB that can be used in an in-memory manner.
// Embedded database it is not used for production development, mostly for development and testing.
public class BaseH2Database {

    // H2 Local Server:
    // URL: jdbc:h2:tcp://localhost/~/test 默认的连接
    // login: sa, sa

    // H2 Database Engine: 创建新的数据库
    // JDBC URL for H2 Console: jdbc:h2:./murex_retail_experience
    //   注意创建的相对位置 创建的连接 relational-db-h2-database/database/murex_retail_experience.mv.db
    // login: admin
    // password: admin

    public static void main(String[] a) throws Exception {
        Class.forName("org.h2.Driver");
        // 下面两个指的是同一个数据库
        // "jdbc:h2:~/test"  TODO: 只有一个进程能够连接
        // jdbc:h2:tcp://localhost/~/test TODO: Application和H2 Console可以同时连接
        Connection conn = DriverManager.getConnection("jdbc:h2:~/test", "sa", "sa");
        String sql = "Insert into t_test (id, name) values (9, 'item09');";
        // String sql = "Insert into t_cpu values (default, '70d0c37e-634e-4ssa68-8862-0ba44f216f3b', 'CPU', 'Intel Core i7-8809G', 'Intel', 150.0 , 25, 'Core i7', 4, '3.10 GHz', '1.20 GHz');";
        conn.createStatement().execute(sql);
        conn.close();
    }

    private static void testBasicConnection() throws ClassNotFoundException, SQLException {
        // Class.forName("org.h2.Driver");
        // System.out.println("Application ");
        // String url = "jdbc:h2:./murex_retail_experience;DB_CLOSE_DELAY=-1;DATABASE_TO_UPPER=true;";
        // Connection conn = DriverManager.getConnection(url, "admin", "admin");
        // System.out.println(conn);
        // // String sql2 = "CREATE TABLE t_test2 ( \n" +
        // //         "   id INT NOT NULL, \n" +
        // //         "   title VARCHAR(50) NOT NULL, \n" +
        // //         "   author VARCHAR(20) NOT NULL, \n);";
        // // String sql3 = "Insert into t_test values (1, '44f216f3b', 'chen', , 'Intel', 150.0 , 25, 'Core i7', 4, '3.10 GHz', '1.20 GHz');";
        // String sql = "Insert into t_cpu values (default, '70d0c37e-634e-4a68-8862-0ba44f216f3b', 'CPU', 'Intel Core i7-8809G', 'Intel', 150.0 , 25, 'Core i7', 4, '3.10 GHz', '1.20 GHz');";
        // conn.createStatement().executeUpdate(sql);
        // conn.close();
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

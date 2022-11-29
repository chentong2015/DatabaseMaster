package database.design.connection;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionDB {

    // 通过Driver获取数据库的连接
    public void testConnectionDB() throws SQLException {
        String url = "jdbc:subprotocol:subname";
        Driver driver = DriverManager.getDriver(url);
        Connection connection = driver.connect(url, new Properties());
    }
}

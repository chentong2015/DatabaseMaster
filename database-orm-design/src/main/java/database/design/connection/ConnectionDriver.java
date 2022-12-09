package database.design.connection;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionDriver {

    // Driver: 不同的具体数据库对Driver接口有不同的实现class
    public void testDriver() throws SQLException {
        String url = "jdbc:subprotocol:subname";
        Driver driver = DriverManager.getDriver(url);
        Connection connection = driver.connect(url, new Properties());
    }

    // TODO. Driver和URL必须匹配数据库的类型/版本，才能建立DB连接
    public void openConnection(String url, Driver driverObject, Properties driverProperties) {
        String driverClassName = driverObject.getClass().getName();
        try {
            Connection connection = driverObject.connect(url, driverProperties);
            if (connection == null) {
                throw new RuntimeException("Can not connect to db of url");
            }
        } catch (SQLException e) {
            if (driverClassName.equals("org.h2.driver")) {
                // make sure H2 database is active and accessible by opening a new terminal window
            }
            // Connection can't be created to url with driver + driverClassName
        }
    }
}

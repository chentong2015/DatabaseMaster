import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.util.Properties;

// JDBC Driver: 底层具体的数据库对Driver有不同的实现
// Driver Manager: 使用DriverManager从指定的url中获取Driver
//  Driver driver = DriverManager.getDriver(url);
public class ConnectionDriver {

    public static void main(String[] args) throws Exception {
        testGetConnectionByDriver();
    }

    // 使用Driver直接连接到指定的url(包含DB name)
    // properties配置参数列表中需要包含user, password
    public static void testGetConnectionByDriver() throws Exception {
        String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String url = "jdbc:sqlserver://localhost:1433;Database=test_db;";
        Properties properties = new Properties();
        properties.put("user", "test");
        properties.put("password", "TCHong20");
        // 等效于在Connection String后面添加配置trustServerCertificate=true
        properties.put("trustServerCertificate", "true");

        // 加载指定数据库的Driver实现
        Driver driver = (Driver) Class.forName(driverName).getDeclaredConstructor().newInstance();
        Connection connection = driver.connect(url, properties);
        connection.setAutoCommit(true);
        System.out.println("done");
        connection.close();
    }

    // TODO. Driver和URL必须匹配数据库的类型/版本，才能建立DB连接
    public void openConnection(Driver driverObject, String url, Properties driverProperties) {
        try {
            Connection connection = driverObject.connect(url, driverProperties);
            if (connection == null) {
                throw new RuntimeException("Can not connect to db of url");
            }
        } catch (SQLException exception) {
            // 拿到加载的Driver Class名称，根据名称区别数据库类型
            String driverClassName = driverObject.getClass().getName();
            if (driverClassName.equals("org.h2.driver")) {
                // make sure H2 database is active and accessible by opening a new terminal window
            }
            // Connection can't be created to url with driver + driverClassName
        }
    }
}

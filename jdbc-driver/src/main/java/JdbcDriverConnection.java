import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.util.Properties;

public class JdbcDriverConnection {

    // TODO. 直接通过DB Vendor实现的Driver类型来创建数据库连接
    // - 使用DriverImpl直接连接到指定的url(包含DB name)
    // - 配置properties参数列表(User, Password)
    public static void main(String[] args) throws Exception {
        String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String url = "jdbc:sqlserver://localhost:1433;Database=test_db;";
        Properties properties = new Properties();
        properties.put("user", "test");
        properties.put("password", "TCHong20");
        // 等效于在Connection String后面添加配置trustServerCertificate=true
        properties.put("trustServerCertificate", "true");

        Driver driver = (Driver) Class.forName(driverName).getDeclaredConstructor().newInstance();
        Connection connection = driver.connect(url, properties);
        connection.setAutoCommit(true);
        System.out.println("done");
        connection.close();
    }

    // TODO. Driver和URL必须匹配数据库的类型/版本才能建立DB连接
    public void openConnection(Driver driverObject, String url, Properties driverProperties) {
        try {
            Connection connection = driverObject.connect(url, driverProperties);
            if (connection == null) {
                throw new RuntimeException("Can not connect to db of url");
            }
        } catch (SQLException exception) {
            String driverClassName = driverObject.getClass().getName();
            if (driverClassName.equals("org.h2.driver")) {
                System.out.println("Make sure H2 database is active and accessible");
            }
            System.out.println("Connection can't be created to url");
        }
    }
}

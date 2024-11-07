package driver_manager;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.CopyOnWriteArrayList;

// TODO. DriverManager管理不同Driver，使用URL连接到正确的数据库
public class JavaDriverManager {

    private static CopyOnWriteArrayList<CustomDriverInfo> registeredDrivers;

    // TODO. Driver.getConnection()底层调用Driver实现类的connect()方法来创建连接
    // driver.connect(url, info) 接受连接的url和properties
    // driver.connect(url, info) 方法具体由DB Driver来实现
    private static Connection getConnection(String url, Properties info, Class<?> caller) throws SQLException {
        // ...
        // Walk through the loaded registeredDrivers attempting to make a connection.
        // Remember the first exception that gets raised so we can reraise it.

        registeredDrivers = JavaDriverContainer.getRegisteredDrivers();
        ClassLoader callerCL = caller != null ? caller.getClassLoader() : null;

        for (CustomDriverInfo driverInfo : registeredDrivers) {
            if (isDriverAllowed(driverInfo.getDriver(), callerCL)) {
                try {
                    Connection con = driverInfo.getDriver().connect(url, info);
                    if (con != null) {
                        return (con);
                    }
                } catch (SQLException ex) {
                    System.out.println("Failed to get connection");
                }
            } else {
                System.out.println("skipping: " + driverInfo.getClass().getName());
            }
        }
        throw new SQLException("No suitable driver found for " + url, "08001");
    }

    // Indicates whether the class object that would be created if the code calling
    // DriverManager is accessible.
    private static boolean isDriverAllowed(Driver driver, Class<?> caller) {
        ClassLoader callerCL = caller != null ? caller.getClassLoader() : null;
        return isDriverAllowed(driver, callerCL);
    }

    private static boolean isDriverAllowed(Driver driver, ClassLoader classLoader) {
        boolean result = false;
        if (driver != null) {
            Class<?> aClass = null;
            try {
                aClass = Class.forName(driver.getClass().getName(), true, classLoader);
            } catch (Exception ex) {
                result = false;
            }
            result = (aClass == driver.getClass()) ? true : false;
        }
        return result;
    }
}
package driver_manager;

import java.sql.Driver;
import java.sql.DriverAction;
import java.sql.SQLException;
import java.util.concurrent.CopyOnWriteArrayList;

// 所有Driver的容器: 提供Driver的注册和卸载
public class JavaDriverContainer {

    // 添加同步锁，在移除Driver的实现对象时保证安全
    private final static Object lockForInitDrivers = new Object();
    // 多线程并发安全的数据结构
    private final static CopyOnWriteArrayList<CustomDriverInfo> registeredDrivers = new CopyOnWriteArrayList<>();

    // TODO. register将Driver的实现对象DriverManager的缓存List中
    public static void registerDriver(java.sql.Driver driver, DriverAction da) {
        /* Register the driver if it has not already been added to our list */
        if (driver != null) {
            registeredDrivers.addIfAbsent(new CustomDriverInfo(driver, da));
        } else {
            // This is for compatibility with the original DriverManager
            throw new NullPointerException();
        }
    }

    public static void deregisterDriver(Driver driver) throws SQLException {
        if (driver == null) {
            return;
        }
        CustomDriverInfo aDriver = new CustomDriverInfo(driver, null);
        synchronized (lockForInitDrivers) {
            if (registeredDrivers.contains(aDriver)) {
                registeredDrivers.remove(aDriver);
            } else {
                System.out.println("Couldn't find driver to unload");
            }
        }
    }

    public static CopyOnWriteArrayList<CustomDriverInfo> getRegisteredDrivers() {
        return registeredDrivers;
    }
}

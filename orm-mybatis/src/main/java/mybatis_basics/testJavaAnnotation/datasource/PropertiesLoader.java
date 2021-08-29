package mybatis_basics.testJavaAnnotation.datasource;

import java.io.IOException;
import java.util.Properties;

public class PropertiesLoader {

    // 1. 从默认的资源路径/resources下面加载动态配置文件
    public static Properties getPropertiesFromResource() throws IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Properties properties = new Properties();
        properties.load(classLoader.getResourceAsStream("mybatis-config.properties"));
        return properties;
    }

    // 2. 将配置信息写进java源码
    public static Properties createProperties() {
        Properties prop = new Properties();
        prop.setProperty("driverClassName", "com.mysql.jdbc.Driver");
        prop.setProperty("url", "jdbc:mysql://localhost:3306/testdb");
        prop.setProperty("user", "testuser");
        prop.setProperty("password", "test623");
        return prop;
    }
}

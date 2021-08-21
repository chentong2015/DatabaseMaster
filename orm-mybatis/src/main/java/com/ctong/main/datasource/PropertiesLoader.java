package com.ctong.main.datasource;

import java.io.IOException;
import java.util.Properties;

public class PropertiesLoader {

    // 从默认的资源/resources路径下面加载配置文件, InputStream
    public static Properties getPropertiesFromResourcePath() throws IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Properties properties = new Properties();
        properties.load(classLoader.getResourceAsStream("mybatis-config.properties"));
        return properties;
    }

    // 将配置信息写进java源码中: 不推荐 !!
    public static Properties createProperties() {
        Properties prop = new Properties();
        prop.setProperty("driverClassName", "com.mysql.jdbc.Driver");
        prop.setProperty("url", "jdbc:mysql://localhost:3306/testdb");
        prop.setProperty("user", "testuser");
        prop.setProperty("password", "test623");
        return prop;
    }
}

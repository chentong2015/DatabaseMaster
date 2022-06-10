package com.liquibase.main.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConfigHelper {

    public static Properties getProperties(String path) throws IOException {
        Properties properties = new Properties();
        try {
            FileInputStream input = new FileInputStream(path);
            properties.load(input);
            // 可以验证一下properties文件配置的正确性
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("The path " + path + " is incorrect");
        }
        return properties;
    }

    public static DatabaseConfig getSourceConfig(Properties properties) {
        return new DatabaseConfig(properties.getProperty("SOURCE_URL"),
                properties.getProperty("SOURCE_USER"),
                properties.getProperty("SOURCE_PASSWORD"));
    }

    public static DatabaseConfig getDestConfig(Properties properties) {
        return new DatabaseConfig(properties.getProperty("DESTINATION_URL"),
                properties.getProperty("DESTINATION_USER"),
                properties.getProperty("DESTINATION_PASSWORD"));
    }
}

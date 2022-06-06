package com.liquibase.main;

import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.Scope;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;

// TODO. 直接在Java Application层面执行指定的DB changelog脚本文件
//       取消硬编码在Java code中的SQL语句
public class DemoLiquibaseJava {

    private static String connectionString = "jdbc:postgresql://localhost:5432/my_database?user=postgres&password=admin";

    public static void main(String[] args) throws Exception {
        Map<String, Object> config = new HashMap<>();
        // config.put("liquibase.pro.licenseKey", "YOUR_PRO_KEY");
        Scope.child(config, () -> {
            Connection connection = DriverManager.getConnection(connectionString);

            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
            Liquibase liquibase = new liquibase.Liquibase("/test/changelog.xml",
                    new ClassLoaderResourceAccessor(), database);
            // 指定数据库changelog的变更
            liquibase.update(new Contexts(), new LabelExpression());
            System.out.println("Liquibase test ok.");
        });
    }
}

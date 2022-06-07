package com.liquibase.main;

import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.Scope;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import liquibase.resource.ResourceAccessor;

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
            // JDBC连接到指定的数据库，创建Liquibase Database实例
            Connection connection = DriverManager.getConnection(connectionString);
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));

            String changelogFile = "/test/changelog.xml";
            // 资源获取的存储器，根据ClassLoader来加载指定文件的资源
            ResourceAccessor resourceAccessor = new ClassLoaderResourceAccessor();
            Liquibase liquibase = new liquibase.Liquibase(changelogFile, resourceAccessor, database);
            // 指定要操作的schema的名称
            liquibase.setChangeLogParameter("database.schema", database.getDefaultSchemaName());

            // 指定数据库changelog的变更
            liquibase.update(new Contexts(), new LabelExpression());
            System.out.println("Liquibase test ok.");

            // 关闭数据库连接资源，避免泄露
            database.close();
        });
    }
}

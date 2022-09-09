package com.liquibase.main;

import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.Scope;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.core.PostgresDatabase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import liquibase.resource.ResourceAccessor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;

// TODO. Liquibase框架封装思想
// 1. 继承Connection，将连接封装到自定的MyConnection对象中
// 2. 将JdbcConnection和Database包装到自定义的辅助类型LiquibaseHelper中, 控制其生命周期
public class DemoLiquibaseJava {

    private static String psqlConnectStr = "jdbc:postgresql://localhost:5432/my_database?user=postgres&password=admin";

    public static void main(String[] args) throws Exception {
        Map<String, Object> config = new HashMap<>();
        config.put("liquibase.pro.licenseKey", "YOUR_PRO_KEY");
        Scope.child(config, () -> {
            // 定义使用什么方式进行Connection连接
            Connection connection = DriverManager.getConnection(psqlConnectStr);
            JdbcConnection jdbcConnection = new JdbcConnection(connection);
            // 根据DatabaseConnection连接来生成Database的具体实现类型
            // 背后会调用returnDatabase.setConnection(connection);方法
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(jdbcConnection);

            database.setDefaultSchemaName("public");
            if (database instanceof PostgresDatabase) {
                System.out.println("Created Postgres Database !");
            }

            System.out.println("test OK");
            database.close();
            connection.close();
        });
    }

    // TODO. 直接在Java Application层面执行指定的DB changelog脚本文件, 取消硬编码
    // 同一个Table不能在同一个数据库中创建两次
    // liquibase.exception.DatabaseException: ERROR: relation "t_sample_test" already exists
    public static void migrate(Database database) throws LiquibaseException {
        String changelogFile = "/test/changelog.xml";
        ResourceAccessor resourceAccessor = new ClassLoaderResourceAccessor();
        try (Liquibase liquibase = new liquibase.Liquibase(changelogFile, resourceAccessor, database)) {
            liquibase.clearCheckSums();
            // 指定要操作的schema的名称，执行指定数据库changelog的变更
            liquibase.setChangeLogParameter("database.schema", database.getDefaultSchemaName());
            liquibase.update(new Contexts(), new LabelExpression());
        }
    }
}

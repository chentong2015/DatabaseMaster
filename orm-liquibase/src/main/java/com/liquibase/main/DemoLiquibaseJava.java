package com.liquibase.main;

import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.Scope;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.core.MySQLDatabase;
import liquibase.database.core.PostgresDatabase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import liquibase.resource.ResourceAccessor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;

public class DemoLiquibaseJava {

    private static String mysqlConnectStr = "jdbc:mysql://localhost:3306/my_database?rewriteBatchedStatements=true";
    private static String psqlConnectStr = "jdbc:postgresql://localhost:5432/my_database?user=postgres&password=admin";
    private static String psqlUrl = "jdbc:postgresql://dell1451xxx:5432/tpk0002795_56979469";

    public static void main(String[] args) throws Exception {
        Map<String, Object> config = new HashMap<>();
        // config.put("liquibase.pro.licenseKey", "YOUR_PRO_KEY");
        Scope.child(config, () -> {
            // 定义使用什么方式进行Connection连接
            // Connection connection = DriverManager.getConnection(psqlConnectStr);
            // Connection connection = DriverManager.getConnection(mysqlConnectStr, "root", "admin");
            Connection connection = DriverManager.getConnection(psqlUrl, "postgres", "postgres");
            JdbcConnection jdbcConnection = new JdbcConnection(connection);

            // 根据DatabaseConnection连接来生成Database的具体实现类型
            // 底层会调用returnDatabase.setConnection(connection);
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(jdbcConnection);
            System.out.println(database.isAutoCommit());

            database.setDefaultSchemaName("public");
            if (database instanceof PostgresDatabase) {
                // connection.commit();
                // connection.setAutoCommit(false);

                database.commit();
                database.setAutoCommit(false);
                System.out.println("Created Postgres Database !");
            } else if (database instanceof MySQLDatabase) {
                System.out.println("Created MySql Database !");
            }

            Thread.sleep(30000);
            System.out.println("test OK");
            // 这里底层会调用到connection.close()方法
            database.close();
        });
    }

    // TODO. 直接在Java Application层面执行指定的DB changelog脚本文件, 取消硬编码
    public static void migrate(Database database) throws LiquibaseException {
        String changelogFile = "/test/changelog.xml";
        ResourceAccessor resourceAccessor = new ClassLoaderResourceAccessor();
        try (Liquibase liquibase = new liquibase.Liquibase(changelogFile, resourceAccessor, database)) {
            // TODO. Clear Check sums的作用是什么 ?
            liquibase.clearCheckSums();
            // 指定要操作的schema的名称，执行指定数据库changelog的变更
            liquibase.setChangeLogParameter("database.schema", database.getDefaultSchemaName());
            liquibase.update(new Contexts(), new LabelExpression());
        }
    }
}

package com.liquibase.main;

import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.Scope;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
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

    // 资源获取的存储器，根据ClassLoader来加载指定文件的资源
    public static void main(String[] args) throws Exception {
        Map<String, Object> config = new HashMap<>();
        config.put("liquibase.pro.licenseKey", "YOUR_PRO_KEY");
        Scope.child(config, () -> {
            Connection connection = DriverManager.getConnection(psqlConnectStr);
            JdbcConnection jdbcConnection = new JdbcConnection(connection);
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(jdbcConnection);
            database.setDefaultSchemaName("default schema name");

            migrate(database);
            database.close();
            connection.close();
        });
    }

    // TODO. 直接在Java Application层面执行指定的DB changelog脚本文件, 取消硬编码
    //  同一个Table不能在同一个数据库中创建两次
    // liquibase.exception.DatabaseException: ERROR: relation "t_sample_test" already exists
    public static void migrate(Database database) throws LiquibaseException {
        String changelogFile = "/test/changelog.xml";
        ResourceAccessor resourceAccessor = new ClassLoaderResourceAccessor();
        Liquibase liquibase = new liquibase.Liquibase(changelogFile, resourceAccessor, database);
        liquibase.clearCheckSums();

        // 指定要操作的schema的名称，执行指定数据库changelog的变更
        liquibase.setChangeLogParameter("database.schema", database.getDefaultSchemaName());
        liquibase.update(new Contexts(), new LabelExpression());
    }
}

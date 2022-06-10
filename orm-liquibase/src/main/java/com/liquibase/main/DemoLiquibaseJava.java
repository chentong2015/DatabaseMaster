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
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

// TODO. 直接在Java Application层面执行指定的DB changelog脚本文件, 取消硬编码
public class DemoLiquibaseJava {

    // 这里的数据库连接信息可以使用ConfigHeler从属性文件中指定获取
    private static String connectionString = "jdbc:postgresql://localhost:5432/my_database?user=postgres&password=admin";

    // TODO. 使用纯JDBC连接到指定的数据库，创建Liquibase Database实例
    // 不提供需要加载的changelog变更文件
    private Database createLiquibaseDatabase() throws SQLException, LiquibaseException {
        try {
            Connection connection = DriverManager.getConnection(connectionString);
            JdbcConnection jdbcConnection = new JdbcConnection(connection);
            Liquibase liquibase = new Liquibase(null, null, jdbcConnection);
            return liquibase.getDatabase();
        } catch (SQLException e) {
            throw new SQLException("Error in connection properties." + e, e);
        }
    }

    // 资源获取的存储器，根据ClassLoader来加载指定文件的资源
    // 并执行指定数据库changelog的变更
    public static void main(String[] args) throws Exception {
        Map<String, Object> config = new HashMap<>();
        // config.put("liquibase.pro.licenseKey", "YOUR_PRO_KEY");
        Scope.child(config, () -> {
            Connection connection = DriverManager.getConnection(connectionString);
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));

            String changelogFile = "/test/changelog.xml";
            ResourceAccessor resourceAccessor = new ClassLoaderResourceAccessor();
            Liquibase liquibase = new liquibase.Liquibase(changelogFile, resourceAccessor, database);
            // 指定要操作的schema的名称
            liquibase.setChangeLogParameter("database.schema", database.getDefaultSchemaName());
            liquibase.update(new Contexts(), new LabelExpression());

            // 关闭数据库连接资源，避免泄露
            database.close();
        });
    }
}

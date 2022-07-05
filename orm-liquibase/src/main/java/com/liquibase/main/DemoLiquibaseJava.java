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

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

// TODO. 直接在Java Application层面执行指定的DB changelog脚本文件, 取消硬编码
public class DemoLiquibaseJava {

    // 这里的数据库连接信息可以使用ConfigHeler从属性文件中指定获取
    private static String psqlConnectStr = "jdbc:postgresql://localhost:5432/my_database?user=postgres&password=admin";
    private static String sqlServerConnectStr = "jdbc:sqlserver://LCTON01:1433;databaseName=my_database;Trusted_Connection=true;user=test;password=TCHong16";

    // 资源获取的存储器，根据ClassLoader来加载指定文件的资源
    public static void main(String[] args) throws Exception {
        Map<String, Object> config = new HashMap<>();
        // config.put("liquibase.pro.licenseKey", "YOUR_PRO_KEY");
        Scope.child(config, () -> {
            Connection connection = DriverManager.getConnection(psqlConnectStr);

            JdbcConnection jdbcConnection = new JdbcConnection(connection);
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(jdbcConnection);
            migrate(database);

            // dropTableIfExist(connection, "t_sample_test");
            database.close();
            connection.close();
        });
    }

    // TODO. 同一个Table不能在同一个数据库中创建两次
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

    // schemaPattern a schema name pattern; must match the schema name
    // 1. "" retrieves those without a schema
    // 2. "null" means that the schema name should not be used to narrow the search
    //    忽略掉不同数据库的schema名称来进行查找, 不受DB的约束
    private static void dropTableIfExist(Connection connection, String tableName) {
        try (Statement statement = connection.createStatement()) {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet resultSet = metaData.getTables(null, null, tableName, null);
            if (resultSet.next()) {
                statement.execute("DROP TABLE " + tableName);
            } else {
                System.out.println("Cannot drop the table" + tableName + ", because it does not exist");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}

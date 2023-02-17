package main.exception;

import liquibase.Scope;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.core.MySQLDatabase;
import liquibase.database.core.PostgresDatabase;
import liquibase.database.jvm.JdbcConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;

public class DemoPsqlDBConnectionException {

    private static String psqlConnectStr = "jdbc:postgresql://localhost:5432/my_database?user=postgres&password=admin";

    public static void main(String[] args) throws Exception {
        Map<String, Object> config = new HashMap<>();
        // config.put("liquibase.pro.licenseKey", "YOUR_PRO_KEY");
        Scope.child(config, () -> {
            // 定义使用什么方式进行Connection连接
            Connection connection = DriverManager.getConnection(psqlConnectStr);
            // Connection connection = DriverManager.getConnection(mysqlConnectStr, "root", "admin");
            // Connection connection = DriverManager.getConnection(psqlUrl, "postgres", "postgres");
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

            // 这里底层会调用到connection.close()方法
            database.close();
        });
    }
}

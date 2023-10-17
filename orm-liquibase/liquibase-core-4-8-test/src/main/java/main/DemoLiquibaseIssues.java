package main;

import liquibase.Contexts;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Liquibase在翻译changelog文件中的changeSet时会构建出SQL Query
public class DemoLiquibaseIssues {

    private static String oracleString = "jdbc:oracle:thin:@//dell719xxx:1521/DELL719SRV";
    private static String psqlConnectStr = "jdbc:postgresql://localhost:5432/liquibase_upgrade_4_8?user=postgres&password=admin";
    private static String sqlServerConnectStr = "jdbc:sqlserver://localhost:1433;Database=liquibase_4_8;Trusted_Connection=true;";

    public static void main(String[] args) throws DatabaseException, SQLException {
        // Connection connection = DriverManager.getConnection(oracleString, "DAS_CONV_TOOL", "DAS_CONV_TOOL");
        // Connection connection = DriverManager.getConnection(oracleString, "JAVA_INT_TESTS", "JAVA_INT_TESTS");
        // Connection connection = DriverManager.getConnection(sqlServerConnectStr, "test", "TCHong20");

        Connection connection = DriverManager.getConnection(psqlConnectStr);
        JdbcConnection jdbcConnection = new JdbcConnection(connection);

        // 自定义生成到database中的changelog表名称
        Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(jdbcConnection);
        // database.setDatabaseChangeLogTableName("databasechangelog_sub");
        // database.setDatabaseChangeLogLockTableName("databasechangeloglock_sub");

        String changelog = "changelog-simple.xml";
        try (Liquibase liquibase = new Liquibase(changelog, new ClassLoaderResourceAccessor(), database)) {
            liquibase.update(new Contexts());
        } catch (LiquibaseException e) {
            e.printStackTrace();
        }
    }
}

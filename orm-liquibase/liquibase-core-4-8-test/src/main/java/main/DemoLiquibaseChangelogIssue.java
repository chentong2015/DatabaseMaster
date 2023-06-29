package main;

import liquibase.Contexts;
import liquibase.LabelExpression;
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
public class DemoLiquibaseChangelogIssue {

    private static String changelog = "changelog-master-agreements.xml";
    private static String oracleString = "jdbc:oracle:thin:@//dell719srv:1521/DELL719SRV";

    private static String psqlConnectStr = "jdbc:postgresql://localhost:5432/liquibase_upgrade_4_8?user=postgres&password=admin";
    private static String sqlServerConnectStr = "jdbc:sqlserver://localhost:1433;Database=liquibase_4_8;Trusted_Connection=true;";

    public static void main(String[] args) throws DatabaseException, SQLException {
        Connection connection = DriverManager.getConnection(oracleString, "DAS_CONV_TOOL", "DAS_CONV_TOOL");
        // Connection connection = DriverManager.getConnection(oracleString, "JAVA_INT_TESTS", "JAVA_INT_TESTS");

        // Connection connection = DriverManager.getConnection(psqlConnectStr);
        // Connection connection = DriverManager.getConnection(sqlServerConnectStr, "test", "TCHong20");
        JdbcConnection jdbcConnection = new JdbcConnection(connection);
        Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(jdbcConnection);

        try (Liquibase liquibase = new Liquibase(changelog, new ClassLoaderResourceAccessor(), database)) {
            liquibase.update(new Contexts(), new LabelExpression());
        } catch (LiquibaseException e) {
            e.printStackTrace();
        }
    }
}

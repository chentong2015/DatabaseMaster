package main;

import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class LiquibaseTester {

    private static String sqlServerConnectStr = "jdbc:sqlserver://localhost:1433;Database=liquibase-4-18;Trusted_Connection=true;useBulkCopyForBatchInsert=true;";
    // Connection connection = DriverManager.getConnection(sqlServerConnectStr, "test", "TCHong20");

    private static String psqlConnectStr = "jdbc:postgresql://localhost:5432/liquibase_upgrade_4_18?user=postgres&password=admin";

    public static void main(String[] args) throws SQLException, LiquibaseException {
        Connection connection = DriverManager.getConnection(psqlConnectStr);
        // Connection connection = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/testdb", "SA", "");

        JdbcConnection jdbcConnection = new JdbcConnection(connection);
        Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(jdbcConnection);

        // try (Liquibase liquibase = new Liquibase("key/changelog-foreign-key.xml",
        //         new ClassLoaderResourceAccessor(), database)) {
        //     liquibase.update(new Contexts(), new LabelExpression());
        // }
    }
}

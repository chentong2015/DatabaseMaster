package main;

import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;

import java.sql.Connection;
import java.sql.DriverManager;

public class LiquibaseTester {

    // CommandLineUtils
    // DatabaseChangeLog
    // IndexSnapshotGenerator
    private static String sqlServerConnectStr = "jdbc:sqlserver://localhost:1433;Database=liquibase-4-5;Trusted_Connection=true;useBulkCopyForBatchInsert=true;";
    private static String psqlConnectStr = "jdbc:postgresql://localhost:5432/liquibase_upgrade_4_5?user=postgres&password=admin";

    public static void main(String[] args) throws Exception {
        Connection connection = DriverManager.getConnection(sqlServerConnectStr, "test", "TCHong18");
        // Connection connection = DriverManager.getConnection(psqlConnectStr);
        JdbcConnection jdbcConnection = new JdbcConnection(connection);

        Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(jdbcConnection);
        ClassLoaderResourceAccessor resourceAccessor = new ClassLoaderResourceAccessor();

        try (Liquibase liquibase = new Liquibase("changelog-proc.xml", resourceAccessor, database)) {
            liquibase.update(new Contexts(), new LabelExpression());
        }
    }
}

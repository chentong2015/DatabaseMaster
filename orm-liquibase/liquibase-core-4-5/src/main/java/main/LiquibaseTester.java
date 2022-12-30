package main;

import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import liquibase.snapshot.SnapshotGeneratorFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class LiquibaseTester {

    private static String sqlServerConnectStr = "jdbc:sqlserver://localhost:1433;Database=test_db;Trusted_Connection=true;useBulkCopyForBatchInsert=true;";
    private static String psqlConnectStr = "jdbc:postgresql://localhost:5432/liquibase_upgrade_4_5?user=postgres&password=admin";

    public static void main(String[] args) throws SQLException, LiquibaseException {
        Connection connection = DriverManager.getConnection(sqlServerConnectStr, "test", "TCHong18");
        JdbcConnection jdbcConnection = new JdbcConnection(connection);
        SnapshotGeneratorFactory.getInstance().register(new MyColumnSnapshotGenerator());

        Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(jdbcConnection);
        ClassLoaderResourceAccessor resourceAccessor = new ClassLoaderResourceAccessor();

        try (Liquibase liquibase = new Liquibase("table-api-changelog.xml", resourceAccessor, database)) {
            liquibase.update(new Contexts(), new LabelExpression());
        }
    }
}

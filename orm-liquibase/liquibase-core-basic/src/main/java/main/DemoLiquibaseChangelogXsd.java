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

public class DemoLiquibaseChangelogXsd {

    private static String psqlConnectStr = "jdbc:postgresql://localhost:5432/my_database?user=postgres&password=admin";

    public static void main(String[] args) throws DatabaseException, SQLException {
        Connection connection = DriverManager.getConnection(psqlConnectStr);
        JdbcConnection jdbcConnection = new JdbcConnection(connection);
        Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(jdbcConnection);

        try (Liquibase liquibase = new Liquibase("xml_schema_definition/changelog-xsd.xml", new ClassLoaderResourceAccessor(), database)) {
            liquibase.update(new Contexts(), new LabelExpression());
        } catch (LiquibaseException e) {
            e.printStackTrace();
        }
    }
}

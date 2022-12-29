package main;

import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class LiquibaseTester {

    private static String psqlConnectStr = "jdbc:postgresql://localhost:5432/liquibase_upgrade_4_5?user=postgres&password=admin";

    public static void main(String[] args) throws SQLException, LiquibaseException {
        Connection connection = DriverManager.getConnection(psqlConnectStr);
        JdbcConnection jdbcConnection = new JdbcConnection(connection);
        Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(jdbcConnection);
        ClassLoaderResourceAccessor resourceAccessor = new ClassLoaderResourceAccessor();

        try (Liquibase liquibase = new Liquibase("load-update-data-changelog.xml", resourceAccessor, database)) {
            liquibase.update(new Contexts(), new LabelExpression());
        }
    }

    private static List<Path> getAllFilesFromResource() {
        try {
            URL resource = LiquibaseTester.class.getClassLoader().getResource("test");
            return Files.walk(Paths.get(resource.toURI()))
                    .filter(Files::isRegularFile)
                    .map(x -> x.getFileName())
                    .collect(Collectors.toList());
        } catch (Exception exception) {
            exception.printStackTrace();
            return Collections.emptyList();
        }
    }
}

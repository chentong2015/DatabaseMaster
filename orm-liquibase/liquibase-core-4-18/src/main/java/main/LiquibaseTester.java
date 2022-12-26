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

    private static Database database;
    private static String psqlConnectStr = "jdbc:postgresql://localhost:5432/liquibase_upgrade_4_18?user=postgres&password=admin";

    public static void main(String[] args) throws SQLException, LiquibaseException {
        Connection connection = DriverManager.getConnection(psqlConnectStr);
        JdbcConnection jdbcConnection = new JdbcConnection(connection);
        database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(jdbcConnection);
        List<Path> pathList = getAllFilesFromResource("/");
        for (Path path : pathList) {
            System.out.println(path.toString());
        }

        try (Liquibase liquibase = new Liquibase("column-types-changelog.xml", new ClassLoaderResourceAccessor(), database)) {
            liquibase.update(new Contexts(), new LabelExpression());
        }
    }

    private static List<Path> getAllFilesFromResource(String folder) {
        try {
            URL resource = LiquibaseTester.class.getClassLoader().getResource(folder);
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

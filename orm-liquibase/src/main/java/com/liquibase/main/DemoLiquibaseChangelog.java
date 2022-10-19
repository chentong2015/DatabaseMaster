package com.liquibase.main;

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

public class DemoLiquibaseChangelog {

    private static String psqlConnectStr = "jdbc:postgresql://localhost:5432/my_database?user=postgres&password=admin";

    public static void main(String[] args) throws DatabaseException, SQLException {
        Connection connection = DriverManager.getConnection(psqlConnectStr, "postgres", "postgres");
        JdbcConnection jdbcConnection = new JdbcConnection(connection);
        Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(jdbcConnection);

        String changelogFile = "/psql/changelog-test.xml";
        try (Liquibase liquibase = new liquibase.Liquibase(changelogFile, new ClassLoaderResourceAccessor(), database)) {
            // TODO. 删除changlog日志表中的md5sum验校字段
            liquibase.clearCheckSums();

            // 指定要操作的schema的名称，执行指定数据库changelog的变更
            // liquibase.setChangeLogParameter("database.schema", database.getDefaultSchemaName());

            liquibase.update(new Contexts(), new LabelExpression());
        } catch (LiquibaseException e) {
            throw new RuntimeException(e);
        }
    }
}

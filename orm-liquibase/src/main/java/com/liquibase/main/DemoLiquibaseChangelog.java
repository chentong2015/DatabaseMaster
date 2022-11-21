package com.liquibase.main;

import com.liquibase.main.extensions.sqlgenerator.MyCreateIndexGenerator;
import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import liquibase.sqlgenerator.SqlGeneratorFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DemoLiquibaseChangelog {

    private static String mysqlConnectStr = "jdbc:mysql://localhost:3306/my_database?rewriteBatchedStatements=true";
    private static String sqlServerConnectStr = "jdbc:sqlserver://localhost:1433;Database=test_db;Trusted_Connection=true;useBulkCopyForBatchInsert=true;";
    private static String psqlConnectStr = "jdbc:postgresql://localhost:5432/my_database?user=postgres&password=admin";
    private static String psqlRemoteUrl = "jdbc:postgresql://dell1451xxx:5432/tpk0002795_56979469";

    public static void main(String[] args) throws DatabaseException, SQLException {
        // "postgres", "postgres"
        Connection connection = DriverManager.getConnection(sqlServerConnectStr, "test", "TCHong18");
        JdbcConnection jdbcConnection = new JdbcConnection(connection);

        SqlGeneratorFactory.getInstance().register(new MyCreateIndexGenerator());
        Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(jdbcConnection);
        fireChangelog(database, "sqlserver/changelog-index.xml");
    }

    private static void fireChangelog(Database database, String changelogFilepath) {
        try (Liquibase liquibase = new liquibase.Liquibase(changelogFilepath, new ClassLoaderResourceAccessor(), database)) {
            // 删除changelog日志表中的md5sum验校字段
            // liquibase.clearCheckSums();

            // 指定要操作的schema的名称，执行指定数据库changelog的变更
            // liquibase.setChangeLogParameter("database.schema", database.getDefaultSchemaName());

            liquibase.update(new Contexts(), new LabelExpression());
        } catch (LiquibaseException e) {
            e.printStackTrace();
        }
    }

    // TODO. 使用纯JDBC连接到指定的数据库，创建Liquibase Database实例
    // 不提供需要加载的changelog变更文件
    private Database createLiquibaseDatabase(String connectString) throws SQLException, LiquibaseException {
        try {
            Connection connection = DriverManager.getConnection(connectString);
            // Connection connection = DriverManager.getConnection(mySqlConnectStr, "root", "admin");
            JdbcConnection jdbcConnection = new JdbcConnection(connection);
            Liquibase liquibase = new Liquibase(null, null, jdbcConnection);
            return liquibase.getDatabase();
        } catch (SQLException e) {
            throw new SQLException("Error in connection properties." + e, e);
        }
    }
}

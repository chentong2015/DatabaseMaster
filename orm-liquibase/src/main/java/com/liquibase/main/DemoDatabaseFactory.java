package com.liquibase.main;

import com.liquibase.main.database.MyDatabase;
import com.liquibase.main.datatype.MyDateTimeType;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.datatype.DataTypeFactory;
import liquibase.exception.LiquibaseException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DemoDatabaseFactory {

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

    // 将自定义的类型注册到Factory工厂中
    public void registerDatabase() {
        DatabaseFactory.getInstance().register(new MyDatabase());
        DataTypeFactory.getInstance().register(new MyDateTimeType());
        DatabaseFactory.getInstance().getDatabase("db name");

        // SnapshotGeneratorFactory.getInstance().register(new MyColumnSnapshotGenerator());
        // PreconditionFactory.getInstance().register(new MyIsNullablePrecondition());
        // SqlGeneratorFactory.getInstance().register(new MyDropColumnGenerator());
    }
}

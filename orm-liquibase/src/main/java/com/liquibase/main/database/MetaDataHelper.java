package com.liquibase.main.database;

import java.sql.*;

// TODO. 不同数据库通过MetaData.getTables()判断table是否存在的方式不同
// getTables is case sensitive for postgres 大小写必须严格的一致
// getTables is not case sensitive for sql server 字符大小写均可以查询
public class MetaDataHelper {

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(DbConnectionString.sqlServerConnectStr)) {
            dropTableIfExist(connection, "t_book");
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    // schemaPattern a schema name pattern; must match the schema name
    // 1. "" retrieves those without a schema
    // 2. "null" means that the schema name should not be used to narrow the search
    //    忽略掉不同数据库的schema名称来进行查找, 不受DB的约束
    private static void dropTableIfExist(Connection connection, String tableName) {
        try (Statement statement = connection.createStatement()) {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet resultSet = metaData.getTables(null, "dbo", tableName, null);
            if (resultSet.next()) {
                // statement.execute("DROP TABLE " + tableName);
                System.out.println("Find table name: " + tableName);
            } else {
                System.out.println("Cannot drop the table " + tableName + ", because it does not exist");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // 不同的数据库删除Function和Procedure的方式不同
    public void dropProcedure(MyDatabaseType dbType, String procedureName) throws SQLException {
        Connection connection = DriverManager.getConnection(DbConnectionString.sqlServerConnectStr);
        if (dbType == MyDatabaseType.ORACLE) {
            try (Statement statement = connection.createStatement()) {
                statement.execute("DROP FUNCTION " + formatProcedureName(procedureName));
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else if ((dbType == MyDatabaseType.SYBASE)
                || (dbType == MyDatabaseType.MS_SQL_SERVER)
                || (dbType == MyDatabaseType.HSQL_SERVER)) {
            try (Statement statement = connection.createStatement()) {
                statement.execute("DROP PROCEDURE " + procedureName);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else if ((dbType == MyDatabaseType.POSTGRES)) {
            try (Statement statement = connection.createStatement()) {
                statement.execute("DROP FUNCTION " + procedureName.toLowerCase());
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private String formatProcedureName(String name) {
        // Format procedure name
        return name + "_";
    }
}

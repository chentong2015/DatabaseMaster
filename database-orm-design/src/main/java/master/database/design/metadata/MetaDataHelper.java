package master.database.design.metadata;

import java.sql.*;

// TODO. 不同数据库通过MetaData.getTables()判断table是否存在的方式不同
// getTables is case sensitive for postgres 大小写必须严格的一致
// getTables is not case sensitive for sql server 字符大小写均可以查询
public class MetaDataHelper {

    private static String psqlConnectStr = "jdbc:postgresql://localhost:5432/my_database?user=postgres&password=admin";
    private static String psqlConnectStr2 = "jdbc:postgresql://dell719srv:5432/java_int_tests?user=java_int_tests&password=JAVA_INT_TESTS";
    private static String sqlServerConnectStr = "jdbc:sqlserver://LCTON01:1433;databaseName=my_database;Trusted_Connection=true;user=test;password=TCHong16";

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(psqlConnectStr)) {
            dropTableIfExist(connection, "TEST_TABLE");
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    // schemaPattern a schema name pattern; must match the schema name
    // 1. "" retrieves those without a schema
    // 2. "null" means that the schema name should not be used to narrow the search
    //    忽略掉不同数据库的schema名称来进行查找, 不受DB的约束
    private static void dropTableIfExist(Connection connection, String tableNamePattern) {
        try (Statement statement = connection.createStatement()) {
            // 获取指定database数据库名称
            String catalog = connection.getCatalog();
            // TODO. Retrieves this Connection object's current schema name.
            // 获取当前连接的默认Schema名称
            String schemaPattern = connection.getSchema();
            String[] tableTypes = new String[]{"TABLE"};

            // TODO. 这里必须同时满足schemaPattern和tableNamePattern: 不同的DB参数有区别
            ResultSet resultSet = connection.getMetaData().getTables(catalog, schemaPattern, tableNamePattern, tableTypes);
            if (resultSet.next()) {
                // statement.execute("DROP TABLE " + tableName);
                System.out.println("Find table name: " + tableNamePattern);
            } else {
                System.out.println("Cannot drop the table " + tableNamePattern + ", because it does not exist");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // 不同的数据库删除Function和Procedure的方式不同
    public void dropProcedure(MyDatabaseType dbType, String procedureName) throws SQLException {
        Connection connection = DriverManager.getConnection(sqlServerConnectStr);
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

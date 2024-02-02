package metadata;

import java.sql.*;

public class DbMetaDataDemo {

    private static String psqlConnectStr = "jdbc:postgresql://localhost:5432/my_database?user=postgres&password=admin";
    private static String psqlConnectStr2 = "jdbc:postgresql://xxx:5432/java_int_tests?user=java_int_tests&password=JAVA_INT_TESTS";

    private static String sqlServerConnectStr = "jdbc:sqlserver://driver_name:1433;databaseName=my_database;Trusted_Connection=true;user=test;password=TCHong16";
    private static String sqlServerConnectStr2 = "jdbc:sqlserver://localhost:1433;Database=liquibase-4-18;Trusted_Connection=true;useBulkCopyForBatchInsert=true;";

    private static String sqlOracleConnectStr = "jdbc:oracle:thin:@//dell756xxx:1521/DELL756xxx";

    // 通过JDBC来拿到数据库中相关表的信息
    public static void main(String[] args) throws SQLException {
        // Connection connection = DriverManager.getConnection(psqlConnectStr)
        // Connection connection = DriverManager.getConnection(sqlServerConnectStr, "test", "TCHong20");

        Connection connection = DriverManager.getConnection(sqlOracleConnectStr, "", "");
        testMetaData(connection);
        connection.close();
    }

    // TODO. 直接查询string类型的列，返回结果的ColumnType为1，对应JDBC Type char
    //       添加RTRIM()查询之后，返回结果的ColumnType为12，对应JDBC Type varchar
    public static void testMetaData(Connection connection) throws SQLException {
        String query = "SELECT RTRIM(M_LABEL) from WF_STFLD_DBF where M_IDENTITY = 92";
        ResultSet resultSet = connection.createStatement().executeQuery(query);
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        System.out.println(resultSetMetaData.getColumnType(1));
        resultSet.next();
        System.out.println(resultSet.getString(1));
        resultSet.close();
    }

    // TODO. 表中M_LABEL1列的名称会随不同数据库而变化，Postgres的列为小写，Oracle的列为大写
    private static boolean checkTableExist(Connection connection) throws SQLException {
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet resultSet = metaData.getColumns(null, null, "T_TABLE", "M_LABEL1");
        return resultSet.next();
    }
}

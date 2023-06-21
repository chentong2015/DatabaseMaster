package database.design.metadata;

import java.sql.*;

public class DbMetaDataDemo {

    private static String psqlConnectStr = "jdbc:postgresql://localhost:5432/my_database?user=postgres&password=admin";
    private static String psqlConnectStr2 = "jdbc:postgresql://xxx:5432/java_int_tests?user=java_int_tests&password=JAVA_INT_TESTS";

    private static String sqlServerConnectStr = "jdbc:sqlserver://driver_name:1433;databaseName=my_database;Trusted_Connection=true;user=test;password=TCHong16";
    private static String sqlServerConnectStr2 = "jdbc:sqlserver://localhost:1433;Database=liquibase-4-18;Trusted_Connection=true;useBulkCopyForBatchInsert=true;";

    // 通过JDBC来拿到数据库中相关表的信息
    public static void main(String[] args) throws SQLException {
        // Connection connection = DriverManager.getConnection(psqlConnectStr)
        // Connection connection = DriverManager.getConnection(sqlServerConnectStr, "test", "TCHong19");

        String url = "jdbc:oracle:thin:@//dell1230xxx:1521/DELL12303";
        Connection connection = DriverManager.getConnection(url, "TPK0002912_62025675_1", "xxx");

        // TODO. 表中M_LABEL1列的名称会随不同数据库而变化，Postgres的列为小写，Oracle的列为大写
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet resultSet = metaData.getColumns(null, null, "T_TABLE", "M_LABEL1");
        System.out.println(resultSet.next());

        connection.close();
    }
}

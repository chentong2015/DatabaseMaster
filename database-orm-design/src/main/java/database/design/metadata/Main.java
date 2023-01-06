package database.design.metadata;

import java.sql.*;

public class Main {

    private static String sqlServerConnectStr = "jdbc:sqlserver://localhost:1433;Database=liquibase-4-18;Trusted_Connection=true;useBulkCopyForBatchInsert=true;";

    public static void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection(sqlServerConnectStr, "test", "TCHong18");
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet resultSet = metaData.getColumns(null, "dao", "Table_2", "name");
        System.out.println("ok");
    }
}

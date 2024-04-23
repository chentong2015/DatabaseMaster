package com.oracle.main;

import com.oracle.main.batching.DemoOracleBatching;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DemoOracleJDBC {

    public static void main(String[] args) throws SQLException {
        Connection connection = null;
        try {
            String url = "jdbc:oracle:thin:@//dell719xxx:1521/DELL719xxx";
            connection = DriverManager.getConnection(url, "DAS_CONV_TOOL", "xxx");
            int[] countLines = DemoOracleBatching.testPrepareStatementInsert(connection);
            for (int count : countLines) {
                System.out.println(count);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }
}

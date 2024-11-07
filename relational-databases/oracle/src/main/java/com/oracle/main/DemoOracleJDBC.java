package com.oracle.main;

import java.sql.*;

public class DemoOracleJDBC {

    private static String url = "jdbc:oracle:thin:@//localhost:1522/orclcdb";

    public static void main(String[] args) throws SQLException {
        Connection connection =  DriverManager.getConnection(url, "fmm", "hello00");

        String query = "insert into FOFUTI_SCTR (ctrysynid, country, formatok) values (?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        for (int id = 231; id < 1000; id++) {
            statement.setString(1, String.valueOf(id));
            statement.setString(2, "Country-" + (id));
            statement.setString(3, "0");
            statement.executeUpdate();
        }
        statement.close();

        // Make sure to close connection
        connection.close();
    }
}

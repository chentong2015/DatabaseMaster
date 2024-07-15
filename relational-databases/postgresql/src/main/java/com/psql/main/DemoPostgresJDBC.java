package com.psql.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DemoPostgresJDBC {

    // private static String url = "jdbc:postgresql://dell1451xxx:5432/tpk0002795_56979469";
    private static String url = "jdbc:postgresql://localhost:5432/my_database?reWriteBatchedInserts=true";

    public static void main(String[] args) throws SQLException {
        try (Connection connection = DriverManager.getConnection(url, "postgres", "")) {
            connection.getSchema(); // Default Schema: public
            connection.setAutoCommit(false); // Enable by default: AutoCommit=true !!
            System.out.println(connection.getAutoCommit());

            ResultSet resultSet = connection.createStatement().executeQuery("select * from t_test");
            while (resultSet.next()) {
                System.out.println(resultSet.getInt("id"));
                System.out.println(resultSet.getString("name"));
            }
            System.out.println("Connection Done");
        }
    }
}

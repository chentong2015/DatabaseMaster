package com.psql.main;

import com.psql.main.batching.PostgresSqlBatching;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

// TODO. PostgreSQL JDBC Connection默认开启AutoCommit
public class DemoPostgresJDBC {

    private static String url = "jdbc:postgresql://localhost:5432/my_database?reWriteBatchedInserts=true";
    // private static String url = "jdbc:postgresql://dell1451xxx:5432/tpk0002795_56979469";

    public static void main(String[] args) throws SQLException, InterruptedException {
        try (Connection connection = DriverManager.getConnection(url, "postgres", "admin")) {
            System.out.println(connection.getAutoCommit());

            // 返回的是受影响的行数，准确的被修改的行数
            String query = "DELETE FROM t_batching_comment WHERE id<5";
            Statement statement = connection.createStatement();
            // statement.executeUpdate(query);

            int[] countLines = PostgresSqlBatching.testPrepareStatementInsert(connection);
            for (int count : countLines) {
                System.out.println(count);
            }
        }
    }
}

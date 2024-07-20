package com.psql.main.batching;

import java.sql.*;

// TODO. 设置重写Insert语句"reWriteBatchedInserts=true"以支持批量插入
// https://jdbc.postgresql.org/documentation/94/connect.html
public class PostgresSqlBatching {

    public static void main(String[] args) throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/my_database?reWriteBatchedInserts=true";
        try (Connection connection = DriverManager.getConnection(url, "postgres", "")) {
            int[] countLines = testPrepareStatementInsert(connection);
            System.out.println(countLines.length);
        }
    }

    // TODO. 推荐使用PreparedStatement来执行Batch批量的操作
    public static int[] testPrepareStatementInsert(Connection connection) throws SQLException {
        String query = "INSERT INTO t_comment(id, review) values (?, ?)";
        int[] countLines;
        try (PreparedStatement prepareStatement = connection.prepareStatement(query)) {
            for (int index = 1; index < 100; index++) {
                prepareStatement.setInt(1, index);
                prepareStatement.setString(2, "review name");
                prepareStatement.addBatch();
            }
            countLines = prepareStatement.executeBatch();
        }
        return countLines;
    }

    // TODO. delete语句无法批量删除batch合并
    public static void testPrepareStatementDelete(Connection connection) throws SQLException {
        long startTime = System.currentTimeMillis();
        String query = "DELETE FROM t_comment WHERE id=?";
        try (PreparedStatement prepareStatement = connection.prepareStatement(query)) {
            for (int index = 1; index < 500000; index++) {
                prepareStatement.setInt(1, index);
                prepareStatement.addBatch();
            }
            prepareStatement.executeBatch();
            // prepareStatement.executeLargeBatch();
        }
        System.out.println(System.currentTimeMillis() - startTime);
    }
}

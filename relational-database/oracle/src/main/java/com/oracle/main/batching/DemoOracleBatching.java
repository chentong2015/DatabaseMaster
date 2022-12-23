package com.oracle.main.batching;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DemoOracleBatching {

    // 如果背后没有合并成batch query执行，如何提高批量插入效率 ?
    public static int[] testPrepareStatementInsert(Connection connection) throws SQLException {
        int[] countLines;
        String query = "INSERT INTO T_BATCHING_COMMENT(ID, REVIEW) values (?, ?)";
        try (PreparedStatement prepareStatement = connection.prepareStatement(query)) {
            for (int index = 10; index < 20; index++) {
                prepareStatement.setInt(1, index);
                prepareStatement.setString(2, "review name");
                prepareStatement.addBatch();
            }
            countLines = prepareStatement.executeBatch();
        }
        return countLines;
    }

    public static void testPrepareStatementDelete(Connection connection) throws SQLException {
        long startTime = System.currentTimeMillis();
        String query = "DELETE FROM t_batching_comment WHERE id=?";
        try (PreparedStatement prepareStatement = connection.prepareStatement(query)) {
            for (int index = 10; index < 500000; index++) {
                prepareStatement.setInt(1, index);
                prepareStatement.addBatch();
            }
            prepareStatement.executeBatch();
        }
        System.out.println(System.currentTimeMillis() - startTime);
    }
}

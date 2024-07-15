package com.sybase.main.batching;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DemoSybaseBatching {

    // TODO. 使用Inserting in Bulk可以提高批量插入的效率
    public static int[] testPrepareStatementInsert(Connection connection) throws SQLException {
        String query = "INSERT INTO t_batching_comment(id, review) values (?, ?)";
        int[] countLines;
        try (PreparedStatement prepareStatement = connection.prepareStatement(query)) {
            for (int index = 20; index < 29; index++) {
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
            for (int index = 1; index < 500000; index++) {
                prepareStatement.setInt(1, index);
                prepareStatement.addBatch();
            }
            prepareStatement.executeBatch();
        }
        System.out.println(System.currentTimeMillis() - startTime);
    }
}

package com.oracle.main.batching;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

// Oracle关于批量插入和删除的测试
public class DemoOracleBatching {

    public static void testPrepareStatementInsert(Connection connection) throws SQLException {
        String query = "INSERT INTO t_comment(id, text) values (?, ?)";
        try (PreparedStatement prepareStatement = connection.prepareStatement(query)) {
            for (int index = 10; index < 500000; index++) {
                prepareStatement.setInt(1, index);
                prepareStatement.setString(2, "review name");
                prepareStatement.addBatch();
            }
            prepareStatement.executeBatch();
        }
    }

    public static void testPrepareStatementDelete(Connection connection) throws SQLException {
        long startTime = System.currentTimeMillis();
        String query = "DELETE FROM t_comment WHERE id=?";
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

package com.psql.main.batching;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

// TODO. 对于批量插入，可以设置重写Insert语句"reWriteBatchedInserts=true"
// https://jdbc.postgresql.org/documentation/94/connect.html
public class PostgresSqlBatching {

    // TODO. JDBC has to create the prepared statement dynamically.
    //    batch size不需要显式的设置值的大小
    //    batch size会根据.addBatch(sql)添加的次数进行动态计算，最终划分成两个批次
    // 执行时会将大部分Insert合成第一个batch，最后小部分Insert合成第二个batch
    // If the batch is fairly large, PostgreSQL driver will split it into smaller ones to address TCP deadlock issues.
    //
    // LOG:  execute <unnamed>: BEGIN
    // LOG:  execute <unnamed>: INSERT INTO t_batching_comment(id, review) values ($1, $2),($3, $4)
    // DETAIL:  parameters: $1 = '70', $2 = 'name test', $3 = '71', $4 = 'name test'
    // LOG:  execute <unnamed>: INSERT INTO t_batching_comment(id, review) values ($1, $2)
    // DETAIL:  parameters: $1 = '72', $2 = 'name test'
    // LOG:  execute S_1: COMMIT
    public static int[] testPrepareStatementInsert(Connection connection) throws SQLException {
        String query = "INSERT INTO t_batching_comment(id, review) values (?, ?)";
        int[] countLines;
        try (PreparedStatement prepareStatement = connection.prepareStatement(query)) {
            for (int index = 2000; index < 2002; index++) {
                prepareStatement.setInt(1, index);
                prepareStatement.setString(2, "review name");
                prepareStatement.addBatch();
            }
            countLines = prepareStatement.executeBatch();
        }
        return countLines;
    }

    // TODO. 批量删除，delete语句无法batch合并，只能通过别的方式提高效率
    // LOG:  execute S_1: BEGIN
    // LOG:  execute S_2: DELETE FROM t_batching_comment WHERE id=$1
    // DETAIL:  parameters: $1 = '35'
    // LOG:  execute S_2: DELETE FROM t_batching_comment WHERE id=$1
    // DETAIL:  parameters: $1 = '36'
    // LOG:  execute S_2: DELETE FROM t_batching_comment WHERE id=$1
    // DETAIL:  parameters: $1 = '37'
    // LOG:  execute S_3: COMMIT
    public static void testPrepareStatementDelete(Connection connection) throws SQLException {
        long startTime = System.currentTimeMillis();
        String query = "DELETE FROM t_batching_comment WHERE id=?";
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

    // TODO. 使用Statement执行的Query不会被Batched, 不建议使用 !!
    // LOG:  execute <unnamed>: BEGIN
    // LOG:  execute <unnamed>: INSERT INTO t_batching_comment(id, review) VALUES (35,'name review')
    // LOG:  execute <unnamed>: INSERT INTO t_batching_comment(id, review) VALUES (36,'name review')
    // LOG:  execute S_1: COMMIT
    private static void testStatementInsert(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            for (int index = 35; index < 40; index++) {
                String sql = "INSERT INTO t_batching_comment(id, review) VALUES (" + index + ",'name review')";
                statement.addBatch(sql);
            }
            statement.executeBatch();
        }
    }
}

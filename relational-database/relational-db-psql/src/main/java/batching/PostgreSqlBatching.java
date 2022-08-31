package batching;

import java.sql.*;

// TODO. JDBC has to create the prepared statement dynamically.
// batch size根据.addBatch(sql)添加的次数进行动态计算，最终划分成两个批次
public class PostgreSqlBatching {

    public static void main(String[] args) throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/my_database?reWriteBatchedInserts=true";
        try (Connection connection = DriverManager.getConnection(url, "postgres", "admin")) {
            connection.setAutoCommit(false);
            testPrepareStatementDelete(connection);
            connection.commit();
            connection.setAutoCommit(true);
        }
        // finally: make sure connection be closed
    }

    // TODO. 对于批量插入，可以设置重写Insert语句"reWriteBatchedInserts=true"
    //   https://jdbc.postgresql.org/documentation/94/connect.html
    // 执行时会将大部分Insert合成第一个batch，最后小部分Insert合成第二个batch
    // LOG:  execute <unnamed>: BEGIN
    // LOG:  execute <unnamed>: INSERT INTO t_batching_comment(id, review) values ($1, $2),($3, $4)
    // DETAIL:  parameters: $1 = '70', $2 = 'name test', $3 = '71', $4 = 'name test'
    // LOG:  execute <unnamed>: INSERT INTO t_batching_comment(id, review) values ($1, $2)
    // DETAIL:  parameters: $1 = '72', $2 = 'name test'
    // LOG:  execute S_1: COMMIT
    public static void testPrepareStatementInsert(Connection connection) throws SQLException {
        String query = "INSERT INTO t_batching_comment(id, review) values (?, ?)";
        try (PreparedStatement prepareStatement = connection.prepareStatement(query)) {
            for (int index = 200; index < 1500000; index++) {
                prepareStatement.setInt(1, index);
                prepareStatement.setString(2, "name test");
                prepareStatement.addBatch();
            }
            int[] result = prepareStatement.executeBatch();
            System.out.println(result[0] + ":" + result[1]);
        }
    }

    // TODO. 对于批量删除，delete语句无法batch合并 => 通过别的方式来提高删除的效率
    // LOG:  execute S_1: BEGIN
    // LOG:  execute S_2: DELETE FROM t_batching_comment WHERE id=$1
    // DETAIL:  parameters: $1 = '35'
    // LOG:  execute S_2: DELETE FROM t_batching_comment WHERE id=$1
    // DETAIL:  parameters: $1 = '36'
    // LOG:  execute S_2: DELETE FROM t_batching_comment WHERE id=$1
    // DETAIL:  parameters: $1 = '37'
    // LOG:  execute S_3: COMMIT
    public static void testPrepareStatementDelete(Connection connection) throws SQLException {
        System.out.println(System.currentTimeMillis());
        String query = "DELETE FROM t_batching_comment WHERE id=?";
        try (PreparedStatement prepareStatement = connection.prepareStatement(query)) {
            for (int index = 200; index < 1500000; index++) {
                prepareStatement.setInt(1, index);
                prepareStatement.addBatch();
            }
            prepareStatement.executeBatch();
            // prepareStatement.executeLargeBatch();
        }
        System.out.println(System.currentTimeMillis());
    }

    // TODO. 使用Statement不会有Batched执行的效果, 不建议使用Statement
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

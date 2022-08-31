package batching;

import java.sql.*;

// TODO. JDBC has to create the prepared statement dynamically.
//  - .addBatch(sql)的数量对应batch size
//  - 通过日志Logger显示Statement中执行的SQL详细信息
public class PostgreSqlBatching {

    public static void main(String[] args) throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/my_database";
        try (Connection connection = DriverManager.getConnection(url, "postgres", "admin")) {
            connection.setAutoCommit(false);
            testStatementInsert(connection);
            connection.commit();
            connection.setAutoCommit(true);
        }
        // finally: make sure connection be closed
    }

    private static void testStatementInsert(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            for (int index = 12; index < 16; index++) {
                String sql = "INSERT INTO t_batching_comment(id, review) VALUES (" + index + ",'name review')";
                statement.addBatch(sql);
            }
            int[] result = statement.executeBatch();
        }
    }

    public static void testStatementDelete(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            connection.setAutoCommit(false);
            for (int index = 12; index < 16; index++) {
                String sql = "DELETE FROM t_batching_comment WHERE id=" + index;
                statement.addBatch(sql);
            }
            int[] result = statement.executeBatch();
        }
    }

    public static void testPrepareStatementInsert(Connection connection) throws SQLException {
        String query = "INSERT INTO t_batching_comment(id, review) values (?, ?)";
        try (PreparedStatement prepareStatement = connection.prepareStatement(query)) {
            for (int index = 60; index < 63; index++) {
                prepareStatement.setInt(1, index);
                prepareStatement.setString(2, "name test");
                prepareStatement.addBatch();
            }
            int[] result = prepareStatement.executeBatch();
            System.out.println(result[0] + ":" + result[1]);
        }
    }

    public static void testPrepareStatementDelete(Connection connection) throws SQLException {
        String query = "DELETE FROM t_batching_comment WHERE id=?";
        try (PreparedStatement prepareStatement = connection.prepareStatement(query)) {
            for (int index = 50; index < 53; index++) {
                prepareStatement.setInt(1, index);
                prepareStatement.addBatch();
            }
            // 返回每一个Query执行后的状态，全部成功则均为1
            int[] result = prepareStatement.executeBatch();
        }
    }
}

package database.design.bacthing;

import java.sql.*;

// TODO. Batching allows to send multiple statements in one-shot, saving unnecessary socket stream flushing.
//   将要执行的数据全部放到一个batch批次中执行，只请求一次数据库Server
//   一个batch一批的操作只是针对一个table，换一个table操作，则会执行新的batch
// JDBC has long been offering support for DML statement batching.
// By default, all statements are sent one after the other, each one in a separate network round-trip.
// Batching allows us to send multiple statements in one-shot, saving unnecessary socket stream flushing.
public class JdbcBatching {

    public static void main(String[] args) throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/my_database";
        String user = "postgres";
        String password = "admin";
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            testStatement(connection);
        }
        // finally: make sure connection be closed
    }

    private static void testStatement(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute("ALTER DATABASE my_database");
            statement.execute("SET log_statement = 'all'");

            connection.setAutoCommit(false);
            String SQL = "INSERT INTO t_batching_comment(id, review) VALUES (42,'name review 40')";
            statement.addBatch(SQL);

            SQL = "INSERT INTO t_batching_comment(id, review) VALUES (43,'name review 41')";
            statement.addBatch(SQL);
            int[] result = statement.executeBatch();
            System.out.println(result.length);
            connection.commit();
        }
    }

    public void testJdbcBatching(Connection connection) throws SQLException {
        String query = "INSERT INTO t_batching_comment(id, review) values (?, ?)";
        try (PreparedStatement prepareStatement = connection.prepareStatement(query)) {
            prepareStatement.setInt(1, 50);
            prepareStatement.setString(2, "name 50");
            prepareStatement.addBatch();

            prepareStatement.setInt(1, 51);
            prepareStatement.setString(2, "name 51");
            prepareStatement.addBatch();

            int[] result = prepareStatement.executeBatch();
            System.out.println(result.length);
            System.out.println(result[0] + ":" + result[1]);
        }
    }
}

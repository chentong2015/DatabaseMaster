package batching;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MySqlBatching {

    public static void main(String[] args) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/my_database";
        try (Connection connection = DriverManager.getConnection(url, "root", "admin")) {
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
}

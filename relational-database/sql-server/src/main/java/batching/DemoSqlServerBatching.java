package batching;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DemoSqlServerBatching {

    public static void main(String[] args) {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            // 这里的连接字符串中encrypt=false不能使用引号
            String url = "jdbc:sqlserver://localhost:1433;Database=test_db;Trusted_Connection=true;useBulkCopyForBatchInsert=true;encrypt=false";
            Connection connection = DriverManager.getConnection(url, "test", "TCHong20");
            connection.setAutoCommit(false);
            // testPrepareStatementDelete(connection);
            connection.commit();
            connection.setAutoCommit(true);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    // TODO. 使用BatchInsert=true属性来执行批量的插入操作
    // This connection property can be enabled to transparently use the Bulk Copy API
    // when doing batch insert operations using java.sql.PreparedStatement.
    // This feature potentially provides much higher performance when enabled.
    public static void testPrepareStatementInsert(Connection connection) throws SQLException {
        String query = "INSERT INTO t_batching_comment(id, review) values (?, ?)";
        try (PreparedStatement prepareStatement = connection.prepareStatement(query)) {
            for (int index = 1; index < 50; index++) {
                prepareStatement.setInt(1, index);
                prepareStatement.setString(2, "review name");
                prepareStatement.addBatch();
            }
            prepareStatement.executeBatch();
        }
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

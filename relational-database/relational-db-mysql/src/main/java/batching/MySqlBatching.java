package batching;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MySqlBatching {

    // TODO. 提升大量插入和删除的执行效率，显式配置批量Statement: "rewriteBatchedStatements=true"
    public static void main(String[] args) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/my_database?rewriteBatchedStatements=true";
        try (Connection connection = DriverManager.getConnection(url, "root", "admin")) {
            connection.setAutoCommit(false);
            testPrepareStatementDelete(connection);
            connection.commit();
            connection.setAutoCommit(true);
        }
        // finally: make sure connection be closed
    }

    // 同等数量级，Batched Insert执行效率远高于Delete !!
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
}

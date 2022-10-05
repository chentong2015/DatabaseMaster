package batching;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DemoSybaseBatching {

    public static void main(String[] args) {
        try {
            Class.forName("com.sybase.jdbc4.jdbc.SybDriver");
            String url = "jdbc:sybase:Tds:xxx:5000/tempdb";
            Connection connection = DriverManager.getConnection(url, "tech_user", "root123");
            connection.setAutoCommit(false);
            testPrepareStatementInsert(connection);
            connection.commit();
            connection.setAutoCommit(true);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    // TODO. 使用Inserting in Bulk可以提高批量插入的效率
    public static void testPrepareStatementInsert(Connection connection) throws SQLException {
        String query = "INSERT INTO t_batching_comment(id, review) values (?, ?)";
        try (PreparedStatement prepareStatement = connection.prepareStatement(query)) {
            for (int index = 10; index < 20; index++) {
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

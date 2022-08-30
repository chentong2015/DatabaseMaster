package database.design.bacthing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcBatching {

    // 将要执行的数据全部放到一个batch批次中执行，只请求一次数据库Server
    public void testJdbcBatching() throws SQLException {
        String query = "insert into t_user (ID, NAME) values (?, ?)";
        Connection connection = DriverManager.getConnection("connection string");
        try (PreparedStatement prepareStatement = connection.prepareStatement(query)) {
            prepareStatement.setInt(1, 1);
            prepareStatement.setString(2, "user 01");
            prepareStatement.addBatch();

            prepareStatement.setInt(1, 2);
            prepareStatement.setString(2, "user 02");
            prepareStatement.addBatch();

            int[] result = prepareStatement.executeBatch();
            // assertEquals(2, result.length);
            // assertTrue(result[0] == Statement.SUCCESS_NO_INFO || result[0] > 0);
            // assertTrue(result[1] == Statement.SUCCESS_NO_INFO || result[1] > 0);
        }
    }
}

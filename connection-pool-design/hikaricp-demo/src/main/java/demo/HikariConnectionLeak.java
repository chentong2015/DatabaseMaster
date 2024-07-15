package demo;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class HikariConnectionLeak {

    private static String query = "insert into enum_status_normalization (id, value) values (";

    // TODO. 从连接池中获取的Connection没有被关闭，造成连接的泄露
    // - 其他的线程由于等待获取连接超时而导致异常
    //
    public static void main(String[] args) throws SQLException, InterruptedException {
        DataSource dataSource = HikariConnectionPool.getDataSource(2);

        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(query + "20, 'test')")) {
            int row = statement.executeUpdate();
            System.out.println("Done Insert: " + row);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        // for (int index = 1; index < 2; index++) {
        //     int finalIndex = index;
        //     new Thread(() -> {
        //         try(Statement statement = dataSource.getConnection().createStatement()) {
        //             statement.executeUpdate(query + (10 + finalIndex) +", 'test')");
        //             System.out.println("Done Insert");
        //         } catch (SQLException exception) {
        //             exception.printStackTrace();
        //         }
        //     }).start();
        // }

        Thread.sleep(70000);
        System.out.println("Done");
    }

    // 获取连接并更新数据后，保证调用Connection.close()关闭
    public void update(int postId, String metaValue) throws SQLException {
        DataSource dataSource = HikariConnectionPool.getDataSource(2);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement("query")) {
            ps.setInt(1, postId);
            ps.setString(2, metaValue);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }
    }
}

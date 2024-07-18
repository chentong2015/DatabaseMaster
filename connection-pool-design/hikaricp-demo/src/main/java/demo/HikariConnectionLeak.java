package demo;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class HikariConnectionLeak {

    public static void main(String[] args) throws SQLException, InterruptedException {
        String query = "insert into enum_status_normalization (id, value) values (20, 'test')";
        DataSource dataSource = HikariCPHandler.getDataSource(2);

        // TODO. 从连接池中获取的Connection必须保证没有连接泄露
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {
            int row = statement.executeUpdate();
            System.out.println("Done Insert");
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        Thread.sleep(60000);
        System.out.println("Done");
    }

    // 获取连接并更新数据后，保证调用Connection.close()关闭
    public void testConnectionLeak() throws SQLException {
        DataSource dataSource = HikariCPHandler.getDataSource(2);
        String query = "insert into enum_status_normalization (id, value) values (";
        for (int index = 30; index < 40; index++) {
            int finalIndex = index ;
            new Thread(() -> {
                // Thread从CP中获取Connection之后并没有在Query完成后关闭
                // 导致其他Thread在Timeout时间断内没有办法再获取
                try(Statement statement = dataSource.getConnection().createStatement()) {
                    statement.executeUpdate(query + finalIndex +", 'test')");
                    System.out.println("Done Insert");
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }).start();
        }
    }
}

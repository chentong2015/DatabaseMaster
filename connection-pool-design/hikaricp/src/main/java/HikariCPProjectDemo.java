import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class HikariCPProjectDemo {

    public static void main(String[] args) throws SQLException, InterruptedException {
        testConnectionWaitTimeout();
        Thread.sleep(60000);
        System.out.println("Done");
    }

    // TODO. 从连接池中获取的Connection必须确保关闭
    public static void testConnectionClose() throws SQLException {
        String query = "insert into test (id, value) values (20, 'test')";
        DataSource dataSource = HikariCPHandler.getDataSource(2);
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {
            int row = statement.executeUpdate();
            System.out.println("Done Insert");
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    // TODO. 测试连接池中的连接泄露: 保证调用Connection.close()关闭
    public static void testConnectionLeak() throws SQLException {
        DataSource dataSource = HikariCPHandler.getDataSource(2);
        String query = "insert into test (id, value) values (";
        for (int index = 30; index < 40; index++) {
            int finalIndex = index;
            new Thread(() -> {
                // Thread从CP中获取Connection后并没有在Query完成后关闭
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

    // TODO. 测试Connection Timeout超时异常: 在指定时间段内连接池没有返回可用的连接
    public static void testConnectionWaitTimeout() throws SQLException {
        DataSource dataSource = HikariCPHandler.getDataSource(2);
        String query = "insert into test (id, value) values (";
        for (int index = 1; index < 10; index++) {
            int finalIndex = index;
            new Thread(() -> {
                // 由于执行Query的时间过长，超过连接的等待时间
                // 当连接池中连接的数量不足以支撑线程数目时，出现连接超时异常
                try(Connection connection = dataSource.getConnection();
                    Statement statement = connection.createStatement()) {
                    statement.executeUpdate(query + finalIndex +", 'test')");
                    Thread.sleep(40000);
                    System.out.println("Done Insert");
                } catch (SQLException | InterruptedException exception) {
                    exception.printStackTrace();
                }
            }).start();
        }
    }
}

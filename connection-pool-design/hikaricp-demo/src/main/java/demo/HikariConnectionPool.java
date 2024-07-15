package demo;

import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.pool.HikariPool;

import javax.sql.DataSource;
import java.sql.SQLException;

public class HikariConnectionPool {

    public static DataSource getDataSource(int threads) throws SQLException {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setPoolName("HCP Name");
        dataSource.setJdbcUrl("jdbc:oracle:thin:@//localhost:1555/orclcdb");
        dataSource.setUsername("fmm");
        dataSource.setPassword("hello00");

        // 线程池中的连接数量和并发的线程数量有关
        dataSource.setAutoCommit(true);
        dataSource.setMaximumPoolSize(threads * 2 + 1);
        dataSource.setMinimumIdle(threads);
        dataSource.setConnectionTimeout(30000);
        dataSource.setMaxLifetime(580000);

        // 判断从线程池中获取的连接必须是有效的
        try {
            // Returns true if the connection has not been closed and is still valid.
            dataSource.getConnection().isValid(0);
        } catch (HikariPool.PoolInitializationException ex) {
            throw new SQLException(ex.getMessage());
        }
        return dataSource;
    }
}

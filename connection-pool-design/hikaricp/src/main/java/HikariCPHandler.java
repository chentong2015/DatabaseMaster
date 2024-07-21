import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class HikariCPHandler {

    public static DataSource getDataSource(int threads) throws SQLException {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setPoolName("HCP Name");
        dataSource.setJdbcUrl("jdbc:oracle:thin:@//localhost:1555/orclcdb");
        dataSource.setUsername("fmm");
        dataSource.setPassword("hello00");

        // 设置获取连接的AutoCommit, 默认自动提交事务
        dataSource.setAutoCommit(true);

        // 线程池中的连接数量和并发的线程数量有关，默认10
        dataSource.setMaximumPoolSize(threads * 2 + 1);

        // 最少闲置状态的连接，保证连接的基数(CP将维持这个数量)，默认10
        dataSource.setMinimumIdle(threads);

        // 设置等待获取连的超时时间，在Connection不够的情况下很重要，默认30s
        dataSource.setConnectionTimeout(30000);

        // 连接的最大存活时间，超过生命周期的连接(没有正在被使用)将被移除
        dataSource.setMaxLifetime(580000);

        // 处于闲置状态的Connection的超时时间, 默认10m
        // 如果这个参数接近或者超过MaxLifetime最大生命周期，则被Disable
        dataSource.setIdleTimeout(500000);

        // TODO. 判断CP中获取的连接是否有效，注意连接泄露
        try (Connection connection = dataSource.getConnection()) {
            System.out.println("Valid:" + connection.isValid(0));
        }
        return dataSource;
    }
}
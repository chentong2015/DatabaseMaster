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
        // minIdle==maxPoolSize 将会造成Fixed size Pool固定大小的池
        dataSource.setMinimumIdle(threads);

        // 设置等待获取连的超时时间，以支持可能耗时较长的操作，默认30s
        // 当Connection数量不足时，即使提高Timeout等待时间，仍然可能造成异常 !!
        dataSource.setConnectionTimeout(60000);

        // 处于闲置状态的Connection的超时时间, 默认10m
        // 如果这个参数接近或者超过MaxLifetime最大生命周期，则被Disable
        dataSource.setIdleTimeout(500000);


        // TODO. MaxLifetime参数可能造成异常，如何模拟被DB超时 ?
        // 连接的最大存活时间，超过生命周期的连接(没有在被使用的连接)将被移除
        // it should be several seconds shorter than any database or infra imposed connection time limit.
        dataSource.setMaxLifetime(580000);

        // TODO. 监测空闲的连接是否还有效，没有被超时关闭 !
        // 尝试保持连接活动的频率，以防止数据库或网络基础设施超时
        // 此值必须小于maxLifetime值, keepalive只会发生在空闲连接上
        // 当针对给定连接的keepalive时间到达时，该连接将从池中移除，然后“ping”，然后返回到池中
        dataSource.setKeepaliveTime(50000);

        // Pool will wait for a connection to be validated as alive.
        dataSource.setValidationTimeout(3000);


        // TODO. 判断CP中获取的连接是否有效，注意连接泄露
        try (Connection connection = dataSource.getConnection()) {
            System.out.println("Valid:" + connection.isValid(0));
        }

        // 推荐使用Connection.isValid()高效验证
        // SQL query to be executed to test the validity of connections
        dataSource.setConnectionTestQuery("test select query");

        // 使用创建出来的连接执行初始化的SQL，完成DB数据的准备
        // SQL string that will be executed on all new connections when they are created
        // before they are added to the pool.
        dataSource.setConnectionInitSql("init query");

        return dataSource;
    }
}
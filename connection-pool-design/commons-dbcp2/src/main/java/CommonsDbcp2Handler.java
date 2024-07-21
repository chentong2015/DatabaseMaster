import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.time.Duration;

public class CommonsDbcp2Handler {

    // 如果使用BasicManagedDataSource，则必须配置TransactionManager
    public static DataSource getDatasource(int threads) {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUsername("fmm");
        dataSource.setPassword("hello00");
        dataSource.setUrl("jdbc:oracle:thin:@//localhost:1555/orclcdb");

        // The maximum permitted lifetime of a connection.
        // A value of zero or less indicates an infinite lifetime
        dataSource.setMaxConn(Duration.ofMinutes(30));

        // 连接池支持的最大连接总数
        dataSource.setMaxTotal(threads * 2 + 1);

        // 连接池中最少的闲置连接数
        dataSource.setMinIdle(threads);

        // The maximum Duration that the pool will wait (when there are no available connections)
        // for a connection to be returned before throwing an exception,
        // or <= 0 to wait indefinitely.
        dataSource.setMaxWait(Duration.ofSeconds(30));

       return dataSource;
    }
}

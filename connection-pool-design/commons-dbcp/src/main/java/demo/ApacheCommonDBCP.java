package demo;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbcp2.managed.BasicManagedDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;

public class ApacheCommonDBCP {

    public static void main(String[] args) throws SQLException {
        try(BasicDataSource dataSource = new BasicManagedDataSource()) {
            // 配置连接池连接到的DB信息
            dataSource.setUsername("user");
            dataSource.setPassword("password");
            dataSource.setUrl("url connection string");

            // 关于连接池的相关参数配置
            dataSource.setMaxConn(Duration.ofMinutes(30)); // 连接的最大生命周期
            dataSource.setMaxTotal(36); // 连接池支持的最大连接总数
            dataSource.setMinIdle(3);   // 连接池中最少的闲置连接数
            dataSource.setMaxWait(Duration.ofMinutes(3));

            execute(dataSource);
        }
    }

    // 从连接池中获取连接并执行Query操作
    private static void execute(DataSource dataSource) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            statement.close();
        }
    }
}

package orm.middleware.design.connection.pool.demo3;

import java.sql.SQLException;

// 连接池的工厂负责创建连接池中的对象
public interface PoolableConnectionFactory<T extends PoolableConnection> {

    T create() throws SQLException;
}

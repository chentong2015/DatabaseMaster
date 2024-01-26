package datalayer.connection.pool.demo3;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

// 连接池中具体的对象, 封装了数据库连接的connection, 实现connection的基本操作
public class MyPoolableConnectionImpl extends PoolableConnection {

    private final Connection delegate;

    public MyPoolableConnectionImpl(Connection connection) {
        this.delegate = connection;
        this.setCreationTime(System.currentTimeMillis());
    }

    public Statement createStatement() throws SQLException {
        return delegate.createStatement();
    }

    public void close() throws SQLException {
        delegate.close();
    }

    @Override
    void destroy() throws SQLException {
        if (!delegate.isClosed()) {
            delegate.close();
        }
    }
}

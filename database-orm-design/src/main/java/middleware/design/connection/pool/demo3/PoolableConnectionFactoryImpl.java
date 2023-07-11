package middleware.design.connection.pool.demo3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PoolableConnectionFactoryImpl implements PoolableConnectionFactory<MyPoolableConnectionImpl> {

    @Override
    public MyPoolableConnectionImpl create() throws SQLException {
        Connection connection = DriverManager.getConnection("url");
        return new MyPoolableConnectionImpl(connection);
    }
}

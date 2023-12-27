package middleware.connection.pool.demo2;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentLinkedQueue;

public class PooledConnections {

    // 数据库的连接池: 适用"非阻塞高性能队列"来存储每一个DB的连接
    private final ConcurrentLinkedQueue<Connection> allConnections = new ConcurrentLinkedQueue<Connection>();
    private final ConcurrentLinkedQueue<Connection> availableConnections = new ConcurrentLinkedQueue<Connection>();

    // TODO. 使用接口"抽象出"DB Connection连接的创建和验证
    // ConnectionCreator可以根据不同的数据库做对应的实现
    private final ConnectionCreator connectionCreator;
    private final ConnectionValidator connectionValidator;

    // 使用参数来配置连接池中Connection的AutoCommit状态
    private final boolean autoCommit;
    private final int minSize;
    private final int maxSize;
    private volatile boolean primed;

    private PooledConnections(Builder builder) {
        connectionCreator = builder.connectionCreator;
        connectionValidator = builder.connectionValidator == null ? ConnectionValidator.ALWAYS_VALID : builder.connectionValidator;
        autoCommit = builder.autoCommit;
        maxSize = builder.maxSize;
        minSize = builder.minSize;
        addConnections(builder.initialSize);
    }

    public void validate() {
        final int size = size();
        if (!primed && size >= minSize) {
            // primed is to allow the pool to lazily reach its defined min-size.
            System.out.println("Connection pool now considered primed; min-size will be maintained");
            primed = true;
        }
        if (size < minSize && primed) {
            int numberToBeAdded = minSize - size;
            System.out.println("Adding %s Connections to the pool");
            addConnections(numberToBeAdded);
        } else if (size > maxSize) {
            int numberToBeRemoved = size - maxSize;
            System.out.println("Removing %s Connections from the pool");
            removeConnections(numberToBeRemoved);
        }
    }

    public void add(Connection conn) {
        final Connection connection = releaseConnection(conn);
        if (connection != null) {
            availableConnections.offer(connection);
        }
    }

    protected Connection releaseConnection(Connection conn) {
        Exception t = null;
        try {
            conn.setAutoCommit(true);
            conn.clearWarnings();
            if (connectionValidator.isValid(conn)) {
                return conn;
            }
        } catch (SQLException ex) {
            t = ex;
        }
        closeConnection(conn, t);
        System.out.println("Connection release failed. Closing pooled connection");
        return null;
    }

    // 关闭一个DB Connection之后，从连接池中移除该connection
    protected void closeConnection(Connection conn, Throwable t) {
        try {
            conn.close();
        } catch (SQLException ex) {
            if (t != null) {
                // t.addSuppressed(ex);
            }
        } finally {
            allConnections.remove(conn);
        }
    }

    public void close() throws SQLException {
        try {
            int allocationCount = allConnections.size() - availableConnections.size();
            if (allocationCount > 0) {
                System.out.println("Connection leak detected: there are " + allocationCount
                        + " unclosed connections upon shutting down pool " + getUrl());
            }
        } finally {
            for (Connection connection : allConnections) {
                connection.close();
            }
        }
    }

    // poll()出队: while无限循环，直到出队一个正确的Connection
    // 设置获取connection的timeout时间
    public Connection poll() throws Exception {
        Connection conn;
        do {
            conn = availableConnections.poll();
            if (conn == null) {
                synchronized (allConnections) {
                    if (allConnections.size() < maxSize) {
                        addConnections(1);
                        return poll();
                    }
                }
                throw new Exception("The internal connection pool has reached " +
                        "its maximum size and no connection is currently available!");
            }
            conn = prepareConnection(conn);
        } while (conn == null);
        return conn;
    }

    // 准备Connection: 设置autoCommit并且验证，如果无效则将其关闭
    protected Connection prepareConnection(Connection conn) {
        Exception t = null;
        try {
            conn.setAutoCommit(autoCommit);
            if (connectionValidator.isValid(conn)) {
                return conn;
            }
        } catch (SQLException ex) {
            t = ex;
        }
        closeConnection(conn, t);
        System.out.println("Connection preparation failed. Closing pooled connection");
        return null;
    }

    public int size() {
        return availableConnections.size();
    }

    protected void removeConnections(int numberToBeRemoved) {
        for (int i = 0; i < numberToBeRemoved; i++) {
            Connection connection = availableConnections.poll();
            try {
                if (connection != null) {
                    connection.close();
                }
                allConnections.remove(connection);
            } catch (SQLException e) {
                System.out.println("unable to close connection");
            }
        }
    }

    // 创建新的连接到连接池中
    protected void addConnections(int numberOfConnections) {
        for (int i = 0; i < numberOfConnections; i++) {
            Connection connection = connectionCreator.createConnection();
            allConnections.add(connection);
            availableConnections.add(connection);
        }
    }

    public String getUrl() {
        return connectionCreator.getUrl();
    }

    // @Internal
    public void releasePooledConnections() {
        for (Connection connection : allConnections) {
            closeConnection(connection, null);
        }
    }

    // 使用builder模式来配置连接池的参数
    public static class Builder {
        private final ConnectionCreator connectionCreator;
        private ConnectionValidator connectionValidator;
        private final boolean autoCommit;
        private int initialSize = 1;
        private int minSize = 1;
        private int maxSize = 20;

        public Builder(ConnectionCreator connectionCreator, boolean autoCommit) {
            this.connectionCreator = connectionCreator;
            this.autoCommit = autoCommit;
        }

        public Builder initialSize(int initialSize) {
            this.initialSize = initialSize;
            return this;
        }

        public Builder minSize(int minSize) {
            this.minSize = minSize;
            return this;
        }

        public Builder maxSize(int maxSize) {
            this.maxSize = maxSize;
            return this;
        }

        public Builder validator(ConnectionValidator connectionValidator) {
            this.connectionValidator = connectionValidator;
            return this;
        }

        public PooledConnections build() {
            return new PooledConnections(this);
        }
    }
}

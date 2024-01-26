package datalayer.connection.pool.demo1;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

public class DBConnectionPoolManager {

    private static int countClient;
    private ArrayList dbDrivers;
    private HashMap<String, DBConnectionPool> connectionPoolMap;

    private static DBConnectionPoolManager instance;

    private DBConnectionPoolManager() {
        dbDrivers = new ArrayList();
        connectionPoolMap = new HashMap<>();
        init();
    }

    // 初始化连接池管理类的唯一实例
    private void init() {
    }

    public synchronized static DBConnectionPoolManager getInstance() {
        if (instance == null) {
            instance = new DBConnectionPoolManager();
        }
        return instance;
    }

    // 从配置文件中创建出所需要的连接池，针对不同的DB和User
    private void createConnectionPools(Properties props) {
        // load drivers
        String user = props.getProperty("db.connection.username");
        String ure = props.getProperty("db.connection.url");
        DBConnectionPool connectionPool = new DBConnectionPool("sql server 1");
        connectionPoolMap.put("sql server 1", connectionPool);
    }

    // 释放指定连接池中的一个Connection
    public void freeConnection(String name, Connection connection) {
        DBConnectionPool connectionPool = connectionPoolMap.get(name);
        connectionPool.freeConnection(connection);
    }

    // 从指定连接池中获取一个连接
    public Connection getConnection(String name) {
        return getConnection(name, 300);
    }

    public Connection getConnection(String name, long timeout) {
        DBConnectionPool connectionPool = connectionPoolMap.get(name);
        return connectionPool.getConnection(timeout);
    }

    public synchronized void release() throws SQLException {
        for (DBConnectionPool connectionPool : connectionPoolMap.values()) {
            connectionPool.release();
        }
    }
}

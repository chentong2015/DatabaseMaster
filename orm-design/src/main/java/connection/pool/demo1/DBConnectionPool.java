package datalayer.connection.pool.demo1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Timer;

public class DBConnectionPool {

    // 通过名称对应特定的线程池，针对不同的DB有不同的连接
    private final String name;
    private String url;
    private String password;
    private String user;
    public Timer timer;

    // 具体参数根据场景测试而定
    private int minConnection = 50;
    private int maxConnection = 500;
    private int countAssigned = 0;

    // 容器，空闲池，根据创建时间顺序存放已创建但尚未分配出去的连接
    private ArrayList<Connection> freeConnections;

    public DBConnectionPool(String name) {
        this.name = name;
        freeConnections = new ArrayList<>();
    }

    public void newConnection(String url, String user) throws SQLException {
        Connection connection = DriverManager.getConnection(url, user, password);
        freeConnections.add(connection);
    }

    // 返回连接给空闲池
    public synchronized void freeConnection(Connection connection) {
        freeConnections.add(connection);
    }

    // 在指定的timeout等待时间内获取一个连接
    public synchronized Connection getConnection(long timeout) {
        // to do ...
        return freeConnections.get(0);
    }

    // 释放连接池中所有DB连接，释放占用的系统资源
    public synchronized void release() throws SQLException {
        for (Connection connection : freeConnections) {
            connection.close();
        }
    }
}

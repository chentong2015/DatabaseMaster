package demo2;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class PoolState implements Runnable {

    // Protecting any lifecycle state change:
    private final ReadWriteLock statelock = new ReentrantReadWriteLock();
    private volatile boolean active = false;
    private ScheduledExecutorService executorService;

    private final PooledConnections pool;
    private final long validationInterval;

    public PoolState(PooledConnections pool, long validationInterval) {
        this.pool = pool;
        this.validationInterval = validationInterval;
    }

    private void startIfNeeded() {
        if (active) {
            return;
        }
        statelock.writeLock().lock();
        try {
            if (active) {
                return;
            }
            executorService = Executors.newSingleThreadScheduledExecutor(new ValidationThreadFactory());
            executorService.scheduleWithFixedDelay(
                    this, validationInterval, validationInterval, TimeUnit.SECONDS
            );
            active = true;
        } finally {
            statelock.writeLock().unlock();
        }
    }

    // @Override
    public void run() {
        if (active) {
            pool.validate();
        }
    }

    public void stop() {
        statelock.writeLock().lock();
        try {
            if (!active) {
                return;
            }
            System.out.println("cleaningUpConnectionPool: " + pool.getUrl());
            active = false;
            if (executorService != null) {
                executorService.shutdown();
            }
            executorService = null;
            try {
                pool.close();
            } catch (SQLException e) {
                System.out.println("unableToClosePooledConnection");
            }
        } finally {
            statelock.writeLock().unlock();
        }
    }

    public Connection getConnection() throws Exception {
        startIfNeeded();
        statelock.readLock().lock();
        try {
            return pool.poll(); // 轮询
        } finally {
            statelock.readLock().unlock();
        }
    }

    public void closeConnection(Connection conn) throws SQLException {
        if (conn == null) {
            return;
        }
        startIfNeeded();
        statelock.readLock().lock();
        try {
            pool.add(conn);
        } finally {
            statelock.readLock().unlock();
        }
    }
}

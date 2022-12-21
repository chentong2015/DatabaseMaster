package orm.middleware.design.connection.pool.demo3;

import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ConnectionPool<T extends PoolableConnection> {

    private final String poolName;
    private final Map<T, Boolean> usedObjects;
    private final BlockingQueue<T> freeObjects;

    // 考虑到多线程，使用原子增长来统计连接池的大小
    private final AtomicInteger poolSize;

    private int iPoolMaxSize = 500;
    private long lPoolTimeOut = 100;

    // 可以使用一个数据库连接的最大次数
    private int maxConnectionReuseCount = 100;
    // 可以使用一个数据库连接多久, 从连接被构建时开始计算
    private long maxConnectionReuseTime = 500;

    private volatile boolean isValid;

    // 使用定时线程池来实现一些定时任务，清除线程池中超时的线程
    private ScheduledExecutorService scheduler;

    // 使用自定义的数据库连接的工厂来创建Connection
    private final PoolableConnectionFactory<T> connectionFactory;

    public ConnectionPool(String poolName, Map<T, Boolean> usedObjects,
                          BlockingQueue<T> freeObjects, AtomicInteger poolSize,
                          PoolableConnectionFactory<T> connectionFactory) {
        this.poolName = poolName;
        this.usedObjects = usedObjects;
        this.freeObjects = freeObjects;
        this.poolSize = poolSize;
        this.connectionFactory = connectionFactory;
        setPoolConnectionScheduledTask();
    }

    // TODO. 开启线程池中Single Thread周期执行的Task
    private void setPoolConnectionScheduledTask() {
        scheduler = Executors.newSingleThreadScheduledExecutor(new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread t = Executors.defaultThreadFactory().newThread(r);
                t.setDaemon(true);
                return t;
            }
        });
        scheduler.scheduleAtFixedRate(new PoolTimeOutTask(), this.lPoolTimeOut, this.lPoolTimeOut, TimeUnit.MILLISECONDS);
    }

    // TODO. 从连接池中获取连接的对象(数据库的连接)
    // 1. 如果可使用的连接为空，并且创建的连接数没有达到最大值，则新创建一个
    // 2. 如果可用的连接存在，则从可用的队列中取，设置获取connection的最大时间timeout 
    public final T getPooledObject() throws Exception {
        T pooledObject = null;
        while ((pooledObject == null) && isValid) {
            if (freeObjects.isEmpty() && (poolSize.get() < iPoolMaxSize)) {
                try {
                    poolSize.incrementAndGet();
                    pooledObject = connectionFactory.create();
                } catch (Exception e) {
                    poolSize.decrementAndGet();
                    throw e;
                }
            } else {
                if (poolSize.get() >= iPoolMaxSize) {
                    System.out.println("Maximum number of pool objects reached");
                }
                try {
                    pooledObject = freeObjects.poll(1, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    // Ignore
                }
            }
        }

        // Pool object已经被kill，状态不再有效
        if (pooledObject == null) {
            throw new Exception("Pool Object has been killed.");
        }

        // 在获取到线程中线程时刻，需要设置线程开始使用(记录活跃时间)
        usedObjects.put(pooledObject, Boolean.TRUE);
        pooledObject.incrementUsedCount();
        return pooledObject;
    }

    // TODO. 如何释放掉一个用完的数据库连接
    // 1. 从记录的usedObjects中移除，判断使用的时间和使用的次数
    // 2. 将释放出来的连接添加到freeObjects自由使用的资源中
    public final void freePooledObject(T pooledObject) {
        if (usedObjects.remove(pooledObject) != null) {
            pooledObject.setFreeTime(System.currentTimeMillis());

            long usageTimeFromCreation = pooledObject.getFreeTime() - pooledObject.getCreationTime();
            if (maxConnectionReuseTime > 0 && usageTimeFromCreation >= maxConnectionReuseTime) {
                destroyPooledObject(pooledObject, "(removed from pool - connection time expired)");
            } else if ((maxConnectionReuseCount > 0) && (pooledObject.getUsedCount() >= maxConnectionReuseCount)) {
                destroyPooledObject(pooledObject, "(removed from pool - connection usage over count)");
            } else {
                freeObjects.add(pooledObject);
            }
        } else {
            if (isValid) {
                // 从usedObjects中释放pool object时返回null，说明其中已经不存在
                System.out.println("Pool Object already freed:" + pooledObject);
            }
        }
    }

    public final void removePooledObject(T pooledObject) {
        usedObjects.remove(pooledObject);
        freeObjects.remove(pooledObject);
        destroyPooledObject(pooledObject, "(removed from pool)");
    }

    private void destroyPooledObject(T pooledObject, String reason) {
        try {
            poolSize.decrementAndGet();
            pooledObject.destroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 一旦kill，线程池中的所有线程都不再有效
    public final void kill() {
        isValid = false;

        usedObjects.clear();
        freeObjects.clear();
        poolSize.set(0);
    }

    // TODO. 通过线程池执行周期性的Task来管理数据库连接被创建后的(未使用的时间)超时
    // 从所有空闲的freeObjects数据库连接中遍历，超时则销毁，反之保留
    private class PoolTimeOutTask implements Runnable {

        @Override
        public void run() {
            int iIterator = 0;
            long lTime = System.currentTimeMillis();
            while (!freeObjects.isEmpty() && (iIterator < freeObjects.size()) && isValid) {
                T pooledObject = freeObjects.poll();
                if (pooledObject != null) {

                    // 计算从释放到现在的时间长短，判断timeout
                    long sinceFreeTime = lTime - pooledObject.getFreeTime();
                    if ((sinceFreeTime < lPoolTimeOut) && isValid) {
                        freeObjects.add(pooledObject);
                    } else {
                        destroyPooledObject(pooledObject, "from timer (" + sinceFreeTime + ")");
                    }
                }
                iIterator++;
            }
        }
    }
}

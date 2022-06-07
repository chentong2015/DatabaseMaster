package com.orm.middleware.connection.pool;

import java.util.concurrent.ThreadFactory;

// TODO. 使用ThreadFactory来创建线程，自定义在创建线程时的配置
public class ValidationThreadFactory implements ThreadFactory {

    @Override
    public Thread newThread(Runnable runnable) {
        Thread thread = new Thread(runnable);
        thread.setDaemon(true);
        thread.setName("Hibernate Connection Pool Validation Thread");
        return thread;
    }
}

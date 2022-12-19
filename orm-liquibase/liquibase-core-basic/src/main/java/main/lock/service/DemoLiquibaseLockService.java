package main.lock.service;

import liquibase.database.Database;
import liquibase.exception.LockException;
import liquibase.lockservice.LockService;
import liquibase.lockservice.LockServiceFactory;

// Liquibase为操作提供LockService锁服务，只有获得锁才能够执行
public class DemoLiquibaseLockService {

    public void testLockService(Database database) throws LockException {
        LockService lockService = LockServiceFactory.getInstance().getLockService(database);
        lockService.waitForLock();

        // 最后确保锁能够被成功的释放掉
        try {
            lockService.releaseLock();
        } catch (LockException e) {
            e.printStackTrace();
        }
        // liquibase.resetServices();
    }
}

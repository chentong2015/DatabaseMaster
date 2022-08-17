package com.hibernate.entity.manager.batching;

import com.hibernate.entity.manager.EntityManagerHandler;
import com.hibernate.entity.manager.query.entity.Sample;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

// TODO. Hibernate支持对单个Session配置批处理的大小
// Hibernate specific JDBC batch size configuration on a per Session basis
public class SessionBatching {

    private void setSessionBatchSize() {
        EntityManager entityManager = null;
        EntityTransaction transaction = null;
        try {
            entityManager = EntityManagerHandler.getEntityManager();
            entityManager.unwrap(Session.class).setJdbcBatchSize(10);
            transaction = entityManager.getTransaction();
            transaction.begin();

            int batchSize = 25;
            for (int i = 0; i < 10000; i++) {
                // 按批次执行数据库的插入操作，避免内存溢出，减少事务的运行时间
                if (i > 0 && i % batchSize == 0) {
                    // flush a batch of inserts and release memory
                    entityManager.flush();
                    entityManager.clear();
                }
                Sample sample = new Sample(i, "name index " + i);
                entityManager.persist(sample);
            }
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction != null && transaction.isActive()) transaction.rollback();
            throw e;
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }
}

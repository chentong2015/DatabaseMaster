package batching;

import jakarta.persistence.EntityManager;

public class DemoBatchingJPA {

    private static EntityManager entityManager; // Persistence.createEntityManagerFactory("");

    // 配置指定的单个Session配置批处理的大小
    // Hibernate specific JDBC batch size configuration on a per Session basis
    // entityManager.unwrap(Session.class).setJdbcBatchSize(10);
    public static void main(String[] args) {
        try {
            entityManager.getTransaction().begin();
            int batchSize = 25;
            for (int i = 10; i < 100000; i++) {
                // 按批次执行数据库的插入操作，避免内存溢出，减少事务的运行时间
                if (i > 0 && i % batchSize == 0) {
                    // flush a batch of inserts and release memory
                    entityManager.flush();
                    entityManager.clear();
                }
                // 在循环中做Entity的持久化，保存到数据库中
                // entityManager.persist(entity);
            }
            entityManager.getTransaction().commit();
        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }
}

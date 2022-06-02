package com.hibernate.main;

import jakarta.persistence.FlushModeType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

// Hibernate Session的正确使用方式
public class DemoHibernateSession {

    public void testHibernateSession() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();

        // 执行一个事务的操作
        Session session = sessionFactory.openSession();
        // TODO. Flush从内存中"冲刷"到磁盘持久层存储
        // 设置每commit一次便会执行一次flush过程
        session.setFlushMode(FlushModeType.COMMIT);
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            // do some work ...
            tx.commit();
        } catch (Exception e) {
            // 使用transaction事务的回滚功能
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            // 确定执行完成后session会被关闭
            session.close();
        }
    }
}

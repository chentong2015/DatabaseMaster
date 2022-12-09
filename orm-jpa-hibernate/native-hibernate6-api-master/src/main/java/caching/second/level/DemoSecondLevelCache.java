package caching.second.level;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import caching.HibernateStatistics;

public class DemoSecondLevelCache {

    public static void main(String[] args) {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        HibernateStatistics.open(sessionFactory);

        Session session = sessionFactory.openSession();
        Session otherSession = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Transaction otherTransaction = otherSession.beginTransaction();

        // 首次查询执行fetch data操作，将数据缓存到一级和二级缓存中
        SecondCacheEntity entity = session.get(SecondCacheEntity.class, 1L);
        HibernateStatistics.print(1);

        // 优先从一级缓存中取，不会击中二级缓存
        entity = session.get(SecondCacheEntity.class, 1L);
        HibernateStatistics.print(2);

        // clear first level cache, so that second level cache is used
        // 当前session的一级缓存中没有数据时，则从二级缓存中查找数据
        session.evict(entity);
        entity = session.get(SecondCacheEntity.class, 1L);
        HibernateStatistics.print(3);

        // 执行一个找不到数据的查询，将不会增加load count
        entity = session.get(SecondCacheEntity.class, 2L);
        HibernateStatistics.print(4);

        // 执行能够查询到数据的查询，将会增加load count
        entity = session.get(SecondCacheEntity.class, 2L);
        HibernateStatistics.print(5);

        // Step 5: Session从共享的二级缓存中获取数据
        entity = otherSession.get(SecondCacheEntity.class, 1L);
        HibernateStatistics.print(6);

        transaction.commit();
        otherTransaction.commit();
        sessionFactory.close();
    }

    // Stats enabled = true
    // Hibernate: 
    //     select
    //         s1_0.id,
    //         s1_0.name,
    //         s1_0.salary 
    //     from
    //         t_cache_second_entity s1_0 
    //     where
    //         s1_0.id=?
    // 
    // ***** 1 *****
    // Load Count = 1
    // Second Level Hit Count = 0
    // Second Level Miss Count = 1
    // Second Level Put Count = 1
    // 
    // ***** 2 *****
    // Load Count = 1
    // Second Level Hit Count = 0
    // Second Level Miss Count = 1
    // Second Level Put Count = 1
    // 
    // ***** 3 *****
    // Load Count = 1
    // Second Level Hit Count = 1
    // Second Level Miss Count = 1
    // Second Level Put Count = 1
    // 
    // Hibernate: 
    //     select
    //         s1_0.id,
    //         s1_0.name,
    //         s1_0.salary 
    //     from
    //         t_cache_second_entity s1_0 
    //     where
    //         s1_0.id=?
    //
    // ***** 4 *****
    // Load Count = 1
    // Second Level Hit Count = 1
    // Second Level Miss Count = 2
    // Second Level Put Count = 1
    // 
    // ***** 5 *****
    // Load Count = 1
    // Second Level Hit Count = 2
    // Second Level Miss Count = 2
    // Second Level Put Count = 1


    private static void persistEntity(Session session) {
        session.getTransaction().begin();
        SecondCacheEntity cacheEntity1 = new SecondCacheEntity(7L, "first cache", 10.0);
        SecondCacheEntity cacheEntity2 = new SecondCacheEntity(8L, "third cache", 30.0);
        session.persist(cacheEntity1);
        session.persist(cacheEntity2);
        session.getTransaction().commit();
    }
}

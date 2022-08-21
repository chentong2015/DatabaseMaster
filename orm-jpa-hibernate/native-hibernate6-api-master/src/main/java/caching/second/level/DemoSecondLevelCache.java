package caching.second.level;

import caching.entity.SecondCacheEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class DemoSecondLevelCache {

    public static void main(String[] args) {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();

        Session session1 = sessionFactory.openSession();
        persistEntity(session1);
        session1.close();

        sessionFactory.close();
    }

    private static void persistEntity(Session session) {
        session.getTransaction().begin();
        SecondCacheEntity cacheEntity1 = new SecondCacheEntity(5L, "first cache", 10.0);
        SecondCacheEntity cacheEntity2 = new SecondCacheEntity(6L, "third cache", 30.0);
        session.persist(cacheEntity1);
        session.persist(cacheEntity2);
        session.getTransaction().commit();
    }
}

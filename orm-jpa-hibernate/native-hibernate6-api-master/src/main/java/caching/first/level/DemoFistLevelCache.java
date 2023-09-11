package caching.first.level;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

// Hibernate的Session(一级缓存)中的对象属于持久态, 需要及时关闭或flush一级缓存, 否则可能造成OOM
public class DemoFistLevelCache {

    public static void main(String[] args) throws InterruptedException {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();

        Session session1 = sessionFactory.openSession();
        persistEntity(session1);
        CacheEntity cacheEntity1 = session1.get(CacheEntity.class, 3L);
        System.out.println(cacheEntity1);

        // 测试在一定的时间后缓存是否消失
        // 如果在sleep期间更改数据库的数据，更新的数据不会update到session1 cache缓存中，优先从缓存中查询 !!
        // 如果在同一个session中做update object，则缓存同步变化
        Thread.sleep(20000);
        CacheEntity cacheEntity2 = session1.get(CacheEntity.class, 4L);
        System.out.println(cacheEntity2);

        // 测试删除缓存中single object
        session1.evict(cacheEntity2);
        System.out.println(session1.contains(cacheEntity1)); // true
        System.out.println(session1.contains(cacheEntity2)); // false

        // 清除session中的所有缓存，之后获取object必须从数据库中获取
        session1.clear();
        session1.close();

        // TODO. New Session始终是从DB中查询获取最新的数据
        // 测试新的session是否需要发送Session到数据库
        // select
        //      c1_0.id,
        //      c1_0.name
        //  from
        //      t_cache_entity c1_0
        //  where
        //      c1_0.id=?
        Session session2 = sessionFactory.openSession();
        CacheEntity cacheEntity3 = session2.get(CacheEntity.class, 3L);
        System.out.println(cacheEntity3);
        session2.close();

        sessionFactory.close();
    }

    private static void persistEntity(Session session) {
        session.getTransaction().begin();
        CacheEntity cache1 = new CacheEntity(3L, "cache 1");
        CacheEntity cache2 = new CacheEntity(4L, "cache 2");
        session.persist(cache1);
        session.persist(cache2);
        session.getTransaction().commit();
    }
}

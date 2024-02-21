package caching.query_cache;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;

public class DemoQueryCacheEntity {

    private static StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
    private static SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();

    public static void main(String[] args) {
        Session session = sessionFactory.openSession();
        String hql = "FROM QueryCacheEntity q where q.name = :name";
        List<QueryCacheEntity> queryCacheEntities = session.createQuery(hql, QueryCacheEntity.class)
                .setParameter("name", "ok")
                .setCacheable(true)
                .list();
        for (QueryCacheEntity entity : queryCacheEntities) {
            System.out.println(entity);
        }

        // 第二次执行相同的Query, 会直接从Query Cache Result中找结果, 不会将请求发送到数据库
        List<QueryCacheEntity> queryCacheEntities1 = session.createQuery(hql, QueryCacheEntity.class)
                .setParameter("name", "ok")
                .setCacheable(true)
                .list();
        for (QueryCacheEntity entity : queryCacheEntities1) {
            System.out.println(entity);
        }
        session.close();
        sessionFactory.close();
    }
}

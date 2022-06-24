package jpa.api;

import jpa.api.query.HqlRawQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

import java.util.List;

// TODO. Hibernate Jpa annotation Mapping映射问题
// 1. @Entity() 没有设置名称，使用默认类型名称或者全路径都能查询 !!
// 2. @Entity(name = "entity-name") 如果设置名称为全路径，则必须使用全路径来查询 !!
public class HibernateJpaEntityDemo {

    static StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
    static SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();

    public static void main(String[] args) {
        Session session = sessionFactory.openSession();
        HqlRawQuery.testHqlQuery(session);
        // testSqlQuery(session);
        session.close();
        sessionFactory.close();
    }

  
    // 测试不同的Entity注解的标注方式，也能查询到数据
    private void testGetSampleData() {
        Session session = sessionFactory.openSession();
        Query<Sample> query = session.createQuery("from Sample", Sample.class);
        List<Sample> samples = query.getResultList();
        for (Sample sample : samples) {
            System.out.println(sample);
        }
        session.close();
    }
}



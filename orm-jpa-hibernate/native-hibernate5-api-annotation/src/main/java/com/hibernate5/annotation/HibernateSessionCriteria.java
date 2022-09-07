package com.hibernate5.annotation;

import com.hibernate5.annotation.entity.Person;
import org.hibernate.*;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.criterion.Order;

import java.util.List;

public class HibernateSessionCriteria {

    static StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
    static SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();

    // TODO. 使用Criteria来完配置和进行查询操作
    public static void main(String[] args) {
        Session session = sessionFactory.openSession();
        Criteria criteria = getCriteria(session, Person.class);
        List<Person> personList = criteria.list();
        for (Person person : personList) {
            System.out.println(person);
        }
        session.close();
        sessionFactory.close();
    }

    // TODO. 设置Session在执行数据库操作时的相关配置
    // 等效于hibernate.cfg.xml中配置，在Java硬编码中设置属性
    private static Criteria getCriteria(Session session, Class<?> fromClass) {
        Criteria criteria = session.createCriteria(fromClass);
        criteria.setLockMode(LockMode.OPTIMISTIC);
        criteria.addOrder(Order.asc("firstname"));
        criteria.setFlushMode(FlushMode.COMMIT);
        criteria.setFetchSize(10);
        // criteria.setMaxResults(1);
        // criteria.setProjection(Projections.rowCount())
        // criteria.uniqueResult();
        return criteria;
    }
}

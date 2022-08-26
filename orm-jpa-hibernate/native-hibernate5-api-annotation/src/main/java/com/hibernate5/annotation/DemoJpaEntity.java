package com.hibernate5.annotation;

import com.hibernate5.annotation.inheritance.table.per.clazz.SuperClassEntity;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

import java.util.List;


public class DemoJpaEntity {

    static StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
    static SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();

    public static void main(String[] args) {
        Session session = sessionFactory.openSession();
        session.setHibernateFlushMode(FlushMode.ALWAYS);

        String hqlQuery = "From " + SuperClassEntity.class.getSimpleName();
        Query<SuperClassEntity> query = session.createQuery(hqlQuery, SuperClassEntity.class);
        List<SuperClassEntity> resultList = query.getResultList();
        for (SuperClassEntity entity : resultList) {
            System.out.println(entity);
        }

        session.close();
        sessionFactory.close();
    }
}
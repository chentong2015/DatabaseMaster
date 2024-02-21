package com.hibernate5.annotation.inheritance.joined;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class DemoJoinedSubClass {

    static StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
    static SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();

    public static void main(String[] args) {
        Session session = sessionFactory.openSession();
        session.getTransaction().begin();

        JoinedSubClass joinedSubClass = new JoinedSubClass();
        joinedSubClass.setId(1);
        joinedSubClass.setName("super name");
        joinedSubClass.setSubName("sub name");
        session.persist(joinedSubClass);
        
        session.getTransaction().commit();
        session.close();
    }
}

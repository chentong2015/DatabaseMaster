package com.hibernate5.annotation.any;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class DemoAnyMapping {

    static StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
    static SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();

    public static void main(String[] args) {
        Session session = sessionFactory.openSession();
        PropertyHolder propertyHolder = session.get(PropertyHolder.class, 1L);
        System.out.println(propertyHolder.getProperty().getName());
        System.out.println(propertyHolder.getProperty().getValue());
        session.close();
    }
}

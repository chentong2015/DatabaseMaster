package com.hibernate5.testing.component;

import com.hibernate5.testing.component.collections.Developer;
import com.hibernate5.testing.component.collections.Name;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.HashSet;
import java.util.Set;

public class ComponentMappingDemo {

    static StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
    static SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();

    public static void main(String[] args) {
        try (Session session = sessionFactory.openSession()) {
            initData(session);
            getData(session);
        }
    }

    private static void initData(Session session) {
        session.getTransaction().begin();
        Name name1 = new Name('a', "first-1", "last-1");
        Name name2 = new Name('b', "first-2", "last-2");
        Name name3 = new Name('c', "first-3", "last-3");
        Set<Name> nameSet = new HashSet<>();
        nameSet.add(name1);
        nameSet.add(name2);
        nameSet.add(name3);
        Developer developer = new Developer(1L, "key", nameSet);
        session.persist(developer);
        session.getTransaction().commit();
    }

    private static void getData(Session session) {
        Developer developer = session.get(Developer.class, 1L);
        System.out.println(developer);
    }
}

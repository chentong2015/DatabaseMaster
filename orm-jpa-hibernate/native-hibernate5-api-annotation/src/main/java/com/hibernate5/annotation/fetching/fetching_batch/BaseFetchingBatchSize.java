package com.hibernate5.annotation.fetching.fetching_batch;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class BaseFetchingBatchSize {

    static StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
    static SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();

    public static void main(String[] args) {
        Session session = sessionFactory.openSession();
        Department department = session.get(Department.class, 1L);
        for (Employee employee : department.getEmployees()) {
            System.out.println(employee);
        }
        session.close();
        sessionFactory.close();
    }
}
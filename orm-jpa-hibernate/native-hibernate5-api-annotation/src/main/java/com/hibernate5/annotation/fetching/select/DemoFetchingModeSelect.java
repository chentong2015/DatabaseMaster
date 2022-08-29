package com.hibernate5.annotation.fetching.select;

import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.HashSet;
import java.util.Set;

public class DemoFetchingModeSelect {

    static StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
    static SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();

    public static void main(String[] args) {
        Session session = sessionFactory.openSession();
        session.setHibernateFlushMode(FlushMode.ALWAYS);

        // 为什么会抛出异常
        // Exception in thread "main" java.lang.StackOverflowError
        Customer customer = session.get(Customer.class, 1L);
        System.out.println(customer);

        // for (Order order : customer.getOrders()) {
        //     System.out.println(order);
        // }
        session.close();
        sessionFactory.close();
    }

    private static void initData(Session session) {
        session.getTransaction().begin();

        Customer customer1 = new Customer(1L);
        Customer customer2 = new Customer(2L);
        Set<Order> orders = new HashSet<>();
        for (long i = 1; i < 25; i++) {
            Order order = new Order(i, "order " + i, customer1);
            session.persist(order);
            orders.add(order);
        }
        customer1.setOrders(orders);
        session.persist(customer1);
        session.persist(customer2);

        session.getTransaction().commit();
    }
}
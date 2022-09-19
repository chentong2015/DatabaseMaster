package com.hibernate5.annotation.fetching.select;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.HashSet;
import java.util.Set;

// Hibernate:
//    select
//        customer0_.id as id1_9_0_
//    from
//        t_fetching_customer customer0_
//    where
//        customer0_.id=?
//
// Hibernate:
//    select
//        orders0_.customer_id as customer3_10_0_,
//        orders0_.id as id1_10_0_,
//        orders0_.id as id1_10_1_,
//        orders0_.name as name2_10_1_
//    from
//        t_fetching_order orders0_
//    where
//        orders0_.customer_id=?
public class BaseFetchingModeSelect {

    static StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
    static SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();

    public static void main(String[] args) {
        Session session = sessionFactory.openSession();
        Customer customer = session.get(Customer.class, 1L);
        System.out.println(customer);
        for (Order order : customer.getOrders()) {
            System.out.println(order);
        }
        session.close();
        sessionFactory.close();
    }

    private static void initData(Session session) {
        session.getTransaction().begin();

        Customer customer1 = new Customer(1L, "name 1");
        Customer customer2 = new Customer(2L, "test name");
        Set<Order> orders = new HashSet<>();
        for (long i = 1; i < 25; i++) {
            Order order = new Order(i, "order " + i);
            session.persist(order);
            orders.add(order);
        }
        customer1.setOrders(orders);
        session.persist(customer1);
        session.persist(customer2);

        session.getTransaction().commit();
    }
}
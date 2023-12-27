package com.hibernate5.annotation.fetching.join;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.HashSet;
import java.util.Set;

// Hibernate:
//    select
//        customer0_.id as id1_12_0_,
//        orders1_.customer_id as customer3_13_1_,
//        orders1_.id as id1_13_1_,
//        orders1_.id as id1_13_2_,
//        orders1_.name as name2_13_2_
//    from
//        t_fetching_join_customer customer0_
//    left outer join
//        t_fetching_join_order orders1_
//            on customer0_.id=orders1_.customer_id
//    where
//        customer0_.id=?
public class BaseFetchingModeJoin {

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
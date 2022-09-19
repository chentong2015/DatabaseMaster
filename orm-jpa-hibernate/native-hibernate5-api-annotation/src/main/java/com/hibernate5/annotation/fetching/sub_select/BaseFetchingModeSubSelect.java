package com.hibernate5.annotation.fetching.sub_select;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

// Hibernate:
//    select
//        customer0_.id as id1_15_,
//        customer0_.name as name2_15_
//    from
//        t_fetching_sub_select_customer customer0_
//    where
//        customer0_.name like 'name%'
//
// Hibernate:
//    select
//        orders0_.customer_id as customer3_16_1_,
//        orders0_.id as id1_16_1_,
//        orders0_.id as id1_16_0_,
//        orders0_.name as name2_16_0_
//    from
//        t_fetching_sub_select_order orders0_
//    where
//        orders0_.customer_id in (
//            select
//                customer0_.id
//            from
//                t_fetching_sub_select_customer customer0_
//            where
//                customer0_.name like 'name%'
//        )
public class BaseFetchingModeSubSelect {

    static StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
    static SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();

    public static void main(String[] args) {
        Session session = sessionFactory.openSession();
        String hql = "select c from " + Customer.class.getName() + " c where c.name like 'name%'";
        List<Customer> customerList = session.createQuery(hql).getResultList();
        System.out.println(customerList.size());
        for (Order order : customerList.get(0).getOrders()) {
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
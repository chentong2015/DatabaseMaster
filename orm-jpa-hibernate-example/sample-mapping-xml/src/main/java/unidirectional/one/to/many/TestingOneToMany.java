package unidirectional.one.to.many;

import org.hibernate.Session;
import util.HibernateSessionUtil;

import java.util.HashSet;
import java.util.Set;

public class TestingOneToMany {

    public static void main(String[] args) {
        Session session = HibernateSessionUtil.getSession();
        getData(session);
        HibernateSessionUtil.closeSession();
    }

    private static void initData(Session session) {
        session.getTransaction().begin();

        Customer customer1 = new Customer("name 1");
        Customer customer2 = new Customer("name 2");
        Set<Order> orders = new HashSet<>();
        for (long i = 1; i < 25; i++) {
            Order order = new Order("order name " + i);
            session.persist(order);
            orders.add(order);
        }
        customer1.setOrders(orders);
        session.persist(customer1);
        session.persist(customer2);

        session.getTransaction().commit();
    }

    private static void getData(Session session) {
        Customer customer = session.get(Customer.class, 1L);
        System.out.println(customer);
        for (Order order : customer.getOrders()) {
            System.out.println(order);
        }
    }
}
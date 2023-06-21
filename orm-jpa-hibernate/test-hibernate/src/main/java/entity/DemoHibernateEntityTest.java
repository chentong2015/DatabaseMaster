package entity;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class DemoHibernateEntityTest {

    static StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
    static SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();

    public static void main(String[] args) {
        Session session = sessionFactory.openSession();
        session.getTransaction().begin();
        TradeEntity tradeEntity1 = new TradeEntity(1L, 10L);
        session.persist(tradeEntity1);

        TradeEntity tradeEntity2 = new TradeEntity(2L, 20L);
        session.persist(tradeEntity2);
        session.getTransaction().commit();

        // HQL的查询可以直接选择属性进行查询
        String fullQuery = "select distinct TRADE.reference" + " FROM " + TradeEntity.class.getName() + " TRADE" +
                " WHERE counterpartyReference = 10";

        long result = session.createQuery(fullQuery, Long.class).getSingleResult();
        System.out.println(result);

        sessionFactory.close();
    }
}

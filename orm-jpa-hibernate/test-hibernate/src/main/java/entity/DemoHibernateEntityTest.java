package entity;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;

public class DemoHibernateEntityTest {

    static StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
    static SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();

    public static void main(String[] args) {
        Session session = sessionFactory.openSession();
        TradeEntity tradeEntity = new TradeEntity();
        
        // session.getTransaction().begin();
        // session.persist(new TradeEntity());
        // session.getTransaction().commit();

        // HQL的查询可以直接选择属性进行查询" WHERE counterpartyReference = 10"
        String fullQuery = "select distinct TRADE.reference" + " FROM " + TradeEntity.class.getCanonicalName() + " TRADE";

        List<Long> result = session.createQuery(fullQuery, Long.class).getResultList();
        System.out.println(result.size());

        sessionFactory.close();
    }
}

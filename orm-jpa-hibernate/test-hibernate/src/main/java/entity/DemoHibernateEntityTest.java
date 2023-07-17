package entity;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;

public class DemoHibernateEntityTest {

    public static void main(String[] args) {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        SessionFactory sessionFactory = new MetadataSources(registry)
                .addAnnotatedClass(TradeEntity.class)
                .buildMetadata()
                .buildSessionFactory();
        Session session = sessionFactory.openSession();

        // session.getTransaction().begin();
        // session.persist(new TradeEntity(1l, 1l));
        // session.getTransaction().commit();

        String fullQuery = "FROM " + TradeEntity.class.getCanonicalName();
        List<TradeEntity> result = session.createQuery(fullQuery, TradeEntity.class).getResultList();
        System.out.println(result.size());

        sessionFactory.close();
    }
}

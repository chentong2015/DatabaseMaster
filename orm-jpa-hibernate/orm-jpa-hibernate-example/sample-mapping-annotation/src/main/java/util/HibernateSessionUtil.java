package util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateSessionUtil {

    private static StandardServiceRegistry registry;
    private static SessionFactory sessionFactory;
    private static ThreadLocal<Session> threadLocalSession = new ThreadLocal<>();

    static {
        try {
            initSessionFactory();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private HibernateSessionUtil() {
    }

    public static Session getSession() {
        Session session = threadLocalSession.get();
        if (session == null || !session.isOpen()) {
            if (sessionFactory == null) {
                initSessionFactory();
            }
            session = (sessionFactory == null) ? null : sessionFactory.openSession();
            threadLocalSession.set(session);
        }
        return session;
    }

    private static void initSessionFactory() {
        registry = new StandardServiceRegistryBuilder().configure().build();
        sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }

    public static void closeSession() {
        Session session = threadLocalSession.get();
        threadLocalSession.remove();
        if (session != null && session.isOpen()) {
            session.close();
        }
    }
}

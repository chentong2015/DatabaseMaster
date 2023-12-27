package hibernate.framework.apis.session;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateSessionUtil {

    private static StandardServiceRegistry registry;
    private static SessionFactory sessionFactory;

    // TODO. 使用ThreadLocal限制Session只在一个线程中共享
    private static ThreadLocal<Session> threadLocalSession = new ThreadLocal<>();

    // 在类加载的时候完成初始，无需创建类型的实例对象
    // 使用try-catch在加载出错的情况下，判断异常原因
    static {
        try {
            initSessionFactory();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private HibernateSessionUtil() {
    }

    // TODO. 获取时判断初始化SessionFactory是否成功，以及再次初始化是否成功 !!
    public static Session getSession() {
        Session session = threadLocalSession.get();
        if (session == null || !session.isOpen()) {
            if (sessionFactory == null) {
                initSessionFactory();
            }
            // 再次判断创建出来的sessionFactory是否为空
            session = (sessionFactory == null) ? null : sessionFactory.openSession();
            threadLocalSession.set(session);
        }
        return session;
    }

    private static void initSessionFactory() {
        registry = new StandardServiceRegistryBuilder().configure().build();
        sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }

    // TODO. 使用工具类来统一管理线程的Session的关闭
    // 只有在session非空并且已经开启的情况，才需要关闭
    public static void closeSession() {
        Session session = threadLocalSession.get();
        threadLocalSession.remove();
        if (session != null && session.isOpen()) {
            session.close();
        }
    }
}

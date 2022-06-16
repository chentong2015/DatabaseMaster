package hibernate.framework.apis;

import hibernate.framework.apis.datamodel.Book;
import hibernate.framework.apis.query.HqlQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

// Native Hibernate APIs(功能较多)
public class DemoNativeHibernateAPI {

    // this.configure("hibernate.cfg.xml")
    // 默认加载指定的配置文件，可以修改加载的文件和路径
    static StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
    // 从元信息中获取Session工厂
    static SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();

    public static void main(String[] args) {
        testPersistObject();
        HqlQuery.testGetQuery(sessionFactory);
        sessionFactory.close();
        // The registry would be destroyed by the SessionFactory,
        // Destroy it manually when we have trouble building the SessionFactory
        StandardServiceRegistryBuilder.destroy(registry);
    }

    // Hibernate Session: 完成数据库的增删查改的操作
    private static void testPersistObject() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Book newBook = new Book();
            newBook.setName("Java");
            newBook.setTitle("The master java title");
            session.persist(newBook);
            transaction.commit();
        } catch (Exception exception) {
            // 这异常情况下，需要执行事务的回滚
            // transaction.rollback();
        }
    }

    // TODO. Hibernate操作对象:
    // 1. 在查询的时候，传递的主键id参数; 查询时必须
    // 2.
    private void testGetObject(Session session, Object object) {
        session.get(Book.class, 1);

        session.getTransaction().begin();
        session.remove(object);
        session.getTransaction().commit();
    }

    // 使用named parameters(:namedParam)具名参数来传递参数值
    private static void testUpdateQuery() {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            String sqlUpdate = "Update Book book set book.name = :newName where book.id = 2";
            int rowAffected = session.createMutationQuery(sqlUpdate)
                    .setParameter("newName", "java new name")
                    .executeUpdate();
            tx.commit();
        }
    }

    // 使用Hibernate Native Query原始的查询方式
    // session.createNativeQuery("SELECT * FROM t_book").addEntity(Book.class)
    private static void testDeleteQuery() {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            String sqlUpdate = "delete Book book where book.id = 3";
            int rowAffected = session.createMutationQuery(sqlUpdate).executeUpdate();
            tx.commit();
        }
    }
}

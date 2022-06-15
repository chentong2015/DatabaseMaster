package hibernate.framework.apis;

import hibernate.framework.apis.entity.Book;
import hibernate.framework.apis.query.HqlQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

// Hibernate(JPA Implementation) 一种JPA标准的实现
// 1. Mapping from Java classes to database tables(from Java data types to SQL data types)
//   将关系型数据库中的行数据映射成一个对象("数据表行" <--> "实体类Entity")
// 2. Provides data query and retrieval facilities.

// Native Hibernate APIs(功能较多): Session, SessionFactory, Transactions,,,
// https://docs.jboss.org/hibernate/orm/5.6/userguide/html_single/Hibernate_User_Guide.html
// https://blog.csdn.net/tengxing007/article/details/50903167 注解版关联关系映射
public class DemoNativeHibernateAPI {

    static StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
    static SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();

    public static void main(String[] args) {
        testPersistObject();
        HqlQuery.testGetQuery(sessionFactory);
        sessionFactory.close();
        // The registry would be destroyed by the SessionFactory,
        // Destroy it manually when we have trouble building the SessionFactory
        StandardServiceRegistryBuilder.destroy(registry);
    }

    // 手动开启事务，执行数据库的插入操作: 一个Session结束之后，需要关闭执行的操作
    private static void testPersistObject() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Book newBook = new Book();
            newBook.setName("Java");
            newBook.setTitle("The master java title");
            session.persist(newBook);
            transaction.commit();
        }
    }

    // TODO. Hibernate操作的应该是对象
    private void testDeleteEntity(Object object) {
        Session session = new Configuration().configure().buildSessionFactory().openSession();
        session.getTransaction().begin();
        // 直接传递实体对象完成数据库操作，无需查询语句
        session.remove(object);
        session.getTransaction().commit();
        session.close();
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

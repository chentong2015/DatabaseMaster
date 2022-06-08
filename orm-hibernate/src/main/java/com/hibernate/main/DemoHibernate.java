package com.hibernate.main;

import com.hibernate.main.model.Book;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

import java.util.List;

// ORM Hibernate: JPA Implementation 两层架构
// 1. Native Hibernate APIs(功能较多): Session, SessionFactory, Transactions,,,
// 2. Hibernate JPA   API  (功能较少): EntityManagerFactory, EntityManager,,,

// TODO: https://docs.jboss.org/hibernate/orm/5.4/quickstart/html_single/
// 1. 将关系型数据库中的行数据映射成一个对象("数据表行" <--> "实体类")
// 2. 通过@Query("query")自定义SQL语句嵌入到源代码，更新则需要重新编译，不适合于优化SQL的场景
public class DemoHibernate {

    static StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
    static SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();

    public static void main(String[] args) {
        testPersistObject();
        // testUpdateQuery();
        // testDeleteQuery();
        testGetQuery();
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

    // TODO. HQL: hibernate query language, 一种类似于sql的简化的查询语言, 不是SQL语言
    // "Book": DB中的Schema表格 ==> 这里必须使用标记了@Entity类型的名称，对应到DB中的表格
    // "form Book": 提取Book表的所有的信息，并映射成指定类型的对象
    private static void testGetQuery() {
        try (Session session = sessionFactory.openSession()) {
            Query<Book> query = session.createQuery("from Book", Book.class);
            List<Book> books = query.getResultList();
            for (Book book : books) {
                System.out.println(book.getId() + " - " + book.getName() + " - " + book.getTitle());
            }
        }
    }

    private static void testUpdateQuery() {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            String sqlUpdate = "update Book book set book.name = :newName where book.id = 2";
            int rowAffected = session.createMutationQuery(sqlUpdate)
                    .setParameter("newName", "java new name")
                    .executeUpdate();
            System.out.println(rowAffected);
            tx.commit();
        }
    }

    private static void testDeleteQuery() {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            String sqlUpdate = "delete Book book where book.id = 3";
            int rowAffected = session.createMutationQuery(sqlUpdate).executeUpdate();
            System.out.println(rowAffected);
            tx.commit();
        }
    }
}

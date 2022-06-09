package com.hibernate6.main;

import com.hibernate6.main.entity.Book;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

import java.util.List;

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
    // "Book": @Entity标记的name名称，或者使用类型作为名称 !!
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

    // 使用named parameters(:namedParam)具名参数来传递参数值
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

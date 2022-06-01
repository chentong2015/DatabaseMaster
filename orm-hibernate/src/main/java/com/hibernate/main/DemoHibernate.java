package com.hibernate.main;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class DemoHibernate {

    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        try {
            SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session sessionAttribute = sessionFactory.openSession();
            testSessionQuery(sessionAttribute);
            sessionAttribute.close();
            sessionFactory.close();
        } catch (Exception e) {
            // The registry would be destroyed by the SessionFactory,
            // Destroy it manually when we have trouble building the SessionFactory
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    // HQL: hibernate query language 一种Hibernate的语言, 一种类似于sql的简化的查询语言
    private static void testSessionQuery(Session sessionAttribute) {
        // Book: DB中的Schema表格
        // form Book: 提取Book表的所有的信息，并映射成指定类型的对象
        // Query<Book> query = sessionAttribute.createQuery("from Book", Book.class);
        // List<Book> books = query.getResultList();

        // 手动开启事务，执行数据库的插入操作
        // sessionAttribute.beginTransaction();
        // Book newBook = new Book();
        // newBook.setName("Test");
        // sessionAttribute.save(newBook); 存储新的对象到数据库表
        // sessionAttribute.getTransaction().commit();
    }
}

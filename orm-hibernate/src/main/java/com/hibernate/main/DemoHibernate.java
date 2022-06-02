package com.hibernate.main;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

// Java Persistence API (JPA)
// ORM Hibernate: JPA Implementation 两层架构
//   1. Native Hibernate APIs(功能较多): Session, SessionFactory, Transactions,,,
//   2. Hibernate JPA   API  (功能较少): EntityManagerFactory, EntityManager,,,

// TODO: https://docs.jboss.org/hibernate/orm/5.4/quickstart/html_single/
// 1. 将关系型数据库中的行数据映射成一个对象("数据表行" <--> "实体类")
// 2. 通过@Query("query")自定义SQL语句嵌入到源代码，更新则需要重新编译，不适合于优化SQL的场景
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
        sessionAttribute.beginTransaction();
        // Book newBook = new Book();
        // newBook.setName("Test");
        // sessionAttribute.save(newBook); 存储新的对象到数据库表
        sessionAttribute.getTransaction().commit();
    }
}

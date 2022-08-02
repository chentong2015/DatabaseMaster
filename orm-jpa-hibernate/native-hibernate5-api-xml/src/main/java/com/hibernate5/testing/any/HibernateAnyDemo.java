package com.hibernate5.testing.any;

import com.hibernate5.testing.any.model2.ClassModel2;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateAnyDemo {

    static StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
    static SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();

    public static void main(String[] args) {
        Session session = sessionFactory.openSession();
        testSaveObjects(session);
        session.close();
        sessionFactory.close();
    }

    // 如果存储的持久化对象自定义了主键id的值，则不能配置xml mapping中id的自增策略
    private static void testSaveObjects(Session session) {
        session.getTransaction().begin();
        ClassModel2 classModel2 = new ClassModel2(2.0, "name model 2");
        session.persist(classModel2);
        MyAnyClass myAnyClass = new MyAnyClass(2, "name new", classModel2);
        session.persist(myAnyClass);
        session.getTransaction().commit();
    }
}

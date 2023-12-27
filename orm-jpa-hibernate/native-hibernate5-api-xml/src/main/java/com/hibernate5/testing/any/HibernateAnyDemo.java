package com.hibernate5.testing.any;

import com.hibernate5.testing.any.model2.ClassModel2;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateAnyDemo {

    static StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
    static SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();

    // TODO. Hibernate 5.6.x版本及以上会判断查询的一行数据是否满足外键id的约束
    //  如果row行中有指向的表的id主键值不存在，则会报错
    public static void main(String[] args) {
        Session session = sessionFactory.openSession();
        String hql = "FROM " + MyAnyClass.class.getName() + " where id = 3";
        MyAnyClass anyClass = session.createQuery(hql, MyAnyClass.class).getSingleResult();
        System.out.println(anyClass.getName());

        session.close();
        sessionFactory.close();
    }

    // 如果存储的持久化对象自定义了主键id的值，则不能配置xml mapping中id的自增策略
    private static void testSaveObjects(Session session) {
        session.getTransaction().begin();
        ClassModel2 classModel2 = new ClassModel2(3.0, "name model 2");
        session.persist(classModel2);

        MyAnyClass myAnyClass = new MyAnyClass(3, "name new", classModel2);
        session.persist(myAnyClass);
        session.getTransaction().commit();
    }
}

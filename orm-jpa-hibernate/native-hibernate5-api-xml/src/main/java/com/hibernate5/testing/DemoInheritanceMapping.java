package com.hibernate5.testing;

import com.hibernate5.testing.subclass.AbstractSuperClass;
import com.hibernate5.testing.subclass.SuperClass;
import com.hibernate5.testing.subclass.SuperClass2;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

import java.util.List;

public class DemoInheritanceMapping {

    static StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
    static SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();

    public static void main(String[] args) {
        testTablePerClassHierarchy();
        // testTablePerSubclassAndPerConcreteClass();
        // testGetObjectsFromJoinedTable();
    }

    // TODO. 1. <subclass> 整个继承链只有一个数据表
    public static void testTablePerClassHierarchy() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        AbstractSuperClass superClass1 = new com.hibernate5.testing.package1.SubClass();
        superClass1.setReference(12.0);
        AbstractSuperClass superClass2 = new com.hibernate5.testing.package1.SubClass();
        superClass2.setReference(22.0);
        AbstractSuperClass superClass3 = new com.hibernate5.testing.package2.SubClass();
        superClass3.setReference(32.0);

        session.persist(superClass1);
        session.persist(superClass2);
        session.persist(superClass3);

        transaction.commit();
        session.close();
    }

    // TODO. 2. <joined-subclass> 根据继承链创建多个表
    // 在存储SubSuperClass继承类的实例对象时，会同时在SuperClass上存储指定字段的数据 !!

    // TODO. 3. <union-subclass>
    // 会在子表上存储全部的信息，不需要再级联查询
    public static void testTablePerSubclassAndPerConcreteClass() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        SuperClass superClass = new SuperClass();
        superClass.setReference(11.0);
        superClass.setName("super class");
        com.hibernate5.testing.package1.SubSuperClass subSuperClass = new com.hibernate5.testing.package1.SubSuperClass();
        subSuperClass.setReference(21.0);
        subSuperClass.setName("sub super class 1");
        subSuperClass.setSubName1("sub name 1");
        session.persist(superClass);
        session.persist(subSuperClass);

        SuperClass2 superClass2 = new SuperClass2();
        superClass2.setReference(11.0);
        superClass2.setName("super class 2");
        com.hibernate5.testing.package2.SubSuperClass subSuperClass2 = new com.hibernate5.testing.package2.SubSuperClass();
        subSuperClass2.setReference(21.0);
        subSuperClass2.setName("sub super class 2");
        subSuperClass2.setSubName2("sub name 2");
        session.persist(superClass2);
        session.persist(subSuperClass2);

        transaction.commit();
        session.close();
    }

    // 测试从joined-class table中获取对象，包含母类和子类的属性值
    public static void testGetObjectsFromJoinedTable() {
        Session session = sessionFactory.openSession();
        com.hibernate5.testing.package1.SubSuperClass subSuperClass =
                session.get(com.hibernate5.testing.package1.SubSuperClass.class, 20.0);
        System.out.println(subSuperClass.getName() + "-" + subSuperClass.getSubName1());

        // 测试使用HQL来获取:
        // String hqlQuery = "From com.hibernate5.testing.package1.SubSuperClass";
        String hqlQuery = "From SubSuperClass";
        System.out.println(hqlQuery);
        Query query = session.createQuery(hqlQuery, com.hibernate5.testing.package1.SubSuperClass.class);
        List<com.hibernate5.testing.package1.SubSuperClass> superClass2 = query.getResultList();
        System.out.println(superClass2.get(0).getName() + "-" + superClass2.get(0).getSubName1());

        session.close();
    }
}

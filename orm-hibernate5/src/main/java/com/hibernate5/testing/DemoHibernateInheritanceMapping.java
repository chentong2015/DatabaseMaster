package com.hibernate5.testing;

import com.hibernate5.testing.basemodel.AbstractSuperClass;
import com.hibernate5.testing.basemodel.SuperClass;
import com.hibernate5.testing.basemodel.SuperClass2;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class DemoHibernateInheritanceMapping {

    static StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
    static SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();

    public static void main(String[] args) {
        // testTablePerClassHierarchy();
        testTablePerSubclassAndPerConcreteClass();
    }

    // TODO. 1. <subclass> 整个继承链只有一个数据表
    public static void testTablePerClassHierarchy() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        AbstractSuperClass superClass1 = new com.hibernate5.testing.package1.SubClass();
        superClass1.setReference(10);
        AbstractSuperClass superClass2 = new com.hibernate5.testing.package1.SubClass();
        superClass2.setReference(20);
        AbstractSuperClass superClass3 = new com.hibernate5.testing.package2.SubClass();
        superClass3.setReference(30);

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
        superClass.setReference(10);
        superClass.setName("super class");
        com.hibernate5.testing.package1.SubSuperClass subSuperClass = new com.hibernate5.testing.package1.SubSuperClass();
        subSuperClass.setReference(20);
        subSuperClass.setName("sub super class 1");
        subSuperClass.setSubName1("sub name 1");
        session.persist(superClass);
        session.persist(subSuperClass);

        SuperClass2 superClass2 = new SuperClass2();
        superClass2.setReference(10);
        superClass2.setName("super class 2");
        com.hibernate5.testing.package2.SubSuperClass subSuperClass2 = new com.hibernate5.testing.package2.SubSuperClass();
        subSuperClass2.setReference(20);
        subSuperClass2.setName("sub super class 2");
        subSuperClass2.setSubName2("sub name 2");
        session.persist(superClass2);
        session.persist(subSuperClass2);

        transaction.commit();
        session.close();
    }
}

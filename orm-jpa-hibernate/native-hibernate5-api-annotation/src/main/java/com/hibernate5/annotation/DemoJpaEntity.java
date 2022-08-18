package com.hibernate5.annotation;

import com.hibernate5.annotation.join.JoinColumn.OneToMany.DemoJoinColumnOneToMany;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

// TODO. Hibernate Jpa annotation Mapping映射问题
// 1. @Entity() 没有设置名称，使用默认类型名称或者全路径都能查询 !!
// 2. @Entity(name = "entity-name") 如果设置名称为全路径，则必须使用全路径来查询 !!
public class DemoJpaEntity {

    static StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
    static SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();

    public static void main(String[] args) {
        Session session = sessionFactory.openSession();
        DemoJoinColumnOneToMany.initTable(session);
        session.close();
        sessionFactory.close();
    }
}
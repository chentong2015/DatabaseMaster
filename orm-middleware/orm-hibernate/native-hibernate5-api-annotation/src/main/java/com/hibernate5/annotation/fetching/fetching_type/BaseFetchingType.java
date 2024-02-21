package com.hibernate5.annotation.fetching.fetching_type;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class BaseFetchingType {

    static StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
    static SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();

    // 如果只是查询Department数据，在FetchType.LAZY类型下不会获取List<Employee>数据
    public static void main(String[] args) {
        Session session = sessionFactory.openSession();
        Department department = session.get(Department.class, 1L);
        System.out.println(department);
        session.close();
        sessionFactory.close();
    }
}
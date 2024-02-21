package com.hibernate5.testing;

import com.hibernate5.testing.package2.MyEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

import java.util.List;

// TODO. Hibernate xml Mapping映射问题
// 1. Hibernate v5.3.9
//    1.1 没有设置entity-name名称，HQL可以使用默认的class名称或全路径名称
//    1.2 如果设置entity-name名称，则必须使用设置的名称(名称中不能含有特殊的符号) !!
// 2. Hibernate v5.6.9
//    2.1 没有设置entity-name名称
//        HQL中如果有重名的POJO class名称，则报错DuplicationMappingException: entities share same JPA entity name
//        SomeClass.class.getName()或者class.getSimpleName()都能查询 !!
//    2.2 如果设置entity-name名称，则必须使用设置的名称
//        如果有重名的POJO class名称，在使用HQL getSimpleName()时会报错: TypedQuery is incompatible
//        如果设置为全路径，则class.getSimpleName()或者class.getName()都能查询 !!
public class DemoHibernateMapping {

    static StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure("hibernate.cfg.xml")
            .build();
    static SessionFactory sessionFactory = new MetadataSources(registry)
            .buildMetadata()
            .buildSessionFactory();

    public static void main(String[] args) {
        Session session = sessionFactory.openSession();
        testHqlQuery(session);
        // testSqlRawQuery(session);
        session.close();
    }

    // 使用class name来取类型的全路径名称(具有唯一性)
    private static void testHqlQuery(Session session) {
        String hqlQuery = "from " + MyEntity.class.getSimpleName();
        System.out.println(hqlQuery);
        Query<MyEntity> query = session.createQuery(hqlQuery, MyEntity.class);
        List<MyEntity> myEntities = query.getResultList();
        for (MyEntity entity : myEntities) {
            System.out.println(entity);
        }
    }

    // 测试SqlQuery查询: 是否只能使用DB table的名称来做查询 ?
    // 自定义返回的字段, 使用映射到数据库的table名称
    private static void testSqlRawQuery(Session session) {
        String sql = "Select name from t_second_entity";
        String sqlQuery = "Select name from " + MyEntity.class.getName();
        List<String> names = session.createSQLQuery(sqlQuery).getResultList();
        for (String name : names) {
            System.out.println("Found name: " + name);
        }
    }
}

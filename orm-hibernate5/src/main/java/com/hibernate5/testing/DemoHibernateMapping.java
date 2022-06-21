package com.hibernate5.testing;

import com.hibernate5.testing.package2.MyEntity;
import com.hibernate5.testing.query.SqlRawQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

import java.util.List;

// TODO. Hibernate Mapping映射问题
// 1. Hibernate v5.3.9
//    1.1 没有设置entity-name名称，HQL可以使用默认的class名称或全路径名称
//    1.2 如果设置entity-name名称，则必须使用设置的名称(名称中不能含有特殊的符号) !!
// 2. Hibernate v5.6.9
//    2.1 没有设置entity-name名称，HQL中如果有重名的POJO class名称，则报错 !!
//    2.2 如果设置entity-name名称，则必须使用设置的名称
//        如果这里设置的是全路径的名称，则class.getSimpleName()或者class.getName()都能查询
public class DemoHibernateMapping {

    static StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure("hibernate.cfg.xml")
            .build();
    static SessionFactory sessionFactory = new MetadataSources(registry)
            .buildMetadata()
            .buildSessionFactory();

    public static void main(String[] args) {
        Session session = sessionFactory.openSession();
        // 使用class name来取类型的全路径名称(具有唯一性)
        String hqlQuery = "from " + MyEntity.class.getSimpleName();
        System.out.println(hqlQuery);

        Query<MyEntity> query = session.createQuery(hqlQuery, MyEntity.class);
        List<MyEntity> myEntities = query.getResultList();
        for (MyEntity entity : myEntities) {
            System.out.println(entity);
        }

        // 测试SqlQuery查询: 自定义返回的字段, 使用映射到数据库的table名称
        SqlRawQuery sqlRawQuery = new SqlRawQuery("Select name from t_second_entity");
        Query sqlQuery = sqlRawQuery.getQuery(session);
        List<String> names = sqlQuery.getResultList();
        for (String name : names) {
            System.out.println(name);
        }
        session.close();
    }
}

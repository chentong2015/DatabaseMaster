package com.hibernate5.testing;

import com.hibernate5.testing.package2.MyEntity;
import com.hibernate5.testing.query.SqlRawQuery;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;

public class DemoHibernateMapping {

    public static void main(String[] args) {
        Session session = new Configuration().configure().buildSessionFactory().openSession();
        // TODO. 这里的HQL查询必须使用entity-name名称，如果自定义修改了
        //       则必须使用全路径com.hibernate5.testing.package2.MyEntity作为查询的引用
        Query<MyEntity> query = session.createQuery("from com.hibernate5.testing.package2.MyEntity", MyEntity.class);
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

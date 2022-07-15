package com.hibernate5.testing.query;

import com.hibernate5.testing.package1.MyEntity;
import com.hibernate5.testing.session.HibernateSessionUtil;
import org.hibernate.Session;

import java.util.List;

// 测试能否拿到HQL和SQL两种具名查询语句
// https://docs.jboss.org/hibernate/core/3.6/reference/fr-FR/html/querysql.html
public class DemoNamedQueries {

    public static void main(String[] args) {
        Session session = HibernateSessionUtil.getSession();
        // session.createNamedQuery("hqlNamedQuerySelectEntity", MyEntity.class).getSingleResult();
        MyEntity entity = (MyEntity) session.getNamedQuery("hqlNamedQuerySelectEntity").getSingleResult();
        System.out.println(entity);

        // session.createNativeQuery("sqlNamedQuerySelectEntity").getResultList();
        List<MyEntity> entityList = session.getNamedNativeQuery("sqlNamedQuerySelectEntity").getResultList();
        for (MyEntity entity1 : entityList) {
            System.out.println(entity1);
        }
        HibernateSessionUtil.closeSession();
    }
}

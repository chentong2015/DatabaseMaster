package com.hibernate5.testing.query;

import com.hibernate5.testing.package1.MyEntity;
import com.hibernate5.testing.HibernateSessionUtil;
import org.hibernate.Session;

import java.util.List;

// 测试能否拿到HQL和SQL两种具名查询语句
// https://docs.jboss.org/hibernate/core/3.6/reference/fr-FR/html/querysql.html
public class DemoNamedQueries {

    public static void main(String[] args) {
        Session session = HibernateSessionUtil.getSession();
        testHqlNamedQueryWithParameters(session);
        HibernateSessionUtil.closeSession();
    }

    private static void testHqlNamedQuery(Session session) {
        // session.createNamedQuery("hqlNamedQuerySelectEntity", MyEntity.class).getSingleResult();
        String nameHql = "hqlNamedQuerySelectEntity";
        MyEntity entity = (MyEntity) session.getNamedQuery(nameHql).getSingleResult();
        System.out.println(entity);
    }

    private static void testHqlNamedQueryWithAlias(Session session) {
        MyEntity entity = (MyEntity) session.getNamedQuery("hqlSelectWithAlias").getSingleResult();
        System.out.println(entity);
    }

    // TODO. 使用具名参数时，设置的Parameter Index和NamedQuery中标注的?Index一致
    //  JDBC PreparedStatement 使用的是? ? (从parameterIndex=1开始)
    //  源码中底层是如何构建PreparedStatement和SQL查询语句的 ?
    private static void testHqlNamedQueryWithParameters(Session session) {
        MyEntity entity = (MyEntity) session.getNamedQuery("hqlSelectWithParameter")
                .setParameter(1, "test")
                .setParameter(2, 50.0)
                .getSingleResult();
        System.out.println(entity);
    }

    private static void testSqlNamedQuery(Session session, String sqlName) {
        // session.createNativeQuery("sqlNamedQuerySelectEntity").getResultList();
        String nameSql = "sqlNamedQuerySelectEntity";
        List<MyEntity> entityList = session.getNamedNativeQuery(nameSql).getResultList();
        for (MyEntity entity1 : entityList) {
            System.out.println(entity1);
        }
    }
}

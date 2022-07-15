package com.hibernate5.testing.query;

import com.hibernate5.testing.package1.MyEntity;
import com.hibernate5.testing.session.HibernateSessionUtil;
import org.hibernate.Session;

import java.util.List;

public class DemoNamedQueries {

    public static void main(String[] args) {
        Session session = HibernateSessionUtil.getSession();
        // MyEntity entity = session.createNamedQuery("selectTestEntity", MyEntity.class).getSingleResult();
        // System.out.println(entity);

        List<MyEntity> entityList = session.createNativeQuery("selectMyEntities", MyEntity.class).getResultList();
        for (MyEntity entity1 : entityList) {
            System.out.println(entity1);
        }

        HibernateSessionUtil.closeSession();
    }
}

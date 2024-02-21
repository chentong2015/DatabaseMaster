package com.hibernate5.testing.any_master;

import com.hibernate5.testing.any_master.entities.FilterItem;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;

public class HibernateAnyMaster {

    static StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure("hibernate.cfg.xml")
            .build();
    static SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();

    // TODO. 只有在加载对应的含有<any设置的表中查询，hibernate才会验证ref的约束
    public static void main(String[] args) {
        Session session = sessionFactory.openSession();
        // String hql = "FROM " + Perspective.class.getName();
        // List<Perspective> results = session.createQuery(hql, Perspective.class).getResultList();
        // System.out.println(results.get(0).getDescription());

        // session.get(FilterItem.class, 1D);
        List<FilterItem> items = session.createQuery("FROM " + FilterItem.class.getName(), FilterItem.class).getResultList();
        System.out.println(items.size());
        for (FilterItem item : items) {
            System.out.println(item.getParentReference());
            // System.out.println(((Portfolio) item.getObjectInstance()).getDescription());
        }

        session.close();
        sessionFactory.close();
    }
}

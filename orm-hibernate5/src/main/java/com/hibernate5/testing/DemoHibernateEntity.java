package com.hibernate5.testing;

import com.hibernate5.testing.entity.Person;
import com.hibernate5.testing.entity.Sample;
import com.hibernate5.testing.query.SqlRawQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

import java.util.List;

public class DemoHibernateEntity {

    static StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
    static SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();

    public static void main(String[] args) {
        Session session = sessionFactory.openSession();
        Query<Person> query = session.createQuery("from Person", Person.class);
        List<Person> personList = query.getResultList();
        for (Person person : personList) {
            System.out.println(person);
        }

        SqlRawQuery sqlRawQuery = new SqlRawQuery("Select firstname from t_person");
        Query sqlQuery = sqlRawQuery.getQuery(session);
        List<String> firstnameList = sqlQuery.getResultList();
        for (String firstname : firstnameList) {
            System.out.println(firstname);
        }
        session.close();
    }

    private void testGetSampleData() {
        Session session = sessionFactory.openSession();
        Query<Sample> query = session.createQuery("from Sample", Sample.class);
        List<Sample> samples = query.getResultList();
        for (Sample sample : samples) {
            System.out.println(sample);
        }
        session.close();
    }
}

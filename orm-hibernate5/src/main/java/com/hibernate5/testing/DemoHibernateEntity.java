package com.hibernate5.testing;

import com.hibernate5.testing.entity.Person;
import com.hibernate5.testing.entity.Sample;
import com.hibernate5.testing.query.SqlRawQuery;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;

public class DemoHibernateEntity {

    public static void main(String[] args) {
        Session session = new Configuration().configure().buildSessionFactory().openSession();
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
        Session session = new Configuration().configure().buildSessionFactory().openSession();
        Query<Sample> query = session.createQuery("from Sample", Sample.class);
        List<Sample> samples = query.getResultList();
        for (Sample sample : samples) {
            System.out.println(sample);
        }
        session.close();
    }
}

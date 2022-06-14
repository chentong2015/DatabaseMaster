package com.hibernate5.testing;

import com.hibernate5.testing.entity.Person;
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
    }
}

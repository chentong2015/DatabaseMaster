package jpa.api;

import com.hibernate5.testing.query.SqlRawQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

import java.util.List;

// TODO. Hibernate Jpa annotation Mapping映射问题
// 1. @Entity() 如果没有设置名称，使用默认的类型名称
// 2. @Entity(name = "entity-name") 如果设置名称为全路径，则必须使用相同的值 ?
public class HibernateJpaEntityDemo {

    static StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
    static SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();

    public static void main(String[] args) {
        Session session = sessionFactory.openSession();
        String hqlQuery = "From " + Person.class.getName();
        Query<Person> query = session.createQuery(hqlQuery, Person.class);
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

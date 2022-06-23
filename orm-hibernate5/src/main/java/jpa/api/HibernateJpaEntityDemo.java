package jpa.api;

import com.hibernate5.testing.query.SqlRawQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

// TODO. Hibernate Jpa annotation Mapping映射问题
// 1. @Entity() 如果没有设置名称，使用默认的类型名称
// 2. @Entity(name = "entity-name") 如果设置名称为全路径，则必须使用全路径来查询 !!
public class HibernateJpaEntityDemo {

    static StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
    static SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();

    public static void main(String[] args) {
        Session session = sessionFactory.openSession();

        Query<Person> query = session.createQuery("From " + Person.class.getName(), Person.class);
        List<Person> personList = query.getResultList();
        for (Person person : personList) {
            System.out.println(person);
        }
        
        String hqlQuery = "Select p.firstname FROM" + Person.class.getName() + " p where p.id = :id";
        Optional<Person> query1 = session.createQuery(hqlQuery, Person.class)
                .setParameter("id", 4)
                .stream().findFirst();

        SqlRawQuery sqlRawQuery = new SqlRawQuery("Select firstname from t_person");
        Query sqlQuery = sqlRawQuery.getQuery(session);
        List<String> firstnameList = sqlQuery.getResultList();
        for (String firstname : firstnameList) {
            System.out.println(firstname);
        }
        session.close();
        sessionFactory.close();
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



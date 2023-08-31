package hibernate_mockito;

import hibernate_mockito.entity.Account;
import hibernate_mockito.entity.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateMockitoDemo {

    public static void main(String[] args) {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        SessionFactory sessionFactory = new MetadataSources(registry)
                .addAnnotatedClass(Account.class)
                .addAnnotatedClass(Person.class)
                .buildMetadata()
                .buildSessionFactory();

        Session session = sessionFactory.openSession();
        session.getTransaction().begin();

        Person person = new Person();
        Account account = new Account("username", 10l, person, "role");
        session.persist(account);

        session.getTransaction().commit();
        session.close();
    }
}

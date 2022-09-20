package many.to.one;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class TestingManyToOne {

    private static StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
    private static SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();

    public static void main(String[] args) {
        Session session = sessionFactory.openSession();
        session.getTransaction().begin();

        Person person = new Person("victor", 28);
        session.persist(person);

        Car car1 = new Car("car name 1", person);
        Car car2 = new Car("car name 2", person);
        Car car3 = new Car("car name 3", person);
        session.persist(car1);
        session.persist(car2);
        session.persist(car3);

        session.getTransaction().commit();
        session.close();
        sessionFactory.close();
    }
}

package many.to.one;

import org.hibernate.Session;
import util.HibernateSessionUtil;

public class TestingManyToOne {

    public static void main(String[] args) {
        Session session = HibernateSessionUtil.getSession();
        session.getTransaction().begin();

        Person person = new Person("victor", 28);
        // session.persist(person);

        Car car1 = new Car("car name 1", person);
        Car car2 = new Car("car name 2", person);
        Car car3 = new Car("car name 3", person);
        session.persist(car1);
        session.persist(car2);
        session.persist(car3);

        session.getTransaction().commit();
        HibernateSessionUtil.closeSession();
    }
}

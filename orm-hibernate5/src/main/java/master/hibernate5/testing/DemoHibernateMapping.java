package master.hibernate5.testing;

import master.hibernate5.testing.package1.MyEntity;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;

public class DemoHibernateMapping {

    public static void main(String[] args) {
        Session session = new Configuration().configure().buildSessionFactory().openSession();
        Query<MyEntity> query = session.createQuery("from MyEntity", MyEntity.class);
        List<MyEntity> myEntities = query.getResultList();
        for (MyEntity entity : myEntities) {
            System.out.println(entity.getId() + " - " + entity.getName() + " - " + entity.getCode());
        }
        session.close();
    }

}

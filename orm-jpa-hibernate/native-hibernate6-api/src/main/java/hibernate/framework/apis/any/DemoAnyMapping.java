package hibernate.framework.apis.any;

import hibernate.framework.apis.any.model.IntegerProperty;
import hibernate.framework.apis.any.model.StringProperty;
import hibernate.framework.apis.session.HibernateSessionUtil;
import org.hibernate.Session;

public class DemoAnyMapping {

    public static void main(String[] args) {
        Session session = HibernateSessionUtil.getSession();
        PropertyHolder propertyHolder = session.get(PropertyHolder.class, 1L);
        System.out.println(propertyHolder.getProperty().getName());
        System.out.println(propertyHolder.getProperty().getValue());
        HibernateSessionUtil.closeSession();
    }

    private static void persist(Session session) {
        session.getTransaction().begin();
        IntegerProperty ageProperty = new IntegerProperty();
        ageProperty.setId(1L);
        ageProperty.setName("age");
        ageProperty.setValue(23);
        session.persist(ageProperty);

        StringProperty nameProperty = new StringProperty();
        nameProperty.setId(1L);
        nameProperty.setName("name");
        nameProperty.setValue("John Doe");
        session.persist(nameProperty);

        // .setProperty() 可以设置成任何MyProperty接口的实现
        PropertyHolder namePropertyHolder = new PropertyHolder();
        namePropertyHolder.setId(1L);
        namePropertyHolder.setProperty(nameProperty);
        session.persist(namePropertyHolder);
        session.getTransaction().commit();
    }
}

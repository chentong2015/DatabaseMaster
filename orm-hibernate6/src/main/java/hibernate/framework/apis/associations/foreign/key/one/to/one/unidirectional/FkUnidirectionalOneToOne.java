package hibernate.framework.apis.associations.foreign.key.one.to.one.unidirectional;

import hibernate.framework.apis.session.HibernateSessionUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

// 单向一对一的关系，只能从Person对象上(基于DB中外键)获取到IdCard
public class FkUnidirectionalOneToOne {

    public static void main(String[] args) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateSessionUtil.getSession();
            transaction = session.beginTransaction();

            IdCard idCard1 = new IdCard();
            idCard1.setCode("102023");
            IdCard idCard2 = new IdCard();
            idCard2.setCode("203991");
            session.persist(idCard1);
            session.persist(idCard2);

            Person person1 = new Person();
            person1.setName("person 1");
            person1.setAge(20);
            person1.setIdCard(idCard1);
            Person person2 = new Person();
            person2.setName("person 2");
            person2.setAge(30);
            person2.setIdCard(idCard2);
            session.persist(person1);
            session.persist(person2);

            transaction.commit();
        } catch (Exception exception) {
            exception.printStackTrace();
            transaction.rollback();
        } finally {
            HibernateSessionUtil.closeSession();
        }
    }
}

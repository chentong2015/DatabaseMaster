package hibernate.framework.apis.mapping.primary.key.one.to.one;

import hibernate.framework.apis.session.HibernateSessionUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

// 双向一个对，可以从两端(基于DB中外键)来获取
public class PkUnidirectionalOneToOne {

    public static void main(String[] args) {
        testSaveObjects();
        testGetObject();
    }

    private static void testSaveObjects() {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateSessionUtil.getSession();
            transaction = session.beginTransaction();

            IdCard idCard1 = new IdCard();
            idCard1.setCode("102023");
            IdCard idCard2 = new IdCard();
            idCard2.setCode("203991");

            Person person1 = new Person();
            person1.setName("person 1");
            person1.setAge(20);
            person1.setIdCard(idCard1);
            Person person2 = new Person();
            person2.setName("person 2");
            person2.setAge(30);
            person2.setIdCard(idCard2);

            // 由于双向级联设置成save-update, 只需要存储一端的数据
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

    private static void testGetObject() {
        Session session = null;
        try {
            session = HibernateSessionUtil.getSession();
            Person person = session.get(Person.class, 1);
            System.out.println(person.getName() + "--" + person.getIdCard().getCode());
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            HibernateSessionUtil.closeSession();
        }
    }
}

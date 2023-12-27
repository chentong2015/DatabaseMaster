package hibernate.framework.apis.mappings.one.to.one.foreign.key.bidirectional;

import hibernate.framework.apis.session.HibernateSessionUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

// 双向一个对，可以从两端(基于DB中外键)来获取
public class FkBidirectionalOneToOne {

    public static void main(String[] args) {
        testSaveObjects();
        testGetObjectFromTwoSides();
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
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            HibernateSessionUtil.closeSession();
        }
    }

    private static void testGetObjectFromTwoSides() {
        Session session = null;
        try {
            session = HibernateSessionUtil.getSession();
            IdCard idCard = session.get(IdCard.class, 1);
            System.out.println(idCard.getCode() + "--" + idCard.getPerson().getName());
            Person person = session.get(Person.class, 1);
            System.out.println(person.getName() + "--" + person.getIdCard().getCode());
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            HibernateSessionUtil.closeSession();
        }
    }
}

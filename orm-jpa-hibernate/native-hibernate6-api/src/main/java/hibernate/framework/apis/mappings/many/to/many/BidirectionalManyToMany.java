package hibernate.framework.apis.mappings.many.to.many;

import hibernate.framework.apis.session.HibernateSessionUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.HashSet;
import java.util.Set;

public class BidirectionalManyToMany {

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

            Strategy strategy1 = new Strategy();
            Strategy strategy2 = new Strategy();
            Set<Strategy> set1 = new HashSet<>();
            set1.add(strategy1);
            set1.add(strategy2);

            Typology typology1 = new Typology();
            Typology typology2 = new Typology();
            Set<Typology> set2 = new HashSet<>();
            set2.add(typology1);
            set2.add(typology2);

            strategy1.setId(1);
            strategy1.setName("strategy1");
            strategy1.setTypologies(set2);

            strategy2.setId(2);
            strategy2.setName("strategy2");
            strategy2.setTypologies(set2);

            typology1.setId(10);
            typology1.setName("typology1");
            typology1.setStrategies(set1);

            typology2.setId(20);
            typology2.setName("typology2");
            typology2.setStrategies(set1);

            session.persist(strategy1);
            session.persist(strategy2);
            session.persist(typology1);
            session.persist(typology2);
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

    private static void testGetObject() {
        Session session = null;
        try {
            session = HibernateSessionUtil.getSession();
            Strategy strategy = session.get(Strategy.class, 1);
            System.out.println(strategy.getId());
            System.out.println(strategy.getName());
            Set<Typology> typologies = strategy.getTypologies();
            for (Typology typology : typologies) {
                System.out.println(typology.getId() + " -- " + typology.getName());
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            HibernateSessionUtil.closeSession();
        }
    }
}

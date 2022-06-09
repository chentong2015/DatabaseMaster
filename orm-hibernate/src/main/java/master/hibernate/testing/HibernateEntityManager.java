package master.hibernate.testing;

import master.hibernate.testing.first.MyPojo;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

// https://www.baeldung.com/hibernate-entitymanager
public class HibernateEntityManager {

    public static void testTwoEntitiesWithSameName() {
        EntityManager entityManager = Persistence
                .createEntityManagerFactory("master.hibernate.testing")
                .createEntityManager();
        entityManager.getTransaction().begin();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        // TODO. 创建一个查询的标准，指定要查询的数据类型和Selection返回的字段
        CriteriaQuery<master.hibernate.testing.first.MyPojo> cq = criteriaBuilder
                .createQuery(master.hibernate.testing.first.MyPojo.class);
        Root<MyPojo> root = cq.from(master.hibernate.testing.first.MyPojo.class);
        cq.select(root.get("id"));
        entityManager.createQuery(cq).getResultList();

        CriteriaQuery<master.hibernate.testing.second.MyPojo> cq1 = criteriaBuilder
                .createQuery(master.hibernate.testing.second.MyPojo.class);
        cq1.from(master.hibernate.testing.second.MyPojo.class);
        entityManager.createQuery(cq1).getResultList();

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}

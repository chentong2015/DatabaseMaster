package hibernate.jpa.api;

import hibernate.framework.apis.entity.Book;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

// Hibernate JPA API (功能较少, 性能较低)
// https://www.baeldung.com/hibernate-entitymanager
public class DemoHibernateJpaAPI {

    // TODO. 创建一个查询的标准，指定要查询的数据类型和Selection返回的字段
    //  同时可以指定在执行查询时的锁策略
    public void testCriteriaQueries() {
        EntityManager entityManager = Persistence
                .createEntityManagerFactory("hibernate.jpa.api")
                .createEntityManager();
        entityManager.getTransaction().begin();

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Book> criteriaQuery = criteriaBuilder.createQuery(Book.class);

        Root<Book> root = criteriaQuery.from(Book.class);
        criteriaQuery.select(root.get("id"));
        criteriaQuery.where(criteriaBuilder.equal(root.get("name"), "java"));
        criteriaQuery.distinct(true);
        criteriaQuery.having();

        entityManager.createQuery(criteriaQuery).setLockMode(LockModeType.OPTIMISTIC).getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    // 提供Jpa native sql原生的sql查询语句
    public void testNativeSqlQueries(EntityManager entityManager) {
        List<Object[]> books = entityManager.createNativeQuery("SELECT * FROM Book")
                .getResultList();
        List<Object[]> bookList = entityManager.createNativeQuery("SELECT id, name FROM Person")
                .getResultList();

        // 提供实体的类型
        List<Book> books1 = entityManager.createNativeQuery("SELECT * FROM Person", Book.class)
                .getResultList();
        List<Book> bookList1 = entityManager.createNativeQuery(
                        "SELECT id, name, nickName, address, createdOn, version " + "FROM Person", Book.class)
                .getResultList();
    }
}

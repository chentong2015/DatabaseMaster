package com.hibernate.entity.manager;

import com.hibernate.entity.manager.entity.Book;
import com.hibernate.entity.manager.entity.Sample;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

// Hibernate JPA API (功能较少, 性能较低)
// 1. HqlQuery      提供标准的HQL查询字符串
// 2. CriteriaQuery 创建一个查询的标准，指定要查询的数据类型和Selection返回的字段，指定查询策略
// 3. NativeQuery   提供Jpa native sql原生的sql查询语句
// https://www.baeldung.com/hibernate-entitymanager
public class DemoEntityManagerQueries {

    // TODO. 在添加完@Entity(name="")名称之后，这里的HQL查询语句必须使用全路径名称
    // 如果这里使用Simple Class Name，则会提示报错; 下面拿到的是类型的全路径
    private static void testHqlQuery() {
        EntityManager entityManager = EntityManagerOperations.getEntityManager();
        entityManager.getTransaction().begin();
        String fullPath = Sample.class.getCanonicalName();
        String deleteQuery = "DELETE FROM " + fullPath + " p WHERE p.name = :name";
        entityManager.createQuery(deleteQuery).setParameter("name", "test").executeUpdate();
        entityManager.getTransaction().commit();
    }

    // TODO. 使用CriteriaQuery.createQuery(Sample.class)来创建条件查询和Entity Name没有关系 !!
    private static void testCriteriaQuery() {
        EntityManager entityManager = EntityManagerOperations.getEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Sample> criteriaQuery = criteriaBuilder.createQuery(Sample.class);

        Root<Sample> root = criteriaQuery.from(Sample.class);
        // criteriaQuery.select(root.get("id"));
        criteriaQuery.where(criteriaBuilder.equal(root.get("name"), "java"));
        criteriaQuery.distinct(true);
        // criteriaQuery.having(predicate..);
        List<Sample> sampleList = entityManager.createQuery(criteriaQuery).getResultList();
        // .setLockMode(LockModeType.OPTIMISTIC) 没有transaction则不需要设置锁模式

        entityManager.close();
        for (Sample sample : sampleList) {
            System.out.println(sample.getId());
        }
    }

    // TODO. 提供查询出来的结果对应的类型，如果是原实体类型，则必须查询出所有的字段
    public void testNativeSqlQueries(EntityManager entityManager) {
        List<Object[]> books = entityManager.createNativeQuery("SELECT * FROM Book").getResultList();
        List<Object[]> bookList = entityManager.createNativeQuery("SELECT id, name FROM Person").getResultList();
        String sql = "SELECT * FROM Person";
        List<Book> books1 = entityManager.createNativeQuery(sql, Book.class).getResultList();
        String sqlString = "SELECT id, name, nickName, address, createdOn, version " + "FROM Person";
        List<Book> bookList1 = entityManager.createNativeQuery(sqlString, Book.class).getResultList();
    }
}

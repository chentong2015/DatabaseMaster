package com.hibernate.entity.manager;

import com.hibernate.entity.manager.entity.Book;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

// Hibernate JPA API (功能较少, 性能较低)
// https://www.baeldung.com/hibernate-entitymanager
// 1. CriteriaQuery 创建一个查询的标准，指定要查询的数据类型和Selection返回的字段，指定查询策略
// 2. NativeQuery   提供Jpa native sql原生的sql查询语句
public class EntityManagerQueries {

    public void testCriteriaQueries() {
        EntityManager entityManager = EntityManagerOperations.getEntityManager();
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

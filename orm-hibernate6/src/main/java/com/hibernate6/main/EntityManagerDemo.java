package com.hibernate6.main;

import com.hibernate6.main.entity.Book;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

// https://www.baeldung.com/hibernate-entitymanager
public class EntityManagerDemo {

    public static void testTwoEntitiesWithSameName() {
        EntityManager entityManager = Persistence
                .createEntityManagerFactory("master.hibernate.testing")
                .createEntityManager();
        entityManager.getTransaction().begin();

        // TODO. 创建一个查询的标准，指定要查询的数据类型和Selection返回的字段
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Book> cq = criteriaBuilder.createQuery(Book.class);
        Root<Book> root = cq.from(Book.class);
        cq.select(root.get("id"));

        entityManager.createQuery(cq)
                .setLockMode(LockModeType.OPTIMISTIC) // 指定查询时使用的锁策略
                .getResultList();

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}

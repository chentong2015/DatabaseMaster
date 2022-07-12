package com.hibernate.entity.manager;

import com.hibernate.entity.manager.entity.Sample;

import javax.persistence.EntityManager;

public class DemoEntityManager {

    public static void main(String[] args) {
        EntityManager entityManager = EntityManagerOperations.getEntityManager();
        entityManager.getTransaction().begin();

        // TODO. 在添加完@Entity(name="")名称之后，这里的HQL查询语句必须使用全路径名称
        // 如果这里使用Simple Class Name，则会提示报错; 下面拿到的是类型的全路径
        String fullPath = Sample.class.getCanonicalName();

        String deleteQuery = "DELETE FROM " + fullPath + " p WHERE p.name = :name";
        entityManager.createQuery(deleteQuery).setParameter("name", "test").executeUpdate();
        entityManager.getTransaction().commit();
        System.out.println("Test ok.");
    }
}

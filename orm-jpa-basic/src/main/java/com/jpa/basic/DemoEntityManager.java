package com.jpa.basic;

import com.jpa.basic.entity.Sample;

import javax.persistence.EntityManager;

public class DemoEntityManager {

    public static void main(String[] args) {
        EntityManager entityManager = EntityManagerOperations.getEntityManager();
        entityManager.getTransaction().begin();

        String deleteQuery = "DELETE FROM " + Sample.class.getSimpleName() + " p WHERE p.name = :name";
        entityManager.createQuery(deleteQuery).setParameter("name", "test").executeUpdate();
        entityManager.getTransaction().commit();
        System.out.println("Test ok.");
    }
}

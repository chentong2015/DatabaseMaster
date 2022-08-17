package com.hibernate.entity.manager;

import com.hibernate.entity.manager.entity.Sample;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

public class DemoJakartaPersistenceAPI {

    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("base.jpa.demo");

    public static void main(String[] args) {
        EntityManager entityManager = emf.createEntityManager();
        String sqlString = "SELECT * FROM t_sample_entity";
        List<Sample> sampleList = entityManager.createNativeQuery(sqlString, Sample.class).getResultList();
        for (Sample sample : sampleList) {
            System.out.println(sample);
        }
        entityManager.close();
    }
}

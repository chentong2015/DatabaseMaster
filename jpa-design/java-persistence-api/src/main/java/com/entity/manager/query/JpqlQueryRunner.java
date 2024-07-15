package com.entity.manager.query;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Jpql: JPA Query Language 针对JPA API的查询语句
public class JpqlQueryRunner {

    public static final int FETCH_SIZE = 50000;
    private final EntityManagerFactory emf;

    public JpqlQueryRunner(DataSource dataSource) {
        this.emf = newEntityManagerFactory(dataSource);
    }

    // TODO. 在创建EntityManagerFactory时通过源代码Properties进行配置
    private EntityManagerFactory newEntityManagerFactory(DataSource dataSource) {
        Map<String, Object> jpaProperties = new HashMap<>();
        jpaProperties.put("hibernate.connection.datasource", dataSource);
        jpaProperties.put("hibernate.dialect_resolvers", "xxxDialectResolver.class.getName()");
        jpaProperties.put("hibernate.show_sql", "false");
        jpaProperties.put("hibernate.hbm2ddl.auto", "none");
        return Persistence.createEntityManagerFactory("name", jpaProperties);
    }

    public <T> List<T> runJpqlQuery(String jpqlString, Map<String, Object> parameters) {
        return runJpqlQuery(jpqlString, parameters, FETCH_SIZE);
    }

    public <T> List<T> runJpqlQuery(String jpqlString, Map<String, Object> parameters, int maxResults) {
        EntityManager em = emf.createEntityManager();
        try {
            Query query = em.createQuery(jpqlString);
            setJPQLParameters(query, parameters);
            if (maxResults > 0) {
                query.setMaxResults(maxResults);
            }
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    private static void setJPQLParameters(Query query, Map<String, Object> parameters) {
        for (Map.Entry<String, Object> entry : parameters.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }
    }
}

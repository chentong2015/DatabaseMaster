package jpa.api.named.query;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class EntityNamedService {

    // TODO. 纯JPA的使用方式: 通过实体类型上定义的NamedQuery具名查询来查询数据
    public List<EntityNamedQuery> findAllEntitiesNamed() {
        EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("unit name");
        EntityManager entityManager = managerFactory.createEntityManager();
        List<EntityNamedQuery> results = entityManager.createNamedQuery("EntityNamed.findAll").getResultList();
        return results;
    }
}

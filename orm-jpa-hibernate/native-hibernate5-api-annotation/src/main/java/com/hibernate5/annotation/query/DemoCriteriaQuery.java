package com.hibernate5.annotation.query;

import org.hibernate.Criteria;
import org.hibernate.FlushMode;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

// TODO... 使用CriteriaQuery来写一个相对复杂一点的查询语句
public class DemoCriteriaQuery {

    // 完整的对应到SQL原生查询语句的条件
    private static void test(Session session) {
        // Builder用来设置一些匹配条件
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        // 设置查询的条件
        CriteriaQuery<BaseEntity> criteriaQuery = criteriaBuilder.createQuery(BaseEntity.class);
        // 从那个entity-name对应的表中查询
        Root<BaseEntity> root = criteriaQuery.from(BaseEntity.class);

        // 完整的查询的条件
        criteriaQuery.distinct(true);
        criteriaQuery.select(root);
        criteriaQuery.select(root.get("id"));
        criteriaQuery.where(criteriaBuilder.equal(root.get("name"), "new test"));
        // criteriaQuery.orderBy()

        // 执行查询，获取返回的结果 .list()
        List<BaseEntity> samples = session.createQuery(criteriaQuery).getResultList();
        System.out.println(samples);
    }

    // TODO. 设置Session在执行数据库操作时的相关配置
    // 等效于hibernate.cfg.xml中配置，在Java硬编码中设置属性
    private static Criteria getCriteria(Session session, Class<?> fromClass) {
        Criteria criteria = session.createCriteria(fromClass);
        criteria.setLockMode(LockMode.OPTIMISTIC);
        criteria.addOrder(Order.asc("firstname"));
        criteria.setFlushMode(FlushMode.COMMIT);
        criteria.setFetchSize(10);
        // criteria.setMaxResults(1);
        // criteria.setProjection(Projections.rowCount())
        // criteria.uniqueResult();
        return criteria;
    }
}

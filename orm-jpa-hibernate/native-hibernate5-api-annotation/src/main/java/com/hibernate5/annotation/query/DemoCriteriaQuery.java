package com.hibernate5.annotation.query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.criterion.Order;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

// TODO: 使用CriteriaQuery来实现相对复杂一点的查询语句
public class DemoCriteriaQuery {

    static StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
    static SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();

    public static void main(String[] args) {
        Session session = sessionFactory.openSession();
        System.out.println("demo ");
        session.close();
    }

    // 完整的对应到SQL原生查询语句的条件
    // Select distinct label
    // from t_entity_sample
    // where name ='new test' and id>=2
    // order by label desc;
    private static void testSelectLabel(Session session) {
        // Builder用来设置一些匹配条件
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        // 创建条件查询，参数是查询结果的类型
        // Params: resultClass – type of the query result
        CriteriaQuery<String> criteriaQuery = criteriaBuilder.createQuery(String.class);
        // 从指定的Entity Class中查询数据
        // Params: entityClass – the entity class
        Root<BaseEntity> root = criteriaQuery.from(BaseEntity.class);

        criteriaQuery.distinct(true);
        // 只获取指定的属性
        criteriaQuery.select(root.get("label"));
        // 同时添加多个where的匹配条件: like, between, isNull, >=, <=
        criteriaQuery.where(criteriaBuilder.equal(root.get("name"), "new test"), criteriaBuilder.greaterThanOrEqualTo(root.get("id"), 2));
        // 添加order by根据字段进行排序
        criteriaQuery.orderBy((javax.persistence.criteria.Order) Order.desc("label"));
        // criteriaQuery.having()
        // criteriaQuery.groupBy()

        // 执行查询，获取返回的结果 .list()
        List<String> labels = session.createQuery(criteriaQuery).getResultList();
        for (String label : labels) {
            System.out.println(label);
        }
    }

    // select * from t_entity_sample;
    private static void testSelectEntityClass(Session session) {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<BaseEntity> criteriaQuery = criteriaBuilder.createQuery(BaseEntity.class);
        Root<BaseEntity> root = criteriaQuery.from(BaseEntity.class);

        // 获取整个Entity Class的所有属性(获取表的所有列)
        criteriaQuery.select(root);
        List<BaseEntity> baseEntities = session.createQuery(criteriaQuery).getResultList();
        for (BaseEntity baseEntity : baseEntities) {
            System.out.println(baseEntity);
        }
    }
}

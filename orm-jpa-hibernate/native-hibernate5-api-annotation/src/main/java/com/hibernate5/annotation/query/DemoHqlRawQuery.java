package com.hibernate5.annotation.query;

import com.hibernate5.annotation.entity.Person;
import com.hibernate5.annotation.entity.non.publiz.entity.SelectionResult;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

// HQL: hibernate query language 类似sql的简化查询语言
// 1. 必须满足特定的语句描述规则，查询时使用的JPA Entity Name名称
// 2. Hibernate源码中会将HQL转换成SQL，然后发送到数据库进行查询
//    org.hibernate.hql.internal.ast.QueryTranslatorImpl
public class DemoHqlRawQuery {

    // TODO. 测试复杂的HQL查询语句的构造
    // 1. .createQuery(query, String.class)   提供的是查询返回的结果类型
    // 2. .createQuery(query).executeUpdate() 如果是更新的语句，则不需要提供查询结果的类型 => 使用transaction事务
    public static void testHqlSelectQuery(Session session) {
        // 表示选择指定的table的所有字段
        String selectQuery = "From " + Person.class.getName();
        Query<Person> query = session.createQuery(selectQuery, Person.class);
        List<Person> personList = query.getResultList();
        for (Person person : personList) {
            System.out.println(person);
        }
    }

    public static void testHqlSelectQueryWhere(Session session) {
        String selectQuery1 = "Select p.firstname FROM " + Person.class.getName() + " p where p.id = :id";
        Optional<String> result = session.createQuery(selectQuery1, String.class)
                .setParameter("id", 4)
                .stream()
                .findFirst();
        if (result.isPresent()) {
            System.out.println("Firstname: " + result.get());
        }
    }

    // TODO. HQL支持将查询出来的Result构建成新的对象(非entity class)
    public static void testHqlWithJoin(Session session) {
        String query = "SELECT new jpa.annotations.entity.entity.SelectionResult(person, sample)" +
                "FROM %s person " +
                "JOIN jpa.annotations.entity.entity.Sample sample " +
                "ON person.id = sample.id";
        // 这里必须使用entity name的全路径
        String entityName = "jpa.annotation.entity.entity.Person";
        String hqlQuery = String.format(query, entityName);

        // 指定ResultSet返回的结果数据类型
        Query<SelectionResult> resultQuery = session.createQuery(hqlQuery, SelectionResult.class);
        List<SelectionResult> resultList = resultQuery.getResultList();
        for (SelectionResult result : resultList) {
            System.out.println(result);
        }
    }

    // TODO. Hibernate v6版本之前支持使用session.createQuery()来执行数据更新操作
    //  Insert, Update, Delete推荐使用session.createMutationQuery()
    public static void testHqlUpdate(Session session) {
        String updateQuery = "update " + Person.class.getName() + "set name = 'new name' where id=2";
        session.createQuery(updateQuery).executeUpdate();
    }

    public static void testHqlDeleteQuery(Session session) {
        session.beginTransaction();
        String deleteQuery = "Delete from " + Person.class.getName() + " p where p.id = 3";
        session.createQuery(deleteQuery).executeUpdate();
        session.getTransaction().commit();
    }
}

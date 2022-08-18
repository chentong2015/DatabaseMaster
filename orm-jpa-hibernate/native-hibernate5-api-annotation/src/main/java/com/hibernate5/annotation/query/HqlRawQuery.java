package com.hibernate5.annotation.query;

import com.hibernate5.annotation.entity.Person;
import com.hibernate5.annotation.entity.non.publiz.entity.SelectionResult;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

// HQL: hibernate query language 类似sql的简化查询语言
// 必须满足特定的语句描述规则, 主要看查询时使用的JPA Entity Name名称
public class HqlRawQuery {

    // TODO. 测试复杂的HQL查询语句的构造
    // 1. .createQuery(query, String.class)   提供的是查询返回的结果类型 The type of the query result
    // 2. .createQuery(query).executeUpdate() 如果是更新的语句，则不需要提供查询结果的类型
    //    执行更新和修改的语句时，需要使用transaction事务
    public static void testHqlQuery(Session session) {
        // 表示选择指定的table的所有字段
        String hqlQuery = "From " + Person.class.getName();
        Query<Person> query = session.createQuery(hqlQuery, Person.class);
        List<Person> personList = query.getResultList();
        for (Person person : personList) {
            System.out.println(person);
        }

        String hqlQuery1 = "Select p.firstname FROM " + Person.class.getName() + " p where p.id = :id";
        Optional<String> result = session.createQuery(hqlQuery1, String.class)
                .setParameter("id", 4)
                .stream()
                .findFirst();
        if (result.isPresent()) {
            System.out.println("Firstname: " + result.get());
        }

        session.beginTransaction();
        String hqlQuery2 = "Delete from " + Person.class.getName() + " p where p.id = 3";
        session.createQuery(hqlQuery2).executeUpdate();
        session.getTransaction().commit();
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
}

package jpa.api.query;

import jpa.api.Person;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

// 创建的是hibernate query查询语句
// session.createQuery(hqlQuery)
public class HqlRawQuery {

    private final String hqlQuery;

    public HqlRawQuery(String hqlQuery) {
        this.hqlQuery = hqlQuery;
    }

    protected Query getQuery(Session session) {
        return session.createQuery(hqlQuery);
    }

    protected <T> Query<T> getQuery(Session session, Class<T> clazz) {
        return session.createQuery(hqlQuery, clazz);
    }

    // TODO. 测试复杂的HQL查询语句的构造
    // 1. .createQuery(query, String.class)   提供的是查询返回的结果类型 The type of the query result
    // 2. .createQuery(query).executeUpdate() 如果是更新的语句，则不需要提供查询结果的类型
    //    执行更新和修改的语句时，需要使用transaction事务
    public static void testHqlQuery(Session session) {
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
}

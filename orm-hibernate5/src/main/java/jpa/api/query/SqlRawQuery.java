package jpa.api.query;

import jpa.api.Person;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import java.util.List;

// 创建原始的SQL查询语句 => 区别于HQL
// session.createNativeQuery(sqlQuery)
// session.createSQLQuery(sqlQuery)
public class SqlRawQuery {

    private final String sqlQuery;

    public SqlRawQuery(String sqlQuery) {
        this.sqlQuery = sqlQuery;
    }

    public Query getQuery(Session session) {
        // session.createNativeQuery(sqlQuery)
        return session.createSQLQuery(sqlQuery);
    }

    protected <T> Query<T> getQuery(Session session, Class<T> clazz) {
        NativeQuery<T> query = session.createSQLQuery(sqlQuery);
        // Identifiable.class.isAssignableFrom(clazz) 判断clazz是否是Identifiable的
        if ((clazz != null)) {
            query.addEntity(clazz);
        }
        return query;
    }

    // TODO. 测试复杂的查询语句和Entity Name的关系
    public static void testSqlQuery(Session session) {
        String sql = "Select firstname from t_person";
        String sqlQuery = "Select firstname from " + Person.class.getSimpleName();

        Query query = session.createSQLQuery(sqlQuery);
        List<String> firstnameList = query.getResultList();
        for (String firstname : firstnameList) {
            System.out.println(firstname);
        }
        
    }
}

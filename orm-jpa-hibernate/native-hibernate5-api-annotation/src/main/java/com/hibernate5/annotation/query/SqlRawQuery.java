package com.hibernate5.annotation.query;

import com.hibernate5.annotation.entity.MyEntity;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

// TODO. 创建原始的SQL查询语句, 使用的表名必须是映射出来的DB中的table名称 !!
// SQL语法异常: org.hibernate.exception.SQLGrammarException
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
        List<String> firstnameList = session.createSQLQuery(sql).getResultList();
        for (String firstname : firstnameList) {
            System.out.println(firstname);
        }
        
        String sqlQuery = "Select p.firstname from t_person p where p.id=:id";
        Optional firstname = session.createSQLQuery(sqlQuery)
                .setParameter("id", 4)
                .getResultStream()
                .findFirst();
        if (firstname.isPresent()) {
            System.out.println("Found name: " + firstname.get());
        }
    }

    // 创建SQL Query查询语句，通过addEntity()让原生查询返回实体对象
    private static void testCreateQuery(Session session) {
        NativeQuery query = session.createSQLQuery("Select * from t_first_entity");

        List<MyEntity> rows = session.createSQLQuery("Select * from t_first_entity")
                .addEntity(MyEntity.class)
                .getResultList();
    }
}

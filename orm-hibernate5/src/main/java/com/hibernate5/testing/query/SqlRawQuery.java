package com.hibernate5.testing.query;

import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

public class SqlRawQuery {

    private final String sqlQuery;

    public SqlRawQuery(String sqlQuery) {
        this.sqlQuery = sqlQuery;
    }

    public Query getQuery(Session session) {
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
}

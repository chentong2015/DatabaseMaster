package entity.query;

import org.hibernate.Session;
import org.hibernate.query.Query;

// session.createQuery(hqlQuery) 创建hibernate query查询语句
public class HqlQueryUtil {

    private final String hqlQuery;

    public HqlQueryUtil(String hqlQuery) {
        this.hqlQuery = hqlQuery;
    }

    protected Query getQuery(Session session) {
        return session.createQuery(hqlQuery);
    }

    protected <T> Query<T> getQuery(Session session, Class<T> clazz) {
        return session.createQuery(hqlQuery, clazz);
    }
}

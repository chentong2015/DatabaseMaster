package master.hibernate5.testing.query;

import org.hibernate.Session;
import org.hibernate.query.Query;

// createQuery()表示创建的是hibernate query查询语句
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
}

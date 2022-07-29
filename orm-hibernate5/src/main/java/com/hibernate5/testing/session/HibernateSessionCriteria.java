package com.hibernate5.testing.session;

import org.hibernate.Criteria;
import org.hibernate.FlushMode;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

public class HibernateSessionCriteria {

    // 设置Session在执行数据库操作时的相关配置
    protected Criteria getCriteria(Session session, Class<?> fromClass) {
        Criteria criteria = session.createCriteria(fromClass);
        criteria.setLockMode(LockMode.OPTIMISTIC);
        criteria.addOrder(Order.asc("propertyName"));
        criteria.setFlushMode(FlushMode.COMMIT);
        Object row = criteria.uniqueResult();
        return criteria;
    }
    
    public void testSessionCriteria() {
        // session.createCriteria(Audit.class)
        //        .addOrder(Order.desc("id"))
        //        .setMaxResults(1)
        //        .uniqueResult();
        // session.createCriteria(Trade.class)
        //        .setProjection(Projections.rowCount())
        //        .uniqueResult();
    }
}

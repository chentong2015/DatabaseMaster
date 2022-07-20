package com.hibernate5.testing.query;

import org.hibernate.Criteria;
import org.hibernate.FlushMode;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

public class DemoSessionCriteria {

    // 设置Session在执行数据库操作时的相关配置 => 相当于执行一个CriteriaQuery标准查询
    protected Criteria getCriteria(Session session, Class<?> fromClass) {
        Criteria criteria = session.createCriteria(fromClass);
        criteria.setLockMode(LockMode.OPTIMISTIC);
        criteria.addOrder(Order.asc("propertyName"));
        criteria.setFlushMode(FlushMode.COMMIT);
        Object row = criteria.uniqueResult();
        return criteria;
    }
}

package com.hibernate6.main.sesssion;

import org.hibernate.Session;
import org.hibernate.query.MutationQuery;

// TODO. 完全对Hibernate Session的封装: 委托delegate
public class DelegateSession implements AutoCloseable {

    private final Session delegateSession;

    public DelegateSession(Session delegateSession) {
        this.delegateSession = delegateSession;
    }

    public void save(Object object) {
        delegateSession.persist(object);
    }

    public MutationQuery createQuery(String queryString) {
        return delegateSession.createMutationQuery(queryString);
    }

    // DelegateSession生命周期: 在使用try-with-resources时能够被自动调用
    @Override
    public void close() throws Exception {
        delegateSession.close();
    }
}

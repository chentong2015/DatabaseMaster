package hibernate.framework.apis.session;

import org.hibernate.Session;

@FunctionalInterface
public interface HibernateSessionCallback<R> {

    // TODO: 通过回调机制来调用session的方法执行相关操作
    R execute(Session session) throws Exception;
}

package hibernate.framework.apis.lifecycle;

import hibernate.framework.apis.datamodel.Book;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class HibernateObjectLifecycle {

    public static void main(String[] args) {
        testSaveUpdateObject();
    }

    // new > save() > close > update()   旧版本方法
    // new > persist() > close > merge() 新版本方法
    private static void testSaveUpdateObject() {
        Session session = null;
        Transaction transaction = null;
        Book book = new Book();
        try {
            session = HibernateSessionUtil.getSession();
            transaction = session.beginTransaction();

            // 1. 对象只在内存中, Session和数据库中都没有
            book.setName("java");
            book.setTitle("title");
            // 2. 持久化保存后成为持久态，数据库中存在
            session.persist(book);

            transaction.commit();
        } catch (Exception exception) {
            transaction.rollback();
        } finally {
            // 3. 内存中有，没有Session，数据库有
            HibernateSessionUtil.closeSession();
        }

        // TODO. 结束操作后对持久化的数据进行更新 ==> 必须要先查询再更改 !!
        try {
            session = HibernateSessionUtil.getSession();
            transaction = session.beginTransaction();

            // 4. 对已经持久化的数据进行更新
            book.setName("test");
            session.merge(book);

            transaction.commit();
        } catch (Exception exception) {
            transaction.rollback();
        } finally {
            HibernateSessionUtil.closeSession();
        }
    }

    // get > load > clear / evict
    // 
    public void testLoadClearObject() {
        Session session = null;
        try {
            session = HibernateSessionUtil.getSession();
            Book book = session.get(Book.class, 1);
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            // 3. 内存中有，没有Session，数据库有
            HibernateSessionUtil.closeSession();
        }
    }
}

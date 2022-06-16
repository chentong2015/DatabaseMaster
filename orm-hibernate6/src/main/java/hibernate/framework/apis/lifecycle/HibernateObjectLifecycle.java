package hibernate.framework.apis.lifecycle;

import hibernate.framework.apis.datamodel.Book;
import hibernate.framework.apis.session.HibernateSessionUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class HibernateObjectLifecycle {

    public static void main(String[] args) {
        testDeleteObject();
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

        // TODO. 结束操作后对持久化的数据进行更新
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
    private static void testLoadClearObject() {
        Session session = null;
        try {
            session = HibernateSessionUtil.getSession();
            // TODO. 立即加载，先查询缓存，再查询数据库
            Book book = session.get(Book.class, 2);
            // TODO. 懒加载(不发SQL)，在使用的时候才会去加载
            Book book1 = session.load(Book.class, 1);

            // 全部清除session的一级缓存
            session.clear();
            // 只清除指定的缓存对象
            session.evict(book);
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            // 3. 内存中有，没有Session，数据库有
            HibernateSessionUtil.closeSession();
        }
    }

    // TODO. 更新数据库中的字段必须要先查询再更改，否则会造成没有设置的字段全部设置成null
    // update 旧版本
    // merge  新版本
    private static void testUpdateObject() {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateSessionUtil.getSession();
            transaction = session.beginTransaction();

            Book book = session.get(Book.class, 10);
            if (book != null) {
                book.setName("update name");
                session.merge(book);
            }
            transaction.commit();
        } catch (Exception exception) {
            transaction.rollback();
        } finally {
            // 3. 内存中有，没有Session，数据库有
            HibernateSessionUtil.closeSession();
        }
    }

    // delete 旧版本
    // remove 新版本
    private static void testDeleteObject() {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateSessionUtil.getSession();
            transaction = session.beginTransaction();

            Book book = session.get(Book.class, 10);
            if (book != null) {
                book.setName("update name");
                session.remove(book);
            }
            transaction.commit();
        } catch (Exception exception) {
            transaction.rollback();
        } finally {
            // 3. 内存中有，没有Session，数据库有
            HibernateSessionUtil.closeSession();
        }
    }
}

package hibernate.framework.apis.mapping.unidirectional.many.to.one;

import hibernate.framework.apis.session.HibernateSessionUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

// TODO. 完全根据对象生成数据库中的表
public class DemoMappingManyToOne {

    public static void main(String[] args) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateSessionUtil.getSession();
            transaction = session.beginTransaction();

            // 先存储多对一的那个表的信息
            Grade grade1 = new Grade("basic");
            Grade grade2 = new Grade("master");
            session.persist(grade1);
            session.persist(grade2);

            // 再存储包含外键关系的对象
            Student student1 = new Student("name1", 10, grade1);
            Student student2 = new Student("name2", 20, grade1);
            Student student3 = new Student("name3", 30, grade2);
            session.persist(student1);
            session.persist(student2);
            session.persist(student3);

            transaction.commit();
        } catch (Exception exception) {
            transaction.rollback();
        } finally {
            // 3. 内存中有，没有Session，数据库有
            HibernateSessionUtil.closeSession();
        }
    }
}

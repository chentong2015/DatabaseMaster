package hibernate.framework.apis.associations.unidirectional.one.to.many;

import hibernate.framework.apis.session.HibernateSessionUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

// 先存储One端的数据，再存储many端的数据
// 存储是一定会存在update更新操作，效率不如Many-To-One
public class UndirectionalOneToMany {

    public static void main(String[] args) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateSessionUtil.getSession();
            transaction = session.beginTransaction();

            Student student1 = new Student("cascade name1", 10);
            Student student2 = new Student("cascade name2", 20);
            Student student3 = new Student("cascade name3", 30);
            Grade grade1 = new Grade("cascade basic");
            grade1.getStudents().add(student1);
            grade1.getStudents().add(student2);
            Grade grade2 = new Grade("cascade master");
            grade2.getStudents().add(student3);

            // 使用"save-update"级联操作之后，只需要存储grade
            session.persist(grade1);
            session.persist(grade2);
            // session.persist(student1);
            // session.persist(student2);
            // session.persist(student3);
            transaction.commit();
        } catch (Exception exception) {
            exception.printStackTrace();
            transaction.rollback();
        } finally {
            HibernateSessionUtil.closeSession();
        }
    }
}
